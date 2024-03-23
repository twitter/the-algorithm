package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.real_time_aggregates

import com.google.inject.name.Named
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.TweetEngagementCache
import com.ExTwitter.home_mixer.util.CandidatesUtil
import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.ExTwitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.servo.cache.ReadCache
import com.ExTwitter.timelines.data_processing.ml_util.aggregation_framework.AggregateGroup
import com.ExTwitter.timelines.prediction.common.aggregates.real_time.TimelinesOnlineAggregationFeaturesOnlyConfig._
import javax.inject.Inject
import javax.inject.Singleton

object TweetEngagementRealTimeAggregateFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TweetEngagementRealTimeAggregateFeatureHydrator @Inject() (
  @Named(TweetEngagementCache) override val client: ReadCache[Long, DataRecord],
  override val statsReceiver: StatsReceiver)
    extends BaseRealTimeAggregateBulkCandidateFeatureHydrator[Long] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TweetEngagementRealTimeAggregate")

  override val outputFeature: DataRecordInAFeature[TweetCandidate] =
    TweetEngagementRealTimeAggregateFeature

  override val aggregateGroups: Seq[AggregateGroup] = Seq(
    tweetEngagement30MinuteCountsProd,
    tweetEngagementTotalCountsProd,
    tweetEngagementUserStateRealTimeAggregatesProd,
    tweetNegativeEngagementUserStateRealTimeAggregates,
    tweetNegativeEngagement6HourCounts,
    tweetNegativeEngagementTotalCounts,
    tweetShareEngagementsRealTimeAggregates,
    tweetBCEDwellEngagementsRealTimeAggregates
  )

  override val aggregateGroupToPrefix: Map[AggregateGroup, String] = Map(
    tweetShareEngagementsRealTimeAggregates -> "original_tweet.timelines.tweet_share_engagements_real_time_aggregates.",
    tweetBCEDwellEngagementsRealTimeAggregates -> "original_tweet.timelines.tweet_bce_dwell_engagements_real_time_aggregates."
  )

  override def keysFromQueryAndCandidates(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[Long]] = {
    candidates
      .map(candidate => Some(CandidatesUtil.getOriginalTweetId(candidate)))
  }
}
