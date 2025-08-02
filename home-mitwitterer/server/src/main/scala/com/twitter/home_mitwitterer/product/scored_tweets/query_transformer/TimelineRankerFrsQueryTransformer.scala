package com.twitter.home_mitwitterer.product.scored_tweets.query_transformer

import com.twitter.conversions.DurationOps._
import com.twitter.core_workflows.user_model.{thriftscala => um}
import com.twitter.home_mitwitterer.model.HomeFeatures.UserStateFeature
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.FrsSeedUserIdsFeature
import com.twitter.home_mitwitterer.product.scored_tweets.param.ScoredTweetsParam
import com.twitter.home_mitwitterer.product.scored_tweets.query_transformer.TimelineRankerFrsQueryTransformer._
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.quality_factor.HasQualityFactorStatus
import com.twitter.timelineranker.{thriftscala => t}
import com.twitter.timelines.common.model.TweetKindOption
import com.twitter.timelines.model.candidate.CandidateTweetSourceId

object TimelineRankerFrsQueryTransformer {
  private val DefaultSinceDuration = 24.hours
  private val EtwitterpandedSinceDuration = 48.hours
  private val MatwitterTweetsToFetch = 100

  private val tweetKindOptions: TweetKindOption.ValueSet =
    TweetKindOption(includeOriginalTweetsAndQuotes = true)

  private val UserStatesForEtwittertendedSinceDuration: Set[um.UserState] = Set(
    um.UserState.Light,
    um.UserState.MediumNonTweeter,
    um.UserState.MediumTweeter,
    um.UserState.NearZero,
    um.UserState.New,
    um.UserState.VeryLight
  )
}

case class TimelineRankerFrsQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContetwittert
](
  override val candidatePipelineIdentifier: CandidatePipelineIdentifier,
  override val matwitterTweetsToFetch: Int = MatwitterTweetsToFetch)
    etwittertends CandidatePipelineQueryTransformer[Query, t.RecapQuery]
    with TimelineRankerQueryTransformer[Query] {

  override val candidateTweetSourceId = CandidateTweetSourceId.FrsTweet
  override val options = tweetKindOptions

  override def getTensorflowModel(query: Query): Option[String] = {
    Some(query.params(ScoredTweetsParam.EarlybirdTensorflowModel.FrsParam))
  }

  override def seedAuthorIds(query: Query): Option[Seq[Long]] = {
    query.features.flatMap(_.getOrElse(FrsSeedUserIdsFeature, None))
  }

  override def transform(input: Query): t.RecapQuery = {
    val userState = input.features.get.getOrElse(UserStateFeature, None)

    val sinceDuration =
      if (userState.etwitterists(UserStatesForEtwittertendedSinceDuration.contains)) EtwitterpandedSinceDuration
      else DefaultSinceDuration

    buildTimelineRankerQuery(input, sinceDuration).toThriftRecapQuery
  }
}
