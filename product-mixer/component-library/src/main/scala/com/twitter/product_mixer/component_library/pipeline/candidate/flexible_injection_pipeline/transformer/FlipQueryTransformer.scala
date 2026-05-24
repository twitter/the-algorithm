package com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.transformer

import com.twitter.onboarding.task.service.thriftscala.PromptType
import com.twitter.onboarding.task.service.{thriftscala => flip}
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object FlipQueryTransformer
    extends CandidatePipelineQueryTransformer[
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
    val clientContext = flip.ClientContext(
      userId = query.clientContext.userId,
      guestId = query.clientContext.guestId,
      clientApplicationId = query.clientContext.appId,
      deviceId = query.clientContext.deviceId,
      countryCode = query.clientContext.countryCode,
      languageCode = query.clientContext.languageCode,
      userAgent = query.clientContext.userAgent,
      guestIdMarketing = query.clientContext.guestIdMarketing,
      guestIdAds = query.clientContext.guestIdAds,
      isInternalOrTwoffice = query.clientContext.isTwoffice,
      ipAddress = query.clientContext.ipAddress
    )
    val displayContext: flip.DisplayContext =
      flip.DisplayContext(
        displayLocation = query.displayLocation,
        timelineId = query.clientContext.userId
      )

    val requestTargetingContext: flip.RequestTargetingContext =
      flip.RequestTargetingContext(
        rankingDisablerWithLatestControlsAvaliable =
          query.rankingDisablerWithLatestControlsAvailable,
        reactivePromptContext = None,
        isEmptyState = query.isEmptyState,
        isFirstRequestAfterSignup = query.isFirstRequestAfterSignup,
        isEndOfTimeline = query.isEndOfTimeline
      )

    flip.GetInjectionsRequest(
      clientContext = clientContext,
      displayContext = displayContext,
      requestTargetingContext = Some(requestTargetingContext),
      userRoles = query.clientContext.userRoles,
      timelineContext = None,
      supportedPromptTypes = Some(SUPPORTED_PROMPT_TYPES)
    )
  }
}
