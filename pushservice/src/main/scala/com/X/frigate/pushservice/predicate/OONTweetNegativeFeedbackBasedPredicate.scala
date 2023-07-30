package com.X.frigate.pushservice.predicate

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base._
import com.X.frigate.common.rec_types.RecTypes
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.util.CandidateUtil
import com.X.hermit.predicate.NamedPredicate
import com.X.hermit.predicate.Predicate
import com.X.util.Future

object OONTweetNegativeFeedbackBasedPredicate {

  def ntabDislikeBasedPredicate(
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[
    PushCandidate with TweetCandidate with RecommendationType
  ] = {
    val name = "oon_tweet_dislike_based_predicate"
    val scopedStatsReceiver = stats.scope(name)
    val allOonCandidatesCounter = scopedStatsReceiver.counter("all_oon_candidates")
    val oonCandidatesImpressedCounter =
      scopedStatsReceiver.counter("oon_candidates_impressed")
    val filteredCandidatesCounter =
      scopedStatsReceiver.counter("filtered_oon_candidates")

    val ntabDislikeCountFeature =
      "tweet.magic_recs_tweet_real_time_aggregates_v2.pair.v2.magicrecs.realtime.is_ntab_disliked.any_feature.Duration.Top.count"
    val sentFeature =
      "tweet.magic_recs_tweet_real_time_aggregates_v2.pair.v2.magicrecs.realtime.is_sent.any_feature.Duration.Top.count"

    Predicate
      .fromAsync { candidate: PushCandidate with TweetCandidate with RecommendationType =>
        val target = candidate.target
        val crt = candidate.commonRecType
        val isOonCandidate = RecTypes.isOutOfNetworkTweetRecType(crt) ||
          RecTypes.outOfNetworkTopicTweetTypes.contains(crt)

        lazy val ntabDislikeCountThreshold =
          target.params(PushFeatureSwitchParams.TweetNtabDislikeCountThresholdParam)
        lazy val ntabDislikeRateThreshold =
          target.params(PushFeatureSwitchParams.TweetNtabDislikeRateThresholdParam)
        lazy val ntabDislikeCountThresholdForMrTwistly =
          target.params(PushFeatureSwitchParams.TweetNtabDislikeCountThresholdForMrTwistlyParam)
        lazy val ntabDislikeRateThresholdForMrTwistly =
          target.params(PushFeatureSwitchParams.TweetNtabDislikeRateThresholdForMrTwistlyParam)

        val isMrTwistly = CandidateUtil.isMrTwistlyCandidate(candidate)

        lazy val dislikeCount = candidate.numericFeatures.getOrElse(ntabDislikeCountFeature, 0.0)
        lazy val sentCount = candidate.numericFeatures.getOrElse(sentFeature, 0.0)
        lazy val dislikeRate = if (sentCount > 0) dislikeCount / sentCount else 0.0

        if (CandidateUtil.shouldApplyHealthQualityFilters(candidate) && isOonCandidate) {
          allOonCandidatesCounter.incr()
          val (countThreshold, rateThreshold) = if (isMrTwistly) {
            (ntabDislikeCountThresholdForMrTwistly, ntabDislikeRateThresholdForMrTwistly)
          } else {
            (ntabDislikeCountThreshold, ntabDislikeRateThreshold)
          }
          candidate.cachePredicateInfo(
            name + "_count",
            dislikeCount,
            countThreshold,
            dislikeCount > countThreshold)
          candidate.cachePredicateInfo(
            name + "_rate",
            dislikeRate,
            rateThreshold,
            dislikeRate > rateThreshold)
          if (dislikeCount > countThreshold && dislikeRate > rateThreshold) {
            filteredCandidatesCounter.incr()
            Future.False
          } else Future.True
        } else Future.True
      }
      .withStats(stats.scope(name))
      .withName(name)
  }
}
