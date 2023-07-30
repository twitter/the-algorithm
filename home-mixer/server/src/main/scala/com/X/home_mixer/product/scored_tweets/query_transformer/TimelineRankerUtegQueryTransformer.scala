package com.X.home_mixer.product.scored_tweets.query_transformer

import com.X.conversions.DurationOps._
import com.X.home_mixer.model.HomeFeatures.RealGraphInNetworkScoresFeature
import com.X.home_mixer.model.request.HasDeviceContext
import com.X.home_mixer.product.scored_tweets.param.ScoredTweetsParam
import com.X.home_mixer.product.scored_tweets.query_transformer.TimelineRankerUtegQueryTransformer._
import com.X.home_mixer.util.earlybird.EarlybirdRequestUtil
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.quality_factor.HasQualityFactorStatus
import com.X.timelineranker.{model => tlr}
import com.X.timelineranker.{thriftscala => t}
import com.X.timelines.common.model.TweetKindOption
import com.X.timelines.earlybird.common.options.EarlybirdScoringModelConfig
import com.X.timelines.model.UserId
import com.X.timelines.model.candidate.CandidateTweetSourceId

object TimelineRankerUtegQueryTransformer {
  private val SinceDuration = 24.hours
  private val MaxTweetsToFetch = 300
  private val MaxUtegCandidates = 800

  private val tweetKindOptions =
    TweetKindOption(includeOriginalTweetsAndQuotes = true, includeReplies = true)

  def utegEarlybirdModels: Seq[EarlybirdScoringModelConfig] =
    EarlybirdRequestUtil.EarlybirdScoringModels.UnifiedEngagementRectweet
}

case class TimelineRankerUtegQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContext
](
  override val candidatePipelineIdentifier: CandidatePipelineIdentifier,
  override val maxTweetsToFetch: Int = MaxTweetsToFetch)
    extends CandidatePipelineQueryTransformer[Query, t.UtegLikedByTweetsQuery]
    with TimelineRankerQueryTransformer[Query] {

  override val candidateTweetSourceId = CandidateTweetSourceId.RecommendedTweet
  override val options = tweetKindOptions
  override val earlybirdModels = utegEarlybirdModels
  override def getTensorflowModel(query: Query): Option[String] = {
    Some(query.params(ScoredTweetsParam.EarlybirdTensorflowModel.UtegParam))
  }

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
    buildTimelineRankerQuery(input, SinceDuration).toThriftUtegLikedByTweetsQuery
}
