package com.twitter.home_mixer.product.scored_tweets.query_transformer

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.model.request.HasDeviceContext
import com.twitter.home_mixer.product.scored_tweets.query_feature_hydrator.FrsSeedUserIdsFeature
import com.twitter.home_mixer.product.scored_tweets.query_transformer.TimelineRankerFrsQueryTransformer._
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.quality_factor.HasQualityFactorStatus
import com.twitter.timelineranker.{thriftscala => t}
import com.twitter.timelines.common.model.TweetKindOption
import com.twitter.timelines.model.candidate.CandidateTweetSourceId
import com.twitter.util.Duration

object TimelineRankerFrsQueryTransformer {
  private val SinceDuration = 24.hours
  private val MaxTweetsToFetch = 100

  private val tweetKindOptions: TweetKindOption.ValueSet =
    TweetKindOption(includeOriginalTweetsAndQuotes = true)
}

case class TimelineRankerFrsQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContext
](
  override val candidatePipelineIdentifier: CandidatePipelineIdentifier,
  override val maxTweetsToFetch: Int = MaxTweetsToFetch,
  override val sinceDuration: Duration = SinceDuration)
    extends CandidatePipelineQueryTransformer[Query, t.RecapQuery]
    with TimelineRankerQueryTransformer[Query] {

  override val candidateTweetSourceId = CandidateTweetSourceId.FrsTweet
  override val skipVeryRecentTweets = false
  override val options = tweetKindOptions

  override def seedAuthorIds(query: Query): Option[Seq[Long]] = {
    query.features.flatMap(_.getOrElse(FrsSeedUserIdsFeature, None))
  }

  override def transform(input: Query): t.RecapQuery =
    buildTimelineRankerQuery(input).toThriftRecapQuery
}
