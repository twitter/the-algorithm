package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.author_features

import com.twitter.ml.api.DataRecordMerger
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.ml.api.util.CompactDataRecordConverter
import com.twitter.ml.api.util.FDsl._
import com.twitter.timelines.author_features.v1.{thriftjava => af}
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.common.aggregates.TimelinesAggregationConfig
import com.twitter.timelines.prediction.features.user_health.UserHealthFeatures

object AuthorFeaturesAdapter extends TimelinesMutatingAdapterBase[Option[af.AuthorFeatures]] {

  private val originalAuthorAggregatesFeatures =
    TimelinesAggregationConfig.originalAuthorReciprocalEngagementAggregates
      .buildTypedAggregateGroups().flatMap(_.allOutputFeatures)
  private val authorFeatures = originalAuthorAggregatesFeatures ++
    Seq(
      UserHealthFeatures.AuthorState,
      UserHealthFeatures.NumAuthorFollowers,
      UserHealthFeatures.NumAuthorConnectDays,
      UserHealthFeatures.NumAuthorConnect)
  private val featureContext = new FeatureContext(authorFeatures: _*)

  override def getFeatureContext: FeatureContext = featureContext

  override val commonFeatures: Set[Feature[_]] = Set.empty

  private val compactDataRecordConverter = new CompactDataRecordConverter()
  private val drMerger = new DataRecordMerger()

  override def setFeatures(
    authorFeaturesOpt: Option[af.AuthorFeatures],
    richDataRecord: RichDataRecord
  ): Unit = {
    authorFeaturesOpt.foreach { authorFeatures =>
      val dataRecord = richDataRecord.getRecord

      dataRecord.setFeatureValue(
        UserHealthFeatures.AuthorState,
        authorFeatures.user_health.user_state.getValue.toLong)
      dataRecord.setFeatureValue(
        UserHealthFeatures.NumAuthorFollowers,
        authorFeatures.user_health.num_followers.toDouble)
      dataRecord.setFeatureValue(
        UserHealthFeatures.NumAuthorConnectDays,
        authorFeatures.user_health.num_connect_days.toDouble)
      dataRecord.setFeatureValue(
        UserHealthFeatures.NumAuthorConnect,
        authorFeatures.user_health.num_connect.toDouble)

      val originalAuthorAggregatesDataRecord =
        compactDataRecordConverter.compactDataRecordToDataRecord(authorFeatures.aggregates)
      drMerger.merge(dataRecord, originalAuthorAggregatesDataRecord)
    }
  }
}
