package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.author_features

import com.twitter.home_mixer.util.DataRecordUtil
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.util.CompactDataRecordConverter
import com.twitter.ml.api.util.FDsl._
import com.twitter.timelines.author_features.v1.{thriftjava => af}
import com.twitter.timelines.prediction.common.adapters.TimelinesAdapterBase
import com.twitter.timelines.prediction.common.aggregates.TimelinesAggregationConfig
import com.twitter.timelines.prediction.features.user_health.UserHealthFeatures
import scala.collection.JavaConverters._

object AuthorFeaturesAdapter extends TimelinesAdapterBase[af.AuthorFeatures] {

  private val Prefix = "original_author.timelines.original_author_aggregates."

  private val typedAggregateGroups =
    TimelinesAggregationConfig.originalAuthorAggregatesV1.buildTypedAggregateGroups()

  private val aggregateFeaturesRenameMap: Map[Feature[_], Feature[_]] =
    typedAggregateGroups.map(_.outputFeaturesToRenamedOutputFeatures(Prefix)).reduce(_ ++ _)

  private val prefixedOriginalAuthorAggregateFeatures =
    typedAggregateGroups.flatMap(_.allOutputFeatures).map { feature =>
      aggregateFeaturesRenameMap.getOrElse(feature, feature)
    }

  private val authorFeatures = prefixedOriginalAuthorAggregateFeatures ++ Seq(
    UserHealthFeatures.AuthorState,
    UserHealthFeatures.NumAuthorFollowers,
    UserHealthFeatures.NumAuthorConnectDays,
    UserHealthFeatures.NumAuthorConnect
  )

  private val aggregateFeatureContext: FeatureContext =
    new FeatureContext(typedAggregateGroups.flatMap(_.allOutputFeatures).asJava)

  private lazy val prefixedAggregateFeatureContext: FeatureContext =
    new FeatureContext(prefixedOriginalAuthorAggregateFeatures.asJava)

  override val getFeatureContext: FeatureContext = new FeatureContext(authorFeatures: _*)

  override val commonFeatures: Set[Feature[_]] = Set.empty

  private val compactDataRecordConverter = new CompactDataRecordConverter()

  override def adaptToDataRecords(
    authorFeatures: af.AuthorFeatures
  ): java.util.List[DataRecord] = {
    val dataRecord =
      if (authorFeatures.aggregates != null) {
        val originalAuthorAggregatesDataRecord =
          compactDataRecordConverter.compactDataRecordToDataRecord(authorFeatures.aggregates)

        DataRecordUtil.applyRename(
          originalAuthorAggregatesDataRecord,
          aggregateFeatureContext,
          prefixedAggregateFeatureContext,
          aggregateFeaturesRenameMap)
      } else new DataRecord

    if (authorFeatures.user_health != null) {
      val userHealth = authorFeatures.user_health

      if (userHealth.user_state != null) {
        dataRecord.setFeatureValue(
          UserHealthFeatures.AuthorState,
          userHealth.user_state.getValue.toLong
        )
      }

      dataRecord.setFeatureValue(
        UserHealthFeatures.NumAuthorFollowers,
        userHealth.num_followers.toDouble
      )

      dataRecord.setFeatureValue(
        UserHealthFeatures.NumAuthorConnectDays,
        userHealth.num_connect_days.toDouble
      )

      dataRecord.setFeatureValue(
        UserHealthFeatures.NumAuthorConnect,
        userHealth.num_connect.toDouble
      )
    }

    List(dataRecord).asJava
  }
}
