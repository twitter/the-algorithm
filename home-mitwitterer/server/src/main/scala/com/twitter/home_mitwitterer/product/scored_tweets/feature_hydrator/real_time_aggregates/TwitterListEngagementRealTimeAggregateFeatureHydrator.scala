package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.real_time_aggregates

import com.google.inject.name.Named
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.model.HomeFeatures.TwitterListIdFeature
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.TwitterListEngagementCache
import com.twitter.ml.api.DataRecord
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.ReadCache
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.twitter.timelines.prediction.common.aggregates.real_time.TimelinesOnlineAggregationFeaturesOnlyConfig._
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

object TwitterListEngagementRealTimeAggregateFeature
    etwittertends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TwitterListEngagementRealTimeAggregateFeatureHydrator @Inject() (
  @Named(TwitterListEngagementCache) override val client: ReadCache[Long, DataRecord],
  override val statsReceiver: StatsReceiver)
    etwittertends BaseRealTimeAggregateBulkCandidateFeatureHydrator[Long] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TwitterListEngagementRealTimeAggregate")

  override val outputFeature: DataRecordInAFeature[TweetCandidate] =
    TwitterListEngagementRealTimeAggregateFeature

  override val aggregateGroups: Seq[AggregateGroup] = Seq(
    listEngagementRealTimeAggregatesProd
  )

  override val aggregateGroupToPrefitwitter: Map[AggregateGroup, String] = Map(
    listEngagementRealTimeAggregatesProd -> "twitter_list.timelines.twitter_list_engagement_real_time_aggregates."
  )

  override def keysFromQueryAndCandidates(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[Long]] = {
    candidates.map { candidate =>
      candidate.features
        .getTry(TwitterListIdFeature)
        .toOption
        .flatten
    }
  }
}
