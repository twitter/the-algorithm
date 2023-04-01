package com.twitter.home_mixer.product.scored_tweets.query_transformer

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.model.HomeFeatures.RealGraphInNetworkScoresFeature
import com.twitter.home_mixer.model.request.HasDeviceContext
import com.twitter.home_mixer.product.scored_tweets.query_transformer.TimelineRankerUtegQueryTransformer._
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.quality_factor.HasQualityFactorStatus
import com.twitter.timelinemixer.clients.timelineranker.EarlybirdScoringModels
import com.twitter.timelinemixer.clients.timelineranker.EarlybirdScoringModelsId
import com.twitter.timelineranker.{model => tlr}
import com.twitter.timelineranker.{thriftscala => t}
import com.twitter.timelines.common.model.TweetKindOption
import com.twitter.timelines.model.UserId
import com.twitter.timelines.model.candidate.CandidateTweetSourceId
import com.twitter.util.Duration

object TimelineRankerUtegQueryTransformer {
  private val SinceDuration = 24.hours
  private val MaxTweetsToFetch = 500
  private val MaxUtegCandidates = 800

  private val TensorflowModel = "timelines_rectweet_replica"

  private val tweetKindOptions = TweetKindOption(includeReplies = true)

  def utegEarlybirdModels =
    EarlybirdScoringModels.fromEnum(EarlybirdScoringModelsId.UnifiedEngagementRectweet)
}

case class TimelineRankerUtegQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContext
](
  override val candidatePipelineIdentifier: CandidatePipelineIdentifier,
  override val maxTweetsToFetch: Int = MaxTweetsToFetch,
  override val sinceDuration: Duration = SinceDuration)
    extends CandidatePipelineQueryTransformer[Query, t.UtegLikedByTweetsQuery]
    with TimelineRankerQueryTransformer[Query] {

  override val candidateTweetSourceId = CandidateTweetSourceId.RecommendedTweet
  override val skipVeryRecentTweets = true
  override val earlybirdModels = utegEarlybirdModels
  override val tensorflowModel = Some(TensorflowModel)

  override def utegLikedByTweetsOptions(input: Query): Option[tlr.UtegLikedByTweetsOptions] = Some(
    tlr.UtegLikedByTweetsOptions(
      utegCount = MaxUtegCandidates,
      isInNetwork = false,
      weightedFollowings = input.features
        .map(_.getOrElse(RealGraphInNetworkScoresFeature, Map.empty[UserId, Double]))
        .getOrElse(Map.empty)
    )
  )

  override def transform(input: Query): t.UtegLikedByTweetsQuery =
    buildTimelineRankerQuery(input).toThriftUtegLikedByTweetsQuery
}
