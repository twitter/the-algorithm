package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.home_mixer.functional_component.feature_hydrator.BroadcastStateFeature
import com.twitter.home_mixer.functional_component.feature_hydrator.SpaceStateFeature
import com.twitter.home_mixer.functional_component.scorer.FeedbackFatigueScorer
import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam._
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.util.MtlNormalizer
import com.twitter.timelineservice.{thriftscala => tls}
import com.twitter.ubs.{thriftscala => ubs}

trait RescoringFactorProvider {

  def selector(query: PipelineQuery, candidate: CandidateWithFeatures[TweetCandidate]): Boolean

  def factor(
    query: PipelineQuery,
    candidate: CandidateWithFeatures[TweetCandidate]
  ): Double

  def apply(
    query: PipelineQuery,
    candidate: CandidateWithFeatures[TweetCandidate],
  ): Double = if (selector(query, candidate)) factor(query, candidate) else 1.0
}

case class RescoreListwise(listwiseRescoringMap: Map[Long, Double])
    extends RescoringFactorProvider {

  override def selector(
    query: PipelineQuery,
    candidate: CandidateWithFeatures[TweetCandidate]
  ): Boolean = listwiseRescoringMap.contains(candidate.candidate.id)

  override def factor(
    query: PipelineQuery,
    candidate: CandidateWithFeatures[TweetCandidate]
  ): Double = listwiseRescoringMap(candidate.candidate.id)
}

/**
 * Re-scoring multiplier to apply to out-of-network tweets
 */
object RescoreOutOfNetwork extends RescoringFactorProvider {

  def selector(query: PipelineQuery, candidate: CandidateWithFeatures[TweetCandidate]): Boolean =
    !candidate.features.getOrElse(InNetworkFeature, false)

  def factor(
    query: PipelineQuery,
    candidate: CandidateWithFeatures[TweetCandidate]
  ): Double = query.params(OutOfNetworkScaleFactorParam)
}

/**
 * Re-scoring multiplier to apply to reply candidates
 */
object RescoreReplies extends RescoringFactorProvider {

  def selector(query: PipelineQuery, candidate: CandidateWithFeatures[TweetCandidate]): Boolean =
    candidate.features.getOrElse(InReplyToTweetIdFeature, None).isDefined

  def factor(
    query: PipelineQuery,
    candidate: CandidateWithFeatures[TweetCandidate]
  ): Double = query.params(ReplyScaleFactorParam)
}

/**
 * Re-scoring multiplier to calibrate multi-tasks learning model prediction
 */
case class RescoreMTLNormalization(mtlNormalizer: MtlNormalizer) extends RescoringFactorProvider {

  def selector(query: PipelineQuery, candidate: CandidateWithFeatures[TweetCandidate]): Boolean =
    query.params(MtlNormalization.EnableMtlNormalizationParam)

  def factor(
    query: PipelineQuery,
    candidate: CandidateWithFeatures[TweetCandidate]
  ): Double = mtlNormalizer(
    attribute = candidate.features.getOrElse(AuthorFollowersFeature, None),
    retweet = candidate.features.getOrElse(SourceTweetIdFeature, None).isDefined,
    reply = candidate.features.getOrElse(InReplyToTweetIdFeature, None).isDefined
  )
}

case class RescoreFeedbackFatigue(query: PipelineQuery) extends RescoringFactorProvider {

  def selector(query: PipelineQuery, candidate: CandidateWithFeatures[TweetCandidate]): Boolean =
    true

  private val feedbackEntriesByEngagementType =
    query.features
      .getOrElse(FeatureMap.empty).getOrElse(FeedbackHistoryFeature, Seq.empty)
      .filter { entry =>
        val timeSinceFeedback = query.queryTime.minus(entry.timestamp)
        timeSinceFeedback < FeedbackFatigueScorer.DurationForDiscounting &&
        entry.feedbackType == tls.FeedbackType.SeeFewer
      }.groupBy(_.engagementType)

  private val authorsToDiscount =
    FeedbackFatigueScorer.getUserDiscounts(
      query.queryTime,
      feedbackEntriesByEngagementType.getOrElse(tls.FeedbackEngagementType.Tweet, Seq.empty))

  private val likersToDiscount =
    FeedbackFatigueScorer.getUserDiscounts(
      query.queryTime,
      feedbackEntriesByEngagementType.getOrElse(tls.FeedbackEngagementType.Like, Seq.empty))

  private val followersToDiscount =
    FeedbackFatigueScorer.getUserDiscounts(
      query.queryTime,
      feedbackEntriesByEngagementType.getOrElse(tls.FeedbackEngagementType.Follow, Seq.empty))

  private val retweetersToDiscount =
    FeedbackFatigueScorer.getUserDiscounts(
      query.queryTime,
      feedbackEntriesByEngagementType.getOrElse(tls.FeedbackEngagementType.Retweet, Seq.empty))

  def factor(
    query: PipelineQuery,
    candidate: CandidateWithFeatures[TweetCandidate]
  ): Double = {
    FeedbackFatigueScorer.getScoreMultiplier(
      candidate,
      authorsToDiscount,
      likersToDiscount,
      followersToDiscount,
      retweetersToDiscount
    )
  }
}

/**
 * Disabled in production
 */
object RescoreLiveContent extends RescoringFactorProvider {

  private val MinFollowers = 1000000

  def selector(query: PipelineQuery, candidate: CandidateWithFeatures[TweetCandidate]): Boolean = {
    (
      candidate.features.getOrElse(SpaceStateFeature, None).contains(ubs.BroadcastState.Running) ||
      candidate.features.getOrElse(BroadcastStateFeature, None).contains(ubs.BroadcastState.Running)
    ) &&
    candidate.features.getOrElse(InNetworkFeature, false) &&
    candidate.features.getOrElse(AuthorFollowersFeature, None).exists(_ > MinFollowers)
  }

  def factor(
    query: PipelineQuery,
    candidate: CandidateWithFeatures[TweetCandidate]
  ): Double = query.params(LiveContentScaleFactorParam)
}
