package com.twitter.home_mixer.functional_component.feature_hydrator.real_time_aggregates

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.TopicIdSocialContextFeature
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.keyTransformD1
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.topicIdFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableTopicBasedRealTimeAggregateFeatureHydratorParam
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TopicEngagementCache
import com.twitter.home_mixer_features.{thriftjava => t}
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.ReadCache
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.twitter.timelines.prediction.common.aggregates.real_time.TimelinesOnlineAggregationFeaturesOnlyConfig._
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

object TopicEngagementRealTimeAggregateFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TopicEngagementRealTimeAggregateFeatureHydrator @Inject() (
  override val homeMixerFeatureService: t.HomeMixerFeatures.ServiceToClient,
  @Named(TopicEngagementCache) override val client: ReadCache[Long, DataRecord],
  override val statsReceiver: StatsReceiver)
    extends FlagBasedRealTimeAggregateBulkCandidateFeatureHydrator[Long]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TopicEngagementRealTimeAggregate")

  override val outputFeature: DataRecordInAFeature[TweetCandidate] =
    TopicEngagementRealTimeAggregateFeature

  override val aggregateGroups: Seq[AggregateGroup] = Seq(
    topicEngagementRealTimeAggregatesProd,
    topicEngagement24HourRealTimeAggregatesProd,
    topicShareEngagementsRealTimeAggregates
  )

  override val aggregateGroupToPrefix: Map[AggregateGroup, String] = Map(
    topicEngagement24HourRealTimeAggregatesProd -> "topic.timelines.topic_engagement_24_hour_real_time_aggregates.",
    topicShareEngagementsRealTimeAggregates -> "topic.timelines.topic_share_engagements_real_time_aggregates."
  )

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableTopicBasedRealTimeAggregateFeatureHydratorParam)

  def serializeKey(key: Long): String = {
    keyTransformD1(topicIdFeature)(key)
  }

  override def keysFromQueryAndCandidates(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[Long]] = {
    candidates.map { candidate =>
      candidate.features
        .getTry(TopicIdSocialContextFeature)
        .toOption
        .flatten
    }
  }
}
