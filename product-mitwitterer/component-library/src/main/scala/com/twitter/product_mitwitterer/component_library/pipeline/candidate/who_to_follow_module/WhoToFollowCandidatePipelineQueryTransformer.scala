package com.twitter.product_mitwitterer.component_library.pipeline.candidate.who_to_follow_module

import com.twitter.peoplediscovery.api.thriftscala.ClientContetwittert
import com.twitter.peoplediscovery.api.thriftscala.GetModuleRequest
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

object WhoToFollowCandidatePipelineQueryTransformer {
  val DisplayLocation = "timeline"
  val SupportedLayouts = Seq("user-bio-list")
  val LayoutVersion = 2
}

case class WhoToFollowCandidatePipelineQueryTransformer[-Query <: PipelineQuery](
  displayLocationParam: Param[String],
  supportedLayoutsParam: Param[Seq[String]],
  layoutVersionParam: Param[Int],
  etwittercludedUserIdsFeature: Option[Feature[PipelineQuery, Seq[Long]]],
) etwittertends CandidatePipelineQueryTransformer[Query, GetModuleRequest] {

  override def transform(input: Query): GetModuleRequest =
    GetModuleRequest(
      clientContetwittert = ClientContetwittert(
        userId = input.getRequiredUserId,
        deviceId = input.clientContetwittert.deviceId,
        userAgent = input.clientContetwittert.userAgent,
        countryCode = input.clientContetwittert.countryCode,
        languageCode = input.clientContetwittert.languageCode,
      ),
      displayLocation = input.params(displayLocationParam),
      supportedLayouts = input.params(supportedLayoutsParam),
      layoutVersion = input.params(layoutVersionParam),
      etwittercludedUserIds =
        etwittercludedUserIdsFeature.flatMap(feature => input.features.map(_.get(feature))),
      includePromoted = Some(true),
    )
}
