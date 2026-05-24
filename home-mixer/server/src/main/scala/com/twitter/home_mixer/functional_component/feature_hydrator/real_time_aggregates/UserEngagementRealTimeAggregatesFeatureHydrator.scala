package com.twitter.home_mixer.functional_component.feature_hydrator.real_time_aggregates

import com.google.inject.name.Named
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.userIdFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableUserRTAMhFallbackParam
import com.twitter.home_mixer.param.HomeGlobalParams.EnableUserRTAMhOnlyParam
import com.twitter.home_mixer.param.HomeMixerInjectionNames.RTAManhattanStore
import com.twitter.home_mixer.param.HomeMixerInjectionNames.UserEngagementCache
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.feature_hydrator.query.real_time_aggregates.BaseRealTimeAggregateQueryFeatureHydrator
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.cache.ReadCache
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregationKey
import com.twitter.timelines.prediction.common.aggregates.real_time.TimelinesOnlineAggregationFeaturesOnlyConfig._
import com.twitter.util.Future
import com.twitter.util.Try
import com.twitter.timelines.realtime_aggregates.{thriftscala => thrift}
import javax.inject.Inject
import javax.inject.Singleton

object UserEngagementRealTimeAggregateFeature
    extends DataRecordInAFeature[PipelineQuery]
    with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class UserEngagementRealTimeAggregatesFeatureHydrator @Inject() (
  @Named(UserEngagementCache) override val client: ReadCache[Long, DataRecord],
  @Named(RTAManhattanStore) mhClient: Option[ReadableStore[thrift.AggregationKey, DataRecord]],
  override val statsReceiver: StatsReceiver)
    extends BaseRealTimeAggregateQueryFeatureHydrator[Long] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserEngagementRealTimeAggregates")

  override val outputFeature: DataRecordInAFeature[PipelineQuery] =
    UserEngagementRealTimeAggregateFeature

  val aggregateGroups: Seq[AggregateGroup] = Seq(
    userEngagementRealTimeAggregatesProd,
    userShareEngagementsRealTimeAggregates,
    userBCEDwellEngagementsRealTimeAggregates,
    userEngagement48HourRealTimeAggregatesProd,
    userNegativeEngagementAuthorUserState72HourRealTimeAggregates,
    userNegativeEngagementAuthorUserStateRealTimeAggregates,
    userProfileEngagementRealTimeAggregates,
  )

  override val aggregateGroupToPrefix: Map[AggregateGroup, String] = Map(
    userShareEngagementsRealTimeAggregates -> "user.timelines.user_share_engagements_real_time_aggregates.",
    userBCEDwellEngagementsRealTimeAggregates -> "user.timelines.user_bce_dwell_engagements_real_time_aggregates.",
    userEngagement48HourRealTimeAggregatesProd -> "user.timelines.user_engagement_48_hour_real_time_aggregates.",
    userNegativeEngagementAuthorUserState72HourRealTimeAggregates -> "user.timelines.user_negative_engagement_author_user_state_72_hour_real_time_aggregates.",
    userProfileEngagementRealTimeAggregates -> "user.timelines.user_profile_engagement_real_time_aggregates."
  )

  override def keysFromQueryAndCandidates(query: PipelineQuery): Option[Long] = {
    Some(query.getRequiredUserId)
  }

  val EmptyDataRecord = new DataRecord

  override def fetchAndConstructDataRecords(
    possiblyKeys: Seq[Option[Long]]
  ): Future[Seq[Try[DataRecord]]] = {
    val keys = possiblyKeys.flatten.map { k =>
      val ak = AggregationKey(Map(userIdFeature -> k), Map.empty)
      thrift.AggregationKey(
        ak.discreteFeaturesById,
        ak.textFeaturesById
      )
    }
    Future
      .collect {
        keys.map { key =>
          statsReceiver.scope("mhUserRTA").counter("mh_called").incr()
          val result = mhClient.get.get(key)
          result.map { drOpt =>
            if (drOpt.isEmpty) statsReceiver.scope("mhUserRTA").counter("empty").incr()
            else statsReceiver.scope("mhUserRTA").counter("non_empty").incr()
            Try(drOpt.map(postTransformer).getOrElse(EmptyDataRecord))
          }
        }
      }
  }

  def convert(key: Long): thrift.AggregationKey = {
    val ak = AggregationKey(Map(userIdFeature -> key), Map.empty)
    thrift.AggregationKey(
      ak.discreteFeaturesById,
      ak.textFeaturesById
    )
  }

  def fetchAndConstructDataRecordFromMh(
    possiblyKeys: Seq[Option[Long]]
  ): Future[Seq[Try[DataRecord]]] = {
    Future
      .collect {
        possiblyKeys.flatten
          .map {
            convert(_)
          }
          .grouped(64).map { keyGroup =>
            val results = mhClient.get.multiGet(keyGroup.toSet)
            Future.collect(keyGroup.flatMap(results.get)).map { drSeq =>
              drSeq.map { drOpt =>
                if (drOpt.isEmpty) statsReceiver.scope("mhUserRTA").counter("empty").incr()
                else statsReceiver.scope("mhUserRTA").counter("non_empty").incr()
                Try(drOpt.map(postTransformer).getOrElse(EmptyDataRecord))
              }
            }
          }.toSeq
      }.map(_.flatten)
  }

  def getFetchFunc(
    query: PipelineQuery
  ): Future[Seq[Try[DataRecord]]] = {
    val fetchFromMhOnly = query.params.getBoolean(EnableUserRTAMhOnlyParam)
    val fetchFromMhAsFallBack = query.params.getBoolean(EnableUserRTAMhFallbackParam)
    val possiblyKeys = Seq(keysFromQueryAndCandidates(query))
    val stats = statsReceiver.scope("user_author_real_time_rta")
    if (fetchFromMhOnly) {
      fetchAndConstructDataRecordFromMh(possiblyKeys)
    } else if (fetchFromMhAsFallBack) {
      fetchAndConstructDataRecordsWithFallback(
        possiblyKeys,
        stats,
        fetchAndConstructDataRecords,
        fetchAndConstructDataRecordFromMh,
      )
    } else {
      fetchAndConstructDataRecords(possiblyKeys)
    }
  }

  override def hydrate(
    query: PipelineQuery
  ): Stitch[FeatureMap] = OffloadFuturePools.offloadFuture {
    getFetchFunc(query).map { dataRecords =>
      val featureMaps = dataRecords.map { dataRecord =>
        FeatureMapBuilder().add(outputFeature, dataRecord).build()
      }
      featureMaps.head
    }
  }
}
