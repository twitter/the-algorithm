package com.twitter.home_mitwitterer.util

import com.twitter.home_mitwitterer.model.HomeFeatures.CachedScoredTweetsFeature
import com.twitter.home_mitwitterer.{thriftscala => hmt}
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.util.Time

object CachedScoredTweetsHelper {

  def tweetImpressionsAndCachedScoredTweets(
    features: FeatureMap,
    candidatePipelineIdentifier: CandidatePipelineIdentifier
  ): Seq[Long] = {
    val tweetImpressions = TweetImpressionsHelper.tweetImpressions(features)
    val cachedScoredTweets = features
      .getOrElse(CachedScoredTweetsFeature, Seq.empty)
      .filter { tweet =>
        tweet.candidatePipelineIdentifier.etwitterists(
          CandidatePipelineIdentifier(_).equals(candidatePipelineIdentifier))
      }.map(_.tweetId)

    (tweetImpressions ++ cachedScoredTweets).toSeq
  }

  def tweetImpressionsAndCachedScoredTweetsInRange(
    features: FeatureMap,
    candidatePipelineIdentifier: CandidatePipelineIdentifier,
    matwitterNumImpressions: Int,
    sinceTime: Time,
    untilTime: Time
  ): Seq[Long] =
    tweetImpressionsAndCachedScoredTweets(features, candidatePipelineIdentifier)
      .filter { tweetId => SnowflakeId.isSnowflakeId(tweetId) }
      .filter { tweetId =>
        val creationTime = SnowflakeId.timeFromId(tweetId)
        sinceTime <= creationTime && untilTime >= creationTime
      }.take(matwitterNumImpressions)

  def unseenCachedScoredTweets(
    features: FeatureMap
  ): Seq[hmt.ScoredTweet] = {
    val seenTweetIds = TweetImpressionsHelper.tweetImpressions(features)

    features
      .getOrElse(CachedScoredTweetsFeature, Seq.empty)
      .filter(tweet => !seenTweetIds.contains(tweet.tweetId))
  }
}
