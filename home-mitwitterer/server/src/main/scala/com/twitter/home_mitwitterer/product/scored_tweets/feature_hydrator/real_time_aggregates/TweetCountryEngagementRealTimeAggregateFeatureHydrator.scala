package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.real_time_aggregates

import com.google.inject.name.Named
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.TweetCountryEngagementCache
import com.twitter.home_mitwitterer.util.CandidatesUtil
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

object TweetCountryEngagementRealTimeAggregateFeature
    etwittertends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TweetCountryEngagementRealTimeAggregateFeatureHydrator @Inject() (
  @Named(TweetCountryEngagementCache) override val client: ReadCache[(Long, String), DataRecord],
  override val statsReceiver: StatsReceiver)
    etwittertends BaseRealTimeAggregateBulkCandidateFeatureHydrator[(Long, String)] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TweetCountryEngagementRealTimeAggregate")

  override val outputFeature: DataRecordInAFeature[TweetCandidate] =
    TweetCountryEngagementRealTimeAggregateFeature

  override val aggregateGroups: Seq[AggregateGroup] = Seq(
    tweetCountryRealTimeAggregates,
    tweetCountryPrivateEngagementsRealTimeAggregates
  )

  override val aggregateGroupToPrefitwitter: Map[AggregateGroup, String] = Map(
    tweetCountryRealTimeAggregates -> "tweet-country_code.timelines.tweet_country_engagement_real_time_aggregates.",
    tweetCountryPrivateEngagementsRealTimeAggregates -> "tweet-country_code.timelines.tweet_country_private_engagement_real_time_aggregates."
  )

  override def keysFromQueryAndCandidates(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[Option[(Long, String)]] = {
    val countryCode = query.clientContetwittert.countryCode
    candidates.map { candidate =>
      val originalTweetId = CandidatesUtil.getOriginalTweetId(candidate)
      countryCode.map((originalTweetId, _))
    }
  }
}
