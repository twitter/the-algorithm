package com.twitter.home_mixer.functional_component.feature_hydrator.real_time_aggregates

import com.google.inject.name.Named
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.ListIdFeature
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.keyTransformD1
import com.twitter.home_mixer.module.RealtimeAggregateFeatureRepositoryModule.listIdFeature
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwitterListEngagementCache
import com.twitter.home_mixer_features.{thriftjava => t}
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.ReadCache
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.twitter.timelines.prediction.common.aggregates.real_time.TimelinesOnlineAggregationFeaturesOnlyConfig._
import javax.inject.Inject
import javax.inject.Singleton

object TwitterListEngagementRealTimeAggregateFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TwitterListEngagementRealTimeAggregateFeatureHydrator @Inject() (
  override val homeMixerFeatureService: t.HomeMixerFeatures.ServiceToClient,
  @Named(TwitterListEngagementCache) override val client: ReadCache[Long, DataRecord],
  override val statsReceiver: StatsReceiver)
    extends FlagBasedRealTimeAggregateBulkCandidateFeatureHydrator[Long] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TwitterListEngagementRealTimeAggregate")

  override val outputFeature: DataRecordInAFeature[TweetCandidate] =
    TwitterListEngagementRealTimeAggregateFeature

  override val aggregateGroups: Seq[AggregateGroup] =
    Seq(listEngagementRealTimeAggregatesProd)

  override val aggregateGroupToPrefix: Map[AggregateGroup, String] = Map(
    listEngagementRealTimeAggregatesProd -> "twitter_list.timelines.twitter_list_engagement_real_time_aggregates."
  )

  def serializeKey(key: Long): String = {
    keyTransformD1(listIdFeature)(key)
  }

  override def keysFromQueryAndCandidates(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[Long]] = candidates.map { candidate =>
    candidate.features.getTry(ListIdFeature).toOption.flatten
  }
}
