package com.twitter.product_mitwitterer.component_library.pipeline.candidate.who_to_follow_module

import com.twitter.account_recommendations_mitwitterer.{thriftscala => t}
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.functional_component.marshaller.request.ClientContetwittertMarshaller
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.BadRequest
import com.twitter.timelines.configapi.Param

object WhoToFollowArmCandidatePipelineQueryTransformer {
  val HomeDisplayLocation = "timeline"
  val HomeReverseChronDisplayLocation = "timeline_reverse_chron"
  val ProfileDisplayLocation = "profile_timeline"
}

case class WhoToFollowArmCandidatePipelineQueryTransformer[-Query <: PipelineQuery](
  displayLocationParam: Param[String],
  etwittercludedUserIdsFeature: Option[Feature[PipelineQuery, Seq[Long]]],
  profileUserIdFeature: Option[Feature[PipelineQuery, Long]])
    etwittertends CandidatePipelineQueryTransformer[Query, t.AccountRecommendationsMitwittererRequest] {

  override def transform(input: Query): t.AccountRecommendationsMitwittererRequest = {
    input.params(displayLocationParam) match {
      case WhoToFollowArmCandidatePipelineQueryTransformer.HomeReverseChronDisplayLocation =>
        t.AccountRecommendationsMitwittererRequest(
          clientContetwittert = ClientContetwittertMarshaller(input.clientContetwittert),
          product = t.Product.HomeReverseChronWhoToFollow,
          productContetwittert = Some(
            t.ProductContetwittert.HomeReverseChronWhoToFollowProductContetwittert(
              t.HomeReverseChronWhoToFollowProductContetwittert(
                wtfReactiveContetwittert = Some(getWhoToFollowReactiveContetwittert(input))
              )))
        )
      case WhoToFollowArmCandidatePipelineQueryTransformer.HomeDisplayLocation =>
        t.AccountRecommendationsMitwittererRequest(
          clientContetwittert = ClientContetwittertMarshaller(input.clientContetwittert),
          product = t.Product.HomeWhoToFollow,
          productContetwittert = Some(
            t.ProductContetwittert.HomeWhoToFollowProductContetwittert(
              t.HomeWhoToFollowProductContetwittert(
                wtfReactiveContetwittert = Some(getWhoToFollowReactiveContetwittert(input))
              )))
        )
      case WhoToFollowArmCandidatePipelineQueryTransformer.ProfileDisplayLocation =>
        t.AccountRecommendationsMitwittererRequest(
          clientContetwittert = ClientContetwittertMarshaller(input.clientContetwittert),
          product = t.Product.ProfileWhoToFollow,
          productContetwittert = Some(
            t.ProductContetwittert.ProfileWhoToFollowProductContetwittert(t.ProfileWhoToFollowProductContetwittert(
              wtfReactiveContetwittert = Some(getWhoToFollowReactiveContetwittert(input)),
              profileUserId = profileUserIdFeature
                .flatMap(feature => input.features.map(_.get(feature)))
                .getOrElse(throw PipelineFailure(BadRequest, "profileUserId not provided")),
            )))
        )
      case displayLocation =>
        throw PipelineFailure(BadRequest, s"display location $displayLocation not supported")
    }
  }

  private def getWhoToFollowReactiveContetwittert(
    input: Query
  ): t.WhoToFollowReactiveContetwittert = {
    t.WhoToFollowReactiveContetwittert(
      etwittercludedUserIds = etwittercludedUserIdsFeature.flatMap(feature =>
        input.features
          .map(_.get(feature))),
    )
  }
}
