package com.twitter.home_mixer.product.scored_tweets.query_transformer

import com.twitter.conversions.DurationOps._
import com.twitter.core_workflows.user_model.{thriftscala => um}
import com.twitter.home_mixer.functional_component.feature_hydrator.FrsSeedUserIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.UserStateFeature
import com.twitter.home_mixer.model.request.HasDeviceContext
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FetchParams
import com.twitter.home_mixer.product.scored_tweets.query_transformer.TimelineRankerFrsQueryTransformer._
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.quality_factor.HasQualityFactorStatus
import com.twitter.timelineranker.{thriftscala => t}
import com.twitter.timelines.common.model.TweetKindOption
import com.twitter.timelines.model.candidate.CandidateTweetSourceId

object TimelineRankerFrsQueryTransformer {
  private val DefaultSinceDuration = 24.hours
  private val ExpandedSinceDuration = 48.hours
  private val tweetKindOptions: TweetKindOption.ValueSet =
    TweetKindOption(includeOriginalTweetsAndQuotes = true)

  private val UserStatesForExtendedSinceDuration: Set[um.UserState] = Set(
    um.UserState.Light,
    um.UserState.MediumNonTweeter,
    um.UserState.MediumTweeter,
    um.UserState.NearZero,
    um.UserState.New,
    um.UserState.VeryLight
  )
}

case class TimelineRankerFrsQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContext
](
  override val candidatePipelineIdentifier: CandidatePipelineIdentifier)
    extends CandidatePipelineQueryTransformer[Query, t.RecapQuery]
    with TimelineRankerQueryTransformer[Query] {

  override val candidateTweetSourceId = CandidateTweetSourceId.FrsTweet
  override val options = tweetKindOptions
  override def maxTweetsToFetch(query: Query): Int =
    query.params(FetchParams.FRSMaxTweetsToFetchParam)

  override def getTensorflowModel(query: Query): Option[String] = {
    Some(query.params(ScoredTweetsParam.EarlybirdTensorflowModel.FrsParam))
  }

  override def seedAuthorIds(query: Query): Option[Seq[Long]] = {
    query.features.flatMap(_.getOrElse(FrsSeedUserIdsFeature, None))
  }

  override def transform(input: Query): t.RecapQuery = {
    val userState = input.features.get.getOrElse(UserStateFeature, None)

    val sinceDuration =
      if (userState.exists(UserStatesForExtendedSinceDuration.contains)) ExpandedSinceDuration
      else DefaultSinceDuration

    buildTimelineRankerQuery(input, sinceDuration).toThriftRecapQuery
  }
}
