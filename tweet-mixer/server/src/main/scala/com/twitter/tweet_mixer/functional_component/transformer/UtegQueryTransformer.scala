package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.tweet_mixer.feature.RealGraphInNetworkScoresFeature
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.HasExcludedIds
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.recos.recos_common.thriftscala.SocialProofType
import com.twitter.recos.user_tweet_entity_graph.thriftscala.RecommendationType
import com.twitter.recos.user_tweet_entity_graph.thriftscala.TweetEntityDisplayLocation
import com.twitter.recos.user_tweet_entity_graph.{thriftscala => uteg}
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MaxTweetAgeHoursParam
import com.twitter.util.Time

object UtegQueryTransformer {
  private val MaxUserSocialProofSize = 10
  private val MaxTweetSocialProofSize = 10
  private val MinUserSocialProofSize = 1
  private val MaxTweetsToFetch = 800
  private val MaxExcludedTweets = 1500
}

case class UtegQueryTransformer[Query <: PipelineQuery with HasExcludedIds](
  candidatePipelineIdentifier: CandidatePipelineIdentifier)
    extends CandidatePipelineQueryTransformer[Query, uteg.RecommendTweetEntityRequest] {

  import UtegQueryTransformer._

  override def transform(query: Query): uteg.RecommendTweetEntityRequest = {
    val weightedFollowings = query.features
      .map(_.getOrElse(RealGraphInNetworkScoresFeature, Map.empty[Long, Double]))
      .getOrElse(Map.empty)

    val duration = query.params(MaxTweetAgeHoursParam)

    val sinceTime: Time = duration.ago

    val excludedTweetIds = query.excludedIds

    uteg.RecommendTweetEntityRequest(
      requesterId = query.getRequiredUserId,
      displayLocation = TweetEntityDisplayLocation.HomeTimeline,
      recommendationTypes = Seq(RecommendationType.Tweet),
      seedsWithWeights = weightedFollowings,
      maxResultsByType = Some(Map(RecommendationType.Tweet -> MaxTweetsToFetch)),
      maxTweetAgeInMillis = Some(sinceTime.untilNow.inMillis),
      excludedTweetIds = Some(excludedTweetIds.toSeq),
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
