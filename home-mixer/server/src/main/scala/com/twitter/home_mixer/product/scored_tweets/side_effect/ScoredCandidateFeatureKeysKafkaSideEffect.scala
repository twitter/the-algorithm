package com.twitter.home_mixer.product.scored_tweets.side_effect

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mysql.Client
import com.twitter.finagle.mysql.Transactions
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.util.DefaultTimer
import com.twitter.finatra.kafka.serde.ScalaSerdes
import com.twitter.home_mixer.functional_component.scorer.CandidateFeaturesDataRecordFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokAnnotationsFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.IsReadFromCacheFeature
import com.twitter.home_mixer.model.HomeFeatures.PredictionRequestIdFeature
import com.twitter.home_mixer.model.HomeFeatures.WeightedModelScoreFeature
import com.twitter.home_mixer.model.PredictedScoreFeature.PredictedScoreFeatureSet
import com.twitter.home_mixer.param.HomeGlobalParams.IsSelectedByHeavyRankerCountParam
import com.twitter.home_mixer.param.HomeMixerFlagName.DataRecordMetadataStoreConfigsYmlFlag
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableScoredCandidateFeatureKeysKafkaPublishingParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.ScribedScoredCandidateNumParam
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.inject.annotations.Flag
import com.twitter.ml.api.DataRecordMerger
import com.twitter.product_mixer.component_library.side_effect.KafkaPublishingSideEffect
import com.twitter.product_mixer.core.feature.featuremap.datarecord.DataRecordConverter
import com.twitter.product_mixer.core.feature.featuremap.datarecord.SpecificFeatures
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.ml.cont_train.common.domain.non_scalding.CandidateAndCommonFeaturesStreamingUtils
import com.twitter.timelines.data_processing.jobs.light_ranking.light_ranking_features_prep.RecapPartialFeaturesForTwoTowerModels
import com.twitter.timelines.ml.cont_train.common.domain.non_scalding.ScoredCandidateFeatureKeysAdapter
import com.twitter.timelines.ml.cont_train.common.domain.non_scalding.ScoredCandidateFeatureKeysFields
import com.twitter.timelines.ml.kafka.serde.TBaseSerde
import com.twitter.timelines.ml.pldr.client.MysqlClientUtils
import com.twitter.timelines.ml.pldr.client.VersionedMetadataCacheClient
import com.twitter.timelines.ml.pldr.conversion.VersionIdAndFeatures
import com.twitter.timelines.suggests.common.data_record_metadata.{thriftscala => drmd}
import com.twitter.timelines.suggests.common.poly_data_record.{thriftjava => pldr}
import com.twitter.util.Try
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.Serializer
import scala.collection.JavaConverters._

/**
 * Pipeline side-effect that publishes scored candidate feature keys to a Kafka topic.
 */
@Singleton
class ScoredCandidateFeatureKeysKafkaSideEffect @Inject() (
  serviceIdentifier: ServiceIdentifier,
  @Flag(DataRecordMetadataStoreConfigsYmlFlag) dataRecordMetadataStoreConfigsYml: String,
  statsReceiver: StatsReceiver)
    extends KafkaPublishingSideEffect[
      Long,
      pldr.PolyDataRecord,
      PipelineQuery,
      HasMarshalling
    ]
    with Conditionally[PipelineQuery, HasMarshalling]
    with Logging {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier(
    "ScoredCandidateFeatureKeysKafka")
  private val statScope: String = this.getClass.getSimpleName

  private val scopedStatsReceiver = statsReceiver.scope(statScope)
  private val metadataFetchFailedCounter = scopedStatsReceiver.counter("metadataFetchFailed")
  private val randomSelectedCandidatesStat =
    scopedStatsReceiver.stat("randomSelectedCandidates")
  private val randomDroppedCandidatesStat =
    scopedStatsReceiver.stat("randomDroppedCandidates")

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Boolean = {
    if (query.params.getBoolean(EnableScoredCandidateFeatureKeysKafkaPublishingParam)) {
      val scribedCandidateNumber = query.params(ScribedScoredCandidateNumParam)
      filterRankedCandidates(selectedCandidates).size >= scribedCandidateNumber &&
      filterRankedCandidates(droppedCandidates).size >= scribedCandidateNumber && selectedCandidates
        .forall(!_.features.getOrElse(IsReadFromCacheFeature, false)) && remainingCandidates
        .forall(!_.features.getOrElse(IsReadFromCacheFeature, false)) && droppedCandidates
        .forall(!_.features.getOrElse(IsReadFromCacheFeature, false))
    } else false
  }

  private val kafkaTopic: String = serviceIdentifier.environment.toLowerCase match {
    case "prod" => "home_mixer_dropped_candidates_features"
    case _ => "deep_retrieval_candidates_data_staging"
  }

  override val bootstrapServer: String = "/s/kafka/timeline:kafka-tls"
  override val keySerde: Serializer[Long] = ScalaSerdes.Long.serializer()
  override val valueSerde: Serializer[pldr.PolyDataRecord] =
    TBaseSerde.Thrift[pldr.PolyDataRecord]().serializer
  override val clientId: String = "home_mixer_dropped_candidate_feature_keys_producer"

  lazy private val dataRecordMetadataStoreClient: Option[Client with Transactions] = Try {
    try {
      val c = MysqlClientUtils.parseConfigFromYaml(dataRecordMetadataStoreConfigsYml)
      logger.info(s"pldr mysql config: ${c.host} ${c.port} ${c.user} ${c.database}")
    } catch {
      case e: Throwable =>
        logger.error("pldr mysql error: " + e.toString)
    }

    MysqlClientUtils.mysqlClientProvider(
      MysqlClientUtils.parseConfigFromYaml(dataRecordMetadataStoreConfigsYml)
    )
  }.toOption

  lazy private val versionedMetadataCacheClientOpt: Option[
    VersionedMetadataCacheClient[Map[drmd.FeaturesCategory, Option[VersionIdAndFeatures]]]
  ] = dataRecordMetadataStoreClient.map { mysqlClient =>
    new VersionedMetadataCacheClient[Map[drmd.FeaturesCategory, Option[VersionIdAndFeatures]]](
      maximumSize = 1,
      expireDurationOpt = None,
      mysqlClient = mysqlClient,
      transform = CandidateAndCommonFeaturesStreamingUtils.metadataTransformer,
      statsReceiver = statsReceiver
    )
  }

  versionedMetadataCacheClientOpt.foreach {
    _.metadataFetchTimerTask(
      CandidateAndCommonFeaturesStreamingUtils.metadataFetchKey,
      metadataFetchTimer = DefaultTimer,
      metadataFetchInterval = 90.seconds,
      metadataFetchFailedCounter = metadataFetchFailedCounter
    )
  }

  private val drMerger = new DataRecordMerger
  private val predictedScoreFeaturesDataRecordAdapter =
    new DataRecordConverter(SpecificFeatures(PredictedScoreFeatureSet))

  override def buildRecords(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Seq[ProducerRecord[Long, pldr.PolyDataRecord]] = {
    val rankedSelected = filterRankedCandidates(selectedCandidates)
      .filterNot { candidate =>
        val annotations = candidate.features.getOrElse(GrokAnnotationsFeature, None)
        val isNsfw = annotations.flatMap(_.metadata.map(_.isNsfw)).getOrElse(false)
        val isSoftNsfw = annotations.flatMap(_.metadata.map(_.isSoftNsfw)).getOrElse(false)
        val isGore = annotations.flatMap(_.metadata.map(_.isGore)).getOrElse(false)
        val isViolent = annotations.flatMap(_.metadata.map(_.isViolent)).getOrElse(false)
        val isSpam = annotations.flatMap(_.metadata.map(_.isSpam)).getOrElse(false)
        isNsfw || isSoftNsfw || isGore || isViolent || isSpam
      }
    val rankedDropped = filterRankedCandidates(droppedCandidates)

    val candidates = rankedSelected ++ rankedDropped
    val isSelectedCandidateIds: Set[Long] = selectedCandidates.map(_.candidateIdLong).toSet
    val candidatesHeavyRankerScoreBasedRank: Map[Long, Int] = candidates
      .sortBy(
        -_.features
          .getOrElse(WeightedModelScoreFeature, None).getOrElse(Double.NegativeInfinity)).map(
        _.candidateIdLong).zipWithIndex.toMap
    val isSelectedByHeavyRankerCount = query.params(IsSelectedByHeavyRankerCountParam)

    val predictionRequestId = candidates.headOption.flatMap { candidate =>
      candidate.features.getOrElse(PredictionRequestIdFeature, None)
    }

    val scribedCandidateNumber = query.params(ScribedScoredCandidateNumParam)

    val randomRankedSelected = selectRandomCandidates(rankedSelected, scribedCandidateNumber)
    val randomRankedDropped = selectRandomCandidates(rankedDropped, scribedCandidateNumber)

    randomSelectedCandidatesStat.add(randomRankedSelected.size)
    randomDroppedCandidatesStat.add(randomRankedDropped.size)

    val randomCandidates = randomRankedSelected ++ randomRankedDropped

    randomCandidates.flatMap { candidate =>
      val key = candidate.candidateIdLong
      try {
        val scoredCandidateFeatureKeysDataRecord = ScoredCandidateFeatureKeysAdapter
          .adaptToDataRecords(
            ScoredCandidateFeatureKeysFields(
              viewerId = query.getRequiredUserId,
              tweetId = key,
              isSelected = isSelectedCandidateIds.contains(candidate.candidateIdLong),
              isSelectedByHeavyRanker = candidatesHeavyRankerScoreBasedRank
                .getOrElse(
                  candidate.candidateIdLong,
                  candidates.size) < isSelectedByHeavyRankerCount,
              predictionRequestId = predictionRequestId,
              requestTimeMs = Some(query.queryTime.inMilliseconds),
              scribedCandidateNumber = scribedCandidateNumber,
              viewerFollowsOriginalAuthor =
                Some(candidate.features.getOrElse(InNetworkFeature, false)),
              rankByHeavyRanker =
                Some(candidatesHeavyRankerScoreBasedRank.getOrElse(key, candidates.size))
            )
          ).asScala.head

        val candidateFeaturesDataRecord = CandidateAndCommonFeaturesStreamingUtils
          .extractFeaturesFromDrWithContext(
            candidate.features.get(CandidateFeaturesDataRecordFeature),
            RecapPartialFeaturesForTwoTowerModels.scoredCandidateFeatureContext
          )
        drMerger.merge(scoredCandidateFeatureKeysDataRecord, candidateFeaturesDataRecord)

        val predictedScoreFeaturesDataRecord =
          predictedScoreFeaturesDataRecordAdapter.toDataRecord(candidate.features)
        drMerger.merge(scoredCandidateFeatureKeysDataRecord, predictedScoreFeaturesDataRecord)

        val convertedPlDrOpt =
          CandidateAndCommonFeaturesStreamingUtils.candidateFeaturesToPolyDataRecord(
            versionedMetadataCacheClientOpt = versionedMetadataCacheClientOpt,
            candidateFeatures = scoredCandidateFeatureKeysDataRecord,
            valueFormat = pldr.PolyDataRecord._Fields.LITE_COMPACT_DATA_RECORD
          )

        convertedPlDrOpt.map { convertedPlDr =>
          new ProducerRecord(kafkaTopic, key, convertedPlDr)
        }
      } catch {
        case e: Exception =>
          logger.error(
            s"Error while converting features to PolyDataRecord for candidateId: $key",
            e
          )
          None
      }
    }
  }

  private def filterRankedCandidates(
    candidates: Seq[CandidateWithDetails]
  ): Seq[CandidateWithDetails] =
    candidates.filter(_.features.contains(CandidateFeaturesDataRecordFeature))

  private def selectRandomCandidates(
    candidates: Seq[CandidateWithDetails],
    count: Int
  ): Seq[CandidateWithDetails] =
    scala.util.Random.shuffle(CandidatesUtil.getItemCandidates(candidates)).take(count)

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(98.5)
  )
}
