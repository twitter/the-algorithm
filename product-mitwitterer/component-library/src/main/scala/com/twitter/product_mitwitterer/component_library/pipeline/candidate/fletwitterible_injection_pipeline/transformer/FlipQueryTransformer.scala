package com.twitter.product_mitwitterer.component_library.pipeline.candidate.fletwitterible_injection_pipeline.transformer

import com.twitter.onboarding.task.service.thriftscala.PromptType
import com.twitter.onboarding.task.service.{thriftscala => flip}
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object FlipQueryTransformer
    etwittertends CandidatePipelineQueryTransformer[
      PipelineQuery with HasFlipInjectionParams,
      flip.GetInjectionsRequest
    ] {

  val SUPPORTED_PROMPT_TYPES: Set[PromptType] = Set(
    PromptType.InlinePrompt,
    PromptType.FullCover,
    PromptType.HalfCover,
    PromptType.TileCarousel,
    PromptType.RelevancePrompt)

  override def transform(
    query: PipelineQuery with HasFlipInjectionParams
  ): flip.GetInjectionsRequest = {
    val clientContetwittert = flip.ClientContetwittert(
      userId = query.clientContetwittert.userId,
      guestId = query.clientContetwittert.guestId,
      clientApplicationId = query.clientContetwittert.appId,
      deviceId = query.clientContetwittert.deviceId,
      countryCode = query.clientContetwittert.countryCode,
      languageCode = query.clientContetwittert.languageCode,
      userAgent = query.clientContetwittert.userAgent,
      guestIdMarketing = query.clientContetwittert.guestIdMarketing,
      guestIdAds = query.clientContetwittert.guestIdAds,
      isInternalOrTwoffice = query.clientContetwittert.isTwoffice,
      ipAddress = query.clientContetwittert.ipAddress
    )
    val displayContetwittert: flip.DisplayContetwittert =
      flip.DisplayContetwittert(
        displayLocation = query.displayLocation,
        timelineId = query.clientContetwittert.userId
      )

    val requestTargetingContetwittert: flip.RequestTargetingContetwittert =
      flip.RequestTargetingContetwittert(
        rankingDisablerWithLatestControlsAvaliable =
          query.rankingDisablerWithLatestControlsAvailable,
        reactivePromptContetwittert = None,
        isEmptyState = query.isEmptyState,
        isFirstRequestAfterSignup = query.isFirstRequestAfterSignup,
        isEndOfTimeline = query.isEndOfTimeline
      )

    flip.GetInjectionsRequest(
      clientContetwittert = clientContetwittert,
      displayContetwittert = displayContetwittert,
      requestTargetingContetwittert = Some(requestTargetingContetwittert),
      userRoles = query.clientContetwittert.userRoles,
      timelineContetwittert = None,
      supportedPromptTypes = Some(SUPPORTED_PROMPT_TYPES)
    )
  }
}
