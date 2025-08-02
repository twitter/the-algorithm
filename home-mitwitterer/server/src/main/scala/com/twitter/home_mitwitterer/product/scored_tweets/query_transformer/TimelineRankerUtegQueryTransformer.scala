package com.twitter.home_mitwitterer.product.scored_tweets.query_transformer

import com.twitter.conversions.DurationOps._
import com.twitter.home_mitwitterer.model.HomeFeatures.RealGraphInNetworkScoresFeature
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.home_mitwitterer.product.scored_tweets.param.ScoredTweetsParam
import com.twitter.home_mitwitterer.product.scored_tweets.query_transformer.TimelineRankerUtegQueryTransformer._
import com.twitter.home_mitwitterer.util.earlybird.EarlybirdRequestUtil
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.quality_factor.HasQualityFactorStatus
import com.twitter.timelineranker.{model => tlr}
import com.twitter.timelineranker.{thriftscala => t}
import com.twitter.timelines.common.model.TweetKindOption
import com.twitter.timelines.earlybird.common.options.EarlybirdScoringModelConfig
import com.twitter.timelines.model.UserId
import com.twitter.timelines.model.candidate.CandidateTweetSourceId

object TimelineRankerUtegQueryTransformer {
  private val SinceDuration = 24.hours
  private val MatwitterTweetsToFetch = 300
  private val MatwitterUtegCandidates = 800

  private val tweetKindOptions =
    TweetKindOption(includeOriginalTweetsAndQuotes = true, includeReplies = true)

  def utegEarlybirdModels: Seq[EarlybirdScoringModelConfig] =
    EarlybirdRequestUtil.EarlybirdScoringModels.UnifiedEngagementRectweet
}

case class TimelineRankerUtegQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContetwittert
](
  override val candidatePipelineIdentifier: CandidatePipelineIdentifier,
  override val matwitterTweetsToFetch: Int = MatwitterTweetsToFetch)
    etwittertends CandidatePipelineQueryTransformer[Query, t.UtegLikedByTweetsQuery]
    with TimelineRankerQueryTransformer[Query] {

  override val candidateTweetSourceId = CandidateTweetSourceId.RecommendedTweet
  override val options = tweetKindOptions
  override val earlybirdModels = utegEarlybirdModels
  override def getTensorflowModel(query: Query): Option[String] = {
    Some(query.params(ScoredTweetsParam.EarlybirdTensorflowModel.UtegParam))
  }

  override def utegLikedByTweetsOptions(input: Query): Option[tlr.UtegLikedByTweetsOptions] = Some(
    tlr.UtegLikedByTweetsOptions(
      utegCount = MatwitterUtegCandidates,
      isInNetwork = false,
      weightedFollowings = input.features
        .map(_.getOrElse(RealGraphInNetworkScoresFeature, Map.empty[UserId, Double]))
        .getOrElse(Map.empty)
    )
  )

  override def transform(input: Query): t.UtegLikedByTweetsQuery =
    buildTimelineRankerQuery(input, SinceDuration).toThriftUtegLikedByTweetsQuery
}
