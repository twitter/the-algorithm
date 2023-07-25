package com.twitter.home_mixer.product.scored_tweets.feature_hydrator.real_time_aggregates

import com.google.inject.name.Named
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.param.HomeMixerInjectionNames.UserEngagementCache
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.ReadCache
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.twitter.timelines.prediction.common.aggregates.real_time.TimelinesOnlineAggregationFeaturesOnlyConfig._
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
}
