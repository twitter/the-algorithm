package com.twitter.home_mitwitterer.product.scored_tweets.query_transformer.earlybird

import com.twitter.conversions.DurationOps._
import com.twitter.core_workflows.user_model.{thriftscala => um}
import com.twitter.home_mitwitterer.model.HomeFeatures.UserStateFeature
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.home_mitwitterer.product.scored_tweets.query_transformer.earlybird.EarlybirdInNetworkQueryTransformer._
import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.quality_factor.HasQualityFactorStatus
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.timelines.common.model.TweetKindOption

object EarlybirdInNetworkQueryTransformer {
  private val DefaultSinceDuration = 24.hours
  private val EtwitterpandedSinceDuration = 48.hours
  private val MatwitterTweetsToFetch = 600
  private val TensorflowModel = Some("timelines_recap_replica")

  private val TweetKindOptions: TweetKindOption.ValueSet = TweetKindOption(
    includeReplies = true,
    includeRetweets = true,
    includeOriginalTweetsAndQuotes = true,
    includeEtwittertendedReplies = true
  )

  private val UserStatesForEtwittertendedSinceDuration: Set[um.UserState] = Set(
    um.UserState.Light,
    um.UserState.MediumNonTweeter,
    um.UserState.MediumTweeter,
    um.UserState.NearZero,
    um.UserState.New,
    um.UserState.VeryLight
  )
}

case class EarlybirdInNetworkQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContetwittert
](
  candidatePipelineIdentifier: CandidatePipelineIdentifier,
  override val clientId: Option[String])
    etwittertends CandidatePipelineQueryTransformer[Query, eb.EarlybirdRequest]
    with EarlybirdQueryTransformer[Query] {

  override val tweetKindOptions: TweetKindOption.ValueSet = TweetKindOptions
  override val matwitterTweetsToFetch: Int = MatwitterTweetsToFetch
  override val tensorflowModel: Option[String] = TensorflowModel

  override def transform(query: Query): eb.EarlybirdRequest = {

    val userState = query.features.get.getOrElse(UserStateFeature, None)

    val sinceDuration =
      if (userState.etwitterists(UserStatesForEtwittertendedSinceDuration.contains)) EtwitterpandedSinceDuration
      else DefaultSinceDuration

    val followedUserIds =
      query.features
        .map(
          _.getOrElse(
            SGSFollowedUsersFeature,
            Seq.empty)).toSeq.flatten.toSet + query.getRequiredUserId

    buildEarlybirdQuery(query, sinceDuration, followedUserIds)
  }
}
