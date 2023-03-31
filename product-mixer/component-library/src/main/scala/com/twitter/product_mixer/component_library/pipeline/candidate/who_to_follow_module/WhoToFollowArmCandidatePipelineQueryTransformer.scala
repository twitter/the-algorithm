package com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module

import com.twitter.account_recommendations_mixer.{thriftscala => t}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.functional_component.marshaller.request.ClientContextMarshaller
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mixer.core.pipeline.pipeline_failure.BadRequest
import com.twitter.timelines.configapi.Param

object WhoToFollowArmCandidatePipelineQueryTransformer {
  val HomeDisplayLocation = "timeline"
  val HomeReverseChronDisplayLocation = "timeline_reverse_chron"
  val ProfileDisplayLocation = "profile_timeline"
}

case class WhoToFollowArmCandidatePipelineQueryTransformer[-Query <: PipelineQuery](
  displayLocationParam: Param[String],
  excludedUserIdsFeature: Option[Feature[PipelineQuery, Seq[Long]]],
  profileUserIdFeature: Option[Feature[PipelineQuery, Long]])
    extends CandidatePipelineQueryTransformer[Query, t.AccountRecommendationsMixerRequest] {

  override def transform(input: Query): t.AccountRecommendationsMixerRequest = {
    input.params(displayLocationParam) match {
      case WhoToFollowArmCandidatePipelineQueryTransformer.HomeReverseChronDisplayLocation =>
        t.AccountRecommendationsMixerRequest(
          clientContext = ClientContextMarshaller(input.clientContext),
          product = t.Product.HomeReverseChronWhoToFollow,
          productContext = Some(
            t.ProductContext.HomeReverseChronWhoToFollowProductContext(
              t.HomeReverseChronWhoToFollowProductContext(
                wtfReactiveContext = Some(getWhoToFollowReactiveContext(input))
              )))
        )
      case WhoToFollowArmCandidatePipelineQueryTransformer.HomeDisplayLocation =>
        t.AccountRecommendationsMixerRequest(
          clientContext = ClientContextMarshaller(input.clientContext),
          product = t.Product.HomeWhoToFollow,
          productContext = Some(
            t.ProductContext.HomeWhoToFollowProductContext(
              t.HomeWhoToFollowProductContext(
                wtfReactiveContext = Some(getWhoToFollowReactiveContext(input))
              )))
        )
      case WhoToFollowArmCandidatePipelineQueryTransformer.ProfileDisplayLocation =>
        t.AccountRecommendationsMixerRequest(
          clientContext = ClientContextMarshaller(input.clientContext),
          product = t.Product.ProfileWhoToFollow,
          productContext = Some(
            t.ProductContext.ProfileWhoToFollowProductContext(t.ProfileWhoToFollowProductContext(
              wtfReactiveContext = Some(getWhoToFollowReactiveContext(input)),
              profileUserId = profileUserIdFeature
                .flatMap(feature => input.features.map(_.get(feature)))
                .getOrElse(throw PipelineFailure(BadRequest, "profileUserId not provided")),
            )))
        )
      case displayLocation =>
        throw PipelineFailure(BadRequest, s"display location $displayLocation not supported")
    }
  }

  private def getWhoToFollowReactiveContext(
    input: Query
  ): t.WhoToFollowReactiveContext = {
    t.WhoToFollowReactiveContext(
      excludedUserIds = excludedUserIdsFeature.flatMap(feature =>
        input.features
          .map(_.get(feature))),
    )
  }
}
