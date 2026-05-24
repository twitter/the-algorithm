package com.twitter.home_mixer.functional_component.side_effect

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.filter.OffloadFilter
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mysql.Client
import com.twitter.finagle.mysql.Transactions
import com.twitter.finagle.offload.OffloadFuturePool
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.transport.Transport
import com.twitter.finagle.util.DefaultTimer
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.content.ClipEmbeddingFeaturesAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.content.TextTokensFeaturesAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.light_ranking_features.LightRankingCandidateFeatures
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.light_ranking_features.LightRankingCandidateFeaturesAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.non_ml_features.NonMLCandidateFeatures
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.non_ml_features.NonMLCandidateFeaturesAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.non_ml_features.NonMLCommonFeatures
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.non_ml_features.NonMLCommonFeaturesAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.twhin_embeddings.TwhinVideoEmbeddingsAdapter
import com.twitter.home_mixer.functional_component.scorer.CandidateFeaturesDataRecordFeature
import com.twitter.home_mixer.model.HomeFeatures.ClientIdFeature
import com.twitter.home_mixer.model.HomeFeatures.GuestIdFeature
import com.twitter.home_mixer.model.HomeFeatures.HasVideoFeature
import com.twitter.home_mixer.model.HomeFeatures.PredictionRequestIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetTextTokensFeature
import com.twitter.home_mixer.model.HomeFeatures.WeightedModelScoreFeature
import com.twitter.home_mixer.model.PredictedScoreFeature.PredictedScoreFeatureSet
import com.twitter.home_mixer.model.request.FollowingProduct
import com.twitter.home_mixer.model.request.ForYouProduct
import com.twitter.home_mixer.model.request.ScoredTweetsProduct
import com.twitter.home_mixer.model.request.ScoredVideoTweetsProduct
import com.twitter.home_mixer.model.request.SubscribedProduct
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableClipEmbeddingFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTweetTextTokensEmbeddingFeatureScribingParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTweetVideoAggregatedWatchTimeFeatureScribingParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTwhinVideoFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableVideoClipEmbeddingFeatureHydrationDeciderParam
import com.twitter.home_mixer.param.HomeGlobalParams.IsSelectedByHeavyRankerCountParam
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.home_mixer.util.CandidatesUtil.getOriginalAuthorId
import com.twitter.home_mixer.util.CandidatesUtil.getOriginalTweetId
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.DataRecordMerger
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.product_mixer.core.feature.featuremap.datarecord.DataRecordConverter
import com.twitter.product_mixer.core.feature.featuremap.datarecord.SpecificFeatures
import com.twitter.product_mixer.core.functional_component.common.alert.SuccessRateAlert
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateSourcePosition
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.simclusters_v2.thriftscala.TwhinTweetEmbedding
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus.Store
import com.twitter.strato.generated.client.videoRecommendations.twitterClip.TwitterClipEmbeddingMhClientColumn
import com.twitter.timelines.ml.cont_train.common.domain.non_scalding.CandidateAndCommonFeaturesStreamingUtils
import com.twitter.timelines.ml.pldr.client.MysqlClientUtils
import com.twitter.timelines.ml.pldr.client.VersionedMetadataCacheClient
import com.twitter.timelines.ml.pldr.conversion.VersionIdAndFeatures
import com.twitter.timelines.prediction.adapters.twistly.VideoAggregatedWatchTimeFeaturesAdapter
import com.twitter.timelines.prediction.features.large_embeddings.LargeEmbeddingsFeatures.AllCandidateLargeEmbeddingsFeatures
import com.twitter.timelines.served_candidates_logging.{thriftscala => sc}
import com.twitter.timelines.suggests.common.data_record_metadata.{thriftscala => drmd}
import com.twitter.timelines.suggests.common.poly_data_record.{thriftjava => pldr}
import com.twitter.timelines.util.stats.FutureObserver
import com.twitter.timelines.util.stats.OptionObserver
import com.twitter.twistly.thriftscala.VideoViewEngagementType
import com.twitter.twistly.thriftscala.WatchTimeMetadata
import com.twitter.twistly.{thriftscala => ts}
import com.twitter.util.Future
import com.twitter.util.Try
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

@Singleton
class BaseCacheCandidateFeaturesSideEffect @Inject() (
  dataRecordMetadataStoreConfigsYml: String,
  store: Store[
    sc.CandidateFeatureKey,
    pldr.PolyDataRecord
  ],
  tweetWatchTimeMetadataStore: ReadableStore[(Long, VideoViewEngagementType), WatchTimeMetadata],
  twitterClipEmbeddingMhClientColumn: TwitterClipEmbeddingMhClientColumn,
  twhinVideoStore: ReadableStore[Long, TwhinTweetEmbedding],
  statsReceiver: StatsReceiver)
    extends PipelineResultSideEffect[PipelineQuery, HasMarshalling]
    with Conditionally[PipelineQuery, HasMarshalling]
    with Logging {

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Boolean = {
    val serviceIdentifier = ServiceIdentifier.fromCertificate(Transport.peerCertificate)
    (selectedCandidates.nonEmpty || remainingCandidates.nonEmpty || droppedCandidates.nonEmpty) && serviceIdentifier.role != "video-mixer"
  }

  override val alerts: Seq[SuccessRateAlert] = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(98.5))

  val identifier: SideEffectIdentifier =
    SideEffectIdentifier("BaseCacheCandidateFeaturesSideEffect")
  val statScope: String = this.getClass.getSimpleName

  private val scopedStatsReceiver = statsReceiver.scope(statScope)
  private val metadataFetchFailedCounter = scopedStatsReceiver.counter("metadataFetchFailed")
  private val writesRequestCounter = scopedStatsReceiver.counter("writesRequests")
  private val writesFailedCounter = scopedStatsReceiver.counter("writesFailed")

  private val twhinVideoEmbeddingFutureObserver = FutureObserver(
    scopedStatsReceiver.scope("twhinVideoEmbedding"))
  private val twhinVideoEmbeddingOptionObserver = OptionObserver(
    scopedStatsReceiver.scope("twhinVideoEmbedding"))

  private val clipEmbeddingCounter = scopedStatsReceiver.counter("clipEmbeddingCounter")

  private val tweetWatchTimeMetadataRequestCounter =
    scopedStatsReceiver.counter("tweetWatchTimeMetadataRequests")
  private val tweetWatchTimeMetadataSuccessCounter =
    scopedStatsReceiver.counter("tweetWatchTimeMetadataSuccessCounter")
  private val tweetWatchTimeMetadataFailureCounter =
    scopedStatsReceiver.counter("tweetWatchTimeMetadataFailureCounter")

  private val drMerger = new DataRecordMerger
  private val predictedScoreFeaturesDataRecordAdapter =
    new DataRecordConverter(SpecificFeatures(PredictedScoreFeatureSet))

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

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, HasMarshalling]
  ): Stitch[Unit] = Stitch.let(OffloadFuturePool.lowPriorityLocal)(()) {
    OffloadFuturePools.offloadStitch {

      // Exclude unscored candidates (e.g. because of scoring QF truncation or cache reads)
      val selectedCandidates =
        (inputs.selectedCandidates ++ inputs.remainingCandidates)
          .filter { candidate =>
            candidate.features.contains(CandidateFeaturesDataRecordFeature)
          }
      val droppedCandidates =
        inputs.droppedCandidates
          .filter { candidate =>
            candidate.features.contains(CandidateFeaturesDataRecordFeature)
          }

      val candidates = (selectedCandidates ++ droppedCandidates)
      val isSelectedCandidateIds: Set[Long] = selectedCandidates.map(_.candidateIdLong).toSet
      val candidatesHeavyRankerScoreBasedRank: Map[Long, Int] = candidates
        .sortBy(-_.features
          .getOrElse(WeightedModelScoreFeature, None).getOrElse(Double.NegativeInfinity)).map(
          _.candidateIdLong).zipWithIndex.toMap
      val isSelectedByHeavyRankerCount = inputs.query.params(IsSelectedByHeavyRankerCountParam)

      val predictionRequestId = candidates.headOption.flatMap { candidate =>
        candidate.features.getOrElse(PredictionRequestIdFeature, None)
      }

      val productSurface = inputs.query.product match {
        case FollowingProduct => hmt.Product.Following
        case ForYouProduct => hmt.Product.ForYou
        case ScoredTweetsProduct => hmt.Product.ScoredTweets
        case ScoredVideoTweetsProduct => hmt.Product.ScoredVideoTweets
        case SubscribedProduct => hmt.Product.Subscribed
        case other => throw new UnsupportedOperationException(s"Unknown product: $other")
      }

      val nonMLCommonFeatures = NonMLCommonFeatures(
        userId = inputs.query.getRequiredUserId,
        guestId = inputs.query.features.flatMap(_.getOrElse(GuestIdFeature, None)),
        clientId = inputs.query.features.flatMap(_.getOrElse(ClientIdFeature, None)),
        countryCode = inputs.query.getCountryCode,
        predictionRequestId = predictionRequestId,
        productSurface = productSurface.toString,
        servedTimestamp = inputs.query.queryTime.inMilliseconds
      )

      val nonMLCommonFeaturesDataRecord =
        NonMLCommonFeaturesAdapter.adaptToDataRecords(nonMLCommonFeatures).asScala.head

      val sideEffectStitch = fetchExperimentalFeatures(inputs.query, candidates)
        .map { candidatesAndFeatures =>
          // Further writes are cheap and do not have callbacks,
          // therefore it's safe to bypass offload filter.
          OffloadFilter.withOffloadsDisabled {
            candidatesAndFeatures.foreach {
              case (candidate, experimentalFeatures) =>
                val candidateFeaturesPldr = buildCandidateFeaturesPldr(
                  query = inputs.query,
                  candidate = candidate,
                  isSelected = isSelectedCandidateIds.contains(candidate.candidateIdLong),
                  isSelectedByHeavyRanker = candidatesHeavyRankerScoreBasedRank
                    .getOrElse(
                      candidate.candidateIdLong,
                      candidates.size) < isSelectedByHeavyRankerCount,
                  rankByHeavyRanker = candidatesHeavyRankerScoreBasedRank
                    .getOrElse(candidate.candidateIdLong, candidates.size),
                  nonMLCommonFeaturesDataRecord = nonMLCommonFeaturesDataRecord,
                  experimentalFeaturesDataRecords = experimentalFeatures,
                )

                writesRequestCounter.incr()
                val candidateFeaturesKey = sc.CandidateFeatureKey(
                  tweetId = candidate.candidateIdLong,
                  viewerId = inputs.query.getRequiredUserId,
                  servedId = predictionRequestId.getOrElse(-1L)
                )

                store.put(candidateFeaturesKey -> candidateFeaturesPldr).rescue {
                  case _: Throwable =>
                    writesFailedCounter.incr()
                    Future.Unit
                }
            }
          }
        }

      Stitch.run(sideEffectStitch)

      Stitch.Unit
    }
  }

  private def buildCandidateFeaturesPldr(
    query: PipelineQuery,
    candidate: CandidateWithDetails,
    isSelected: Boolean,
    isSelectedByHeavyRanker: Boolean,
    rankByHeavyRanker: Int,
    nonMLCommonFeaturesDataRecord: DataRecord,
    experimentalFeaturesDataRecords: Seq[DataRecord]
  ): Option[pldr.PolyDataRecord] = {
    // Step 1) Set candidate features to all existing candidate features used in ranking
    val candidateFeaturesDataRecord =
      candidate.features.get(CandidateFeaturesDataRecordFeature)

    // Step 2) Remove all large embeddings features from DataRecord
    AllCandidateLargeEmbeddingsFeatures.foreach { feature =>
      candidateFeaturesDataRecord.tensors.remove(feature.getFeatureId)
    }

    // Step 3) Add prediction score
    val predictedScoreFeaturesDataRecord =
      predictedScoreFeaturesDataRecordAdapter.toDataRecord(candidate.features)
    drMerger.merge(candidateFeaturesDataRecord, predictedScoreFeaturesDataRecord)

    // Step 4) Add non-ML common features
    drMerger.merge(candidateFeaturesDataRecord, nonMLCommonFeaturesDataRecord)

    // Step 5) Add non-ML candidate features, including light ranking features
    val nonMLCandidateFeatures = NonMLCandidateFeatures(
      tweetId = candidate.candidateIdLong,
      sourceTweetId = getOriginalTweetId(candidate.candidateIdLong, candidate.features),
      originalAuthorId = getOriginalAuthorId(candidate.features)
    )
    val nonMLCandidateFeaturesDataRecord =
      NonMLCandidateFeaturesAdapter.adaptToDataRecords(nonMLCandidateFeatures).asScala.head
    val lightRankingCandidateFeatures = LightRankingCandidateFeatures(
      isSelected = isSelected,
      isSelectedByHeavyRanker = isSelectedByHeavyRanker,
      rankByHeavyRanker = rankByHeavyRanker,
      servedType = candidate.features.get(ServedTypeFeature),
      candidateSourcePosition = candidate.features.get(CandidateSourcePosition).toLong
    )
    val lightRankingCandidateFeaturesDataRecord =
      LightRankingCandidateFeaturesAdapter
        .adaptToDataRecords(lightRankingCandidateFeatures).asScala.head
    drMerger.merge(nonMLCandidateFeaturesDataRecord, lightRankingCandidateFeaturesDataRecord)
    drMerger.merge(candidateFeaturesDataRecord, nonMLCandidateFeaturesDataRecord)

    // Step 5) Add experimental features (including twhin)
    experimentalFeaturesDataRecords.foreach(drMerger.merge(candidateFeaturesDataRecord, _))

    CandidateAndCommonFeaturesStreamingUtils.candidateFeaturesToPolyDataRecord(
      versionedMetadataCacheClientOpt = versionedMetadataCacheClientOpt,
      candidateFeatures = candidateFeaturesDataRecord,
      valueFormat = pldr.PolyDataRecord._Fields.LITE_COMPACT_DATA_RECORD
    )
  }

  private def fetchExperimentalFeatures(
    query: PipelineQuery,
    candidates: Seq[CandidateWithDetails],
  ): Stitch[Map[CandidateWithDetails, Seq[DataRecord]]] = {
    val stitches = Seq(
      fetchTwhinVideoEmbeddingDataRecords(query, candidates),
      fetchTweetTextTokensDataRecord(query, candidates),
      fetchClipEmbeddings(query, candidates),
      fetchTweetVideoAggregatedWatchTimeDataRecord(query, candidates),
    )
    Stitch
      .collect(stitches)
      .map { candidatesMaps =>
        val candidatesToDataRecords =
          new java.util.HashMap[CandidateWithDetails, ArrayBuffer[DataRecord]](
            candidates.size * 3 / 4)
        candidatesMaps.foreach { candidatesToDataRecord =>
          candidatesToDataRecord.foreach {
            case (candidate, dataRecord) =>
              if (dataRecord.isDefined) {
                candidatesToDataRecords
                  .computeIfAbsent(
                    candidate,
                    _ => new ArrayBuffer[DataRecord](stitches.size)
                  ).append(dataRecord.get)
              }
          }
        }
        candidatesToDataRecords.asScala.toMap
      }
  }

  private def fetchTwhinVideoEmbeddingDataRecords(
    query: PipelineQuery,
    candidates: Seq[CandidateWithDetails],
  ): Stitch[Map[CandidateWithDetails, Option[DataRecord]]] = {
    if (!query.params(EnableTwhinVideoFeaturesParam)) {
      Stitch.value(Map.empty)
    } else {
      val originalTweetToCandidates = candidates.groupBy { CandidatesUtil.getOriginalTweetId(_) }
      Stitch.callFuture {
        twhinVideoEmbeddingFutureObserver(
          Future.collect(twhinVideoStore.multiGet(originalTweetToCandidates.keySet)).map {
            originalTweetIdToEmbeddings =>
              originalTweetIdToEmbeddings.flatMap {
                case (originalTweetId, embeddingOpt) =>
                  val floatTensor = twhinVideoEmbeddingOptionObserver(embeddingOpt)
                    .map { e => ml.FloatTensor(e.embedding) }
                  val dataRecord =
                    TwhinVideoEmbeddingsAdapter.adaptToDataRecords(floatTensor).asScala.headOption
                  originalTweetToCandidates(originalTweetId).map { candidate =>
                    candidate -> dataRecord
                  }
              }
          }
        )
      }
    }
  }

  private def fetchClipEmbeddings(
    query: PipelineQuery,
    candidates: Seq[CandidateWithDetails],
  ): Stitch[Map[CandidateWithDetails, Option[DataRecord]]] = {
    Stitch
      .collect(
        candidates.map { candidate =>
          if (query.params(EnableClipEmbeddingFeaturesParam) &&
            candidate.features.getOrElse(HasVideoFeature, false) &&
            !query.params(
              EnableVideoClipEmbeddingFeatureHydrationDeciderParam
            )) { // If it's not enabled through feature hydrator
            clipEmbeddingCounter.incr()
            twitterClipEmbeddingMhClientColumn.fetcher
              .fetch(candidate.candidateIdLong)
              .map { result =>
                candidate -> result.v.flatMap { record =>
                  ClipEmbeddingFeaturesAdapter
                    .adaptToDataRecords(record)
                    .asScala
                    .headOption
                }
              }
          } else {
            Stitch.value(candidate -> None)
          }
        }
      ).map(_.toMap)
  }

  private def fetchTweetTextTokensDataRecord(
    query: PipelineQuery,
    candidates: Seq[CandidateWithDetails],
  ): Stitch[Map[CandidateWithDetails, Option[DataRecord]]] = {
    Stitch.value {
      if (query.params(EnableTweetTextTokensEmbeddingFeatureScribingParam)) {
        Map.empty
      } else {
        candidates.map { candidate =>
          candidate -> candidate.features.getOrElse(TweetTextTokensFeature, None).flatMap {
            textTokens =>
              TextTokensFeaturesAdapter
                .adaptToDataRecords(textTokens)
                .asScala
                .headOption
          }
        }.toMap
      }
    }
  }
  private def fetchTweetVideoAggregatedWatchTimeDataRecord(
    query: PipelineQuery,
    candidates: Seq[CandidateWithDetails],
  ): Stitch[Map[CandidateWithDetails, Option[DataRecord]]] = {
    Stitch
      .collect(
        candidates.map { candidate =>
          if (query.params(
              EnableTweetVideoAggregatedWatchTimeFeatureScribingParam) && candidate.features
              .getOrElse(HasVideoFeature, false)) {
            tweetWatchTimeMetadataRequestCounter.incr()
            Stitch.callFuture(
              tweetWatchTimeMetadataStore
                .get(
                  (candidate.candidateIdLong, ts.VideoViewEngagementType.ImmersiveVideoWatchTime)
                ).onSuccess { _ => tweetWatchTimeMetadataSuccessCounter.incr() }
                .onFailure { _ => tweetWatchTimeMetadataFailureCounter.incr() }
                .map { opt =>
                  candidate -> opt.flatMap(VideoAggregatedWatchTimeFeaturesAdapter
                    .adaptToDataRecords(_).asScala.headOption)
                }
            )
          } else {
            Stitch.value(candidate -> None)
          }
        }
      ).map(_.toMap)
  }

}
