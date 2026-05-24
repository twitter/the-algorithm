package com.twitter.home_mixer.functional_component.side_effect

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mysql.Client
import com.twitter.finagle.mysql.Transactions
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.util.DefaultTimer
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.non_ml_features.NonMLCommonFeatures
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.non_ml_features.NonMLCommonFeaturesAdapter
import com.twitter.home_mixer.functional_component.scorer.CommonFeaturesDataRecordFeature
import com.twitter.home_mixer.model.HomeFeatures.ClientIdFeature
import com.twitter.home_mixer.model.HomeFeatures.GuestIdFeature
import com.twitter.home_mixer.model.HomeFeatures.PredictionRequestIdFeature
import com.twitter.home_mixer.model.request.FollowingProduct
import com.twitter.home_mixer.model.request.ForYouProduct
import com.twitter.home_mixer.model.request.ScoredTweetsProduct
import com.twitter.home_mixer.model.request.ScoredVideoTweetsProduct
import com.twitter.home_mixer.model.request.SubscribedProduct
import com.twitter.home_mixer.param.HomeGlobalParams
import com.twitter.home_mixer.param.HomeMixerFlagName.DataRecordMetadataStoreConfigsYmlFlag
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.inject.annotations.Flag
import com.twitter.ml.api.DataRecordMerger
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.ml.cont_train.common.domain.non_scalding.CandidateAndCommonFeaturesStreamingUtils
import com.twitter.timelines.ml.pldr.client.MysqlClientUtils
import com.twitter.timelines.ml.pldr.client.VersionedMetadataCacheClient
import com.twitter.timelines.ml.pldr.conversion.VersionIdAndFeatures
import com.twitter.timelines.prediction.features.large_embeddings.LargeEmbeddingsFeatures.AllCommonLargeEmbeddingsFeatures
import com.twitter.timelines.suggests.common.data_record_metadata.{thriftscala => drmd}
import com.twitter.timelines.suggests.common.poly_data_record.{thriftjava => pldr}
import com.twitter.timelines.util.stats.OptionObserver
import com.twitter.util.Try
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._

@Singleton
class CommonFeaturesPldrConverter @Inject() (
  @Flag(DataRecordMetadataStoreConfigsYmlFlag) dataRecordMetadataStoreConfigsYml: String,
  statsReceiver: StatsReceiver) {

  private val drMerger = new DataRecordMerger

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val metadataFetchFailedCounter = scopedStatsReceiver.counter("metadataFetchFailed")

  private val commonFeaturesPLDROptionObserver =
    OptionObserver(scopedStatsReceiver.scope("commonFeaturesPLDR"))

  private lazy val dataRecordMetadataStoreClient: Option[Client with Transactions] = Try {
    MysqlClientUtils.mysqlClientProvider(
      MysqlClientUtils.parseConfigFromYaml(dataRecordMetadataStoreConfigsYml)
    )
  }.toOption

  private lazy val versionedMetadataCacheClientOpt: Option[
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

  /**
   * Get the common features data record converted to PLDR format with prediction request ID
   *
   * @param query
   * @param selectedCandidates
   * @return prediction request ID and the common features PLDR for the given request
   */
  def getCommonFeaturesPldr(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails]
  ): Option[(Long, pldr.PolyDataRecord)] = {
    // Exclude unscored candidates (e.g. because of scoring QF truncation or cache reads)
    val candidatesHead = selectedCandidates
      .find { candidate =>
        candidate.features.contains(CommonFeaturesDataRecordFeature)
      }

    if (candidatesHead.nonEmpty) {
      val candidateFeaturesHead = candidatesHead.get.features
      val predictionRequestId = candidateFeaturesHead.getOrElse(PredictionRequestIdFeature, None)
      val productSurface = query.product match {
        case FollowingProduct => hmt.Product.Following
        case ForYouProduct => hmt.Product.ForYou
        case ScoredTweetsProduct => hmt.Product.ScoredTweets
        case ScoredVideoTweetsProduct => hmt.Product.ScoredVideoTweets
        case SubscribedProduct => hmt.Product.Subscribed
        case other => throw new UnsupportedOperationException(s"Unknown product: $other")
      }
      val nonMLCommonFeatures = NonMLCommonFeatures(
        userId = query.getRequiredUserId,
        guestId = candidateFeaturesHead.getOrElse(GuestIdFeature, None),
        clientId = candidateFeaturesHead.getOrElse(ClientIdFeature, None),
        countryCode = query.getCountryCode,
        predictionRequestId = predictionRequestId,
        productSurface = productSurface.toString,
        servedTimestamp = query.queryTime.inMilliseconds
      )
      val nonMLCommonFeaturesDataRecord =
        NonMLCommonFeaturesAdapter.adaptToDataRecords(nonMLCommonFeatures).asScala.head

      val commonFeaturesDataRecord = candidateFeaturesHead.get(CommonFeaturesDataRecordFeature)
      //Remove large embeddings from dataRecord
      AllCommonLargeEmbeddingsFeatures.foreach { feature =>
        commonFeaturesDataRecord.tensors.remove(feature.getFeatureId)
      }
      drMerger.merge(commonFeaturesDataRecord, nonMLCommonFeaturesDataRecord)

      val commonFeaturesPLDROpt = CandidateAndCommonFeaturesStreamingUtils
        .commonFeaturesToPolyDataRecord(
          versionedMetadataCacheClientOpt = versionedMetadataCacheClientOpt,
          commonFeatures = commonFeaturesDataRecord,
          valueFormat = pldr.PolyDataRecord._Fields.LITE_COMPACT_DATA_RECORD,
          createNew = query.params(
            HomeGlobalParams.EnableCommonFeaturesDataRecordCopyDuringPldrConversionParam)
        )

      commonFeaturesPLDROptionObserver(commonFeaturesPLDROpt).flatMap { pldr =>
        predictionRequestId.map(_ -> pldr)
      }
    } else None
  }
}
