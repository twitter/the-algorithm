package com.X.frigate.pushservice.predicate

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base._
import com.X.frigate.common.rec_types.RecTypes
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.params.PushConstants
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.util.CandidateUtil
import com.X.hermit.predicate.NamedPredicate
import com.X.hermit.predicate.Predicate
import com.X.util.Future

object TweetEngagementRatioPredicate {

  def QTtoNtabClickBasedPredicate(
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[
    PushCandidate with TweetCandidate with RecommendationType
  ] = {
    val name = "oon_tweet_engagement_filter_qt_to_ntabclick_ratio_based_predicate"
    val scopedStatsReceiver = stats.scope(name)
    val allOonCandidatesCounter = scopedStatsReceiver.counter("all_oon_candidates")
    val filteredCandidatesCounter =
      scopedStatsReceiver.counter("filtered_oon_candidates")

    val quoteCountFeature =
      "tweet.core.tweet_counts.quote_count"
    val ntabClickCountFeature =
      "tweet.magic_recs_tweet_real_time_aggregates_v2.pair.v2.magicrecs.realtime.is_ntab_clicked.any_feature.Duration.Top.count"

    Predicate
      .fromAsync { candidate: PushCandidate with TweetCandidate with RecommendationType =>
        val target = candidate.target
        val crt = candidate.commonRecType
        val isOonCandidate = RecTypes.isOutOfNetworkTweetRecType(crt) ||
          RecTypes.outOfNetworkTopicTweetTypes.contains(crt)

        lazy val QTtoNtabClickRatioThreshold =
          target.params(PushFeatureSwitchParams.TweetQTtoNtabClickRatioThresholdParam)
        lazy val quoteCount = candidate.numericFeatures.getOrElse(quoteCountFeature, 0.0)
        lazy val ntabClickCount = candidate.numericFeatures.getOrElse(ntabClickCountFeature, 0.0)
        lazy val quoteRate = if (ntabClickCount > 0) quoteCount / ntabClickCount else 1.0

        if (isOonCandidate) allOonCandidatesCounter.incr()
        if (CandidateUtil.shouldApplyHealthQualityFilters(candidate) && isOonCandidate) {
          val ntabClickThreshold = 1000
          candidate.cachePredicateInfo(
            name + "_count",
            ntabClickCount,
            ntabClickThreshold,
            ntabClickCount >= ntabClickThreshold)
          candidate.cachePredicateInfo(
            name + "_ratio",
            quoteRate,
            QTtoNtabClickRatioThreshold,
            quoteRate < QTtoNtabClickRatioThreshold)
          if (ntabClickCount >= ntabClickThreshold && quoteRate < QTtoNtabClickRatioThreshold) {
            filteredCandidatesCounter.incr()
            Future.False
          } else Future.True
        } else Future.True
      }
      .withStats(stats.scope(name))
      .withName(name)
  }

  def TweetReplyLikeRatioPredicate(
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetCandidate] = {
    val name = "tweet_reply_like_ratio"
    val scopedStatsReceiver = stats.scope(name)
    val allCandidatesCounter = scopedStatsReceiver.counter("all_candidates")
    val filteredCandidatesCounter = scopedStatsReceiver.counter("filtered_candidates")
    val bucketedCandidatesCounter = scopedStatsReceiver.counter("bucketed_candidates")

    Predicate
      .fromAsync { candidate: PushCandidate =>
        allCandidatesCounter.incr()
        val target = candidate.target
        val likeCount = candidate.numericFeatures
          .getOrElse(PushConstants.TweetLikesFeatureName, 0.0)
        val replyCount = candidate.numericFeatures
          .getOrElse(PushConstants.TweetRepliesFeatureName, 0.0)
        val ratio = replyCount / likeCount.max(1)
        val isOonCandidate = RecTypes.isOutOfNetworkTweetRecType(candidate.commonRecType) ||
          RecTypes.outOfNetworkTopicTweetTypes.contains(candidate.commonRecType)

        if (isOonCandidate
          && CandidateUtil.shouldApplyHealthQualityFilters(candidate)
          && replyCount > target.params(
            PushFeatureSwitchParams.TweetReplytoLikeRatioReplyCountThreshold)) {
          bucketedCandidatesCounter.incr()
          if (ratio > target.params(
              PushFeatureSwitchParams.TweetReplytoLikeRatioThresholdLowerBound)
            && ratio < target.params(
              PushFeatureSwitchParams.TweetReplytoLikeRatioThresholdUpperBound)) {
            filteredCandidatesCounter.incr()
            Future.False
          } else {
            Future.True
          }
        } else {
          Future.True
        }
      }
      .withStats(stats.scope(s"predicate_$name"))
      .withName(name)
  }
}
