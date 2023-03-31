package com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module

import com.twitter.peoplediscovery.api.thriftscala.ClientContext
import com.twitter.peoplediscovery.api.thriftscala.GetModuleRequest
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.pipeline.PipelineQuery
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
  excludedUserIdsFeature: Option[Feature[PipelineQuery, Seq[Long]]],
) extends CandidatePipelineQueryTransformer[Query, GetModuleRequest] {

  override def transform(input: Query): GetModuleRequest =
    GetModuleRequest(
      clientContext = ClientContext(
        userId = input.getRequiredUserId,
        deviceId = input.clientContext.deviceId,
        userAgent = input.clientContext.userAgent,
        countryCode = input.clientContext.countryCode,
        languageCode = input.clientContext.languageCode,
      ),
      displayLocation = input.params(displayLocationParam),
      supportedLayouts = input.params(supportedLayoutsParam),
      layoutVersion = input.params(layoutVersionParam),
      excludedUserIds =
        excludedUserIdsFeature.flatMap(feature => input.features.map(_.get(feature))),
      includePromoted = Some(true),
    )
}
