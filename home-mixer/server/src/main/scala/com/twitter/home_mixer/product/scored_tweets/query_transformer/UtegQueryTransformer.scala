package com.twitter.home_mixer.product.scored_tweets.query_transformer

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.model.HomeFeatures.RealGraphInNetworkScoresFeature
import com.twitter.home_mixer.model.request.HasDeviceContext
import com.twitter.home_mixer.product.scored_tweets.query_transformer.UtegQueryTransformer._
import com.twitter.home_mixer.util.CachedScoredTweetsHelper
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.quality_factor.HasQualityFactorStatus
import com.twitter.recos.recos_common.thriftscala.SocialProofType
import com.twitter.recos.user_tweet_entity_graph.thriftscala.RecommendationType
import com.twitter.recos.user_tweet_entity_graph.thriftscala.TweetEntityDisplayLocation
import com.twitter.recos.user_tweet_entity_graph.{thriftscala => uteg}
import com.twitter.util.Time

object UtegQueryTransformer {
  private val MaxUserSocialProofSize = 10
  private val MaxTweetSocialProofSize = 10
  private val MinUserSocialProofSize = 1
  private val DefaultSinceDuration = 24.hours
  private val MaxTweetsToFetch = 800
  private val MaxExcludedTweets = 1500
}

case class UtegQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContext
](
  candidatePipelineIdentifier: CandidatePipelineIdentifier)
    extends CandidatePipelineQueryTransformer[Query, uteg.RecommendTweetEntityRequest] {

  override def transform(query: Query): uteg.RecommendTweetEntityRequest = {

    val weightedFollowings = query.features
      .map(_.getOrElse(RealGraphInNetworkScoresFeature, Map.empty[Long, Double]))
      .getOrElse(Map.empty)

    val sinceTime: Time = DefaultSinceDuration.ago
    val untilTime: Time = Time.now

    val excludedTweetIds = query.features.map { featureMap =>
      CachedScoredTweetsHelper.tweetImpressionsAndCachedScoredTweetsInRange(
        featureMap,
        candidatePipelineIdentifier,
        MaxExcludedTweets,
        sinceTime,
        untilTime)
    }

    uteg.RecommendTweetEntityRequest(
      requesterId = query.getRequiredUserId,
      displayLocation = TweetEntityDisplayLocation.HomeTimeline,
      recommendationTypes = Seq(RecommendationType.Tweet),
      seedsWithWeights = weightedFollowings,
      maxResultsByType = Some(Map(RecommendationType.Tweet -> MaxTweetsToFetch)),
      maxTweetAgeInMillis = Some(sinceTime.untilNow.inMillis),
      excludedTweetIds = excludedTweetIds,
      maxUserSocialProofSize = Some(MaxUserSocialProofSize),
      maxTweetSocialProofSize = Some(MaxTweetSocialProofSize),
      minUserSocialProofSizes = Some(Map(RecommendationType.Tweet -> MinUserSocialProofSize)),
      socialProofTypes = Some(Seq(SocialProofType.Favorite)),
      tweetAuthors = None,
      maxEngagementAgeInMillis = None,
      tweetTypes = None
    )
  }
}
