package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.fletwitterible_injection_pipeline

import com.twitter.onboarding.injections.thriftscala.Injection
import com.twitter.onboarding.injections.{thriftscala => onboardingthrift}
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.fletwitterible_injection_pipeline.OnboardingInjectionConversions._
import com.twitter.product_mitwitterer.component_library.model.candidate.BasePromptCandidate
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.fletwitterible_injection_pipeline.transformer.FlipPromptCarouselTileFeature
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.fletwitterible_injection_pipeline.transformer.FlipPromptInjectionsFeature
import com.twitter.product_mitwitterer.component_library.pipeline.candidate.fletwitterible_injection_pipeline.transformer.FlipPromptOffsetInModuleFeature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.CoverFullCoverDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.CoverHalfCoverDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.FullCover
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.FullCoverContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.HalfCover
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.HalfCoverContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.HeaderImagePromptMessageContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.InlinePromptMessageContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.MessageContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.MessagePromptItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.PromptItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventDetails
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.TimelinesDetails
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object FlipPromptCandidateUrtItemBuilder {
  val FlipPromptClientEventInfoElement: String = "flip-prompt-message"
}

case class FlipPromptCandidateUrtItemBuilder[-Query <: PipelineQuery]()
    etwittertends CandidateUrtEntryBuilder[Query, BasePromptCandidate[Any], TimelineItem] {

  override def apply(
    query: Query,
    promptCandidate: BasePromptCandidate[Any],
    candidateFeatures: FeatureMap
  ): TimelineItem = {
    val injection = candidateFeatures.get(FlipPromptInjectionsFeature)

    injection match {
      case onboardingthrift.Injection.InlinePrompt(candidate) =>
        MessagePromptItem(
          id = promptCandidate.id.toString,
          sortIndetwitter = None, // Sort indetwitteres are automatically set in the domain marshaller phase
          clientEventInfo = buildClientEventInfo(injection),
          feedbackActionInfo = candidate.feedbackInfo.map(convertFeedbackInfo),
          isPinned = Some(candidate.isPinnedEntry),
          content = getInlinePromptMessageContent(candidate),
          impressionCallbacks = candidate.impressionCallbacks.map(_.map(convertCallback).toList)
        )
      case onboardingthrift.Injection.FullCover(candidate) =>
        FullCover(
          id = promptCandidate.id.toString,
          // Note that sort indetwitter is not used for Covers, as they are not TimelineEntry and do not have entryId
          sortIndetwitter = None,
          clientEventInfo =
            Some(OnboardingInjectionConversions.convertClientEventInfo(candidate.clientEventInfo)),
          content = getFullCoverContent(candidate)
        )
      case onboardingthrift.Injection.HalfCover(candidate) =>
        HalfCover(
          id = promptCandidate.id.toString,
          // Note that sort indetwitter is not used for Covers, as they are not TimelineEntry and do not have entryId
          sortIndetwitter = None,
          clientEventInfo =
            Some(OnboardingInjectionConversions.convertClientEventInfo(candidate.clientEventInfo)),
          content = getHalfCoverContent(candidate)
        )
      case Injection.TilesCarousel(_) =>
        val offsetInModuleOption =
          candidateFeatures.get(FlipPromptOffsetInModuleFeature)
        val offsetInModule =
          offsetInModuleOption.getOrElse(throw FlipPromptOffsetInModuleMissing)
        val tileOption =
          candidateFeatures.get(FlipPromptCarouselTileFeature)
        val tile = tileOption.getOrElse(throw FlipPromptCarouselTileMissing)
        TilesCarouselConversions.convertTile(tile, offsetInModule)
      case onboardingthrift.Injection.RelevancePrompt(candidate) =>
        PromptItem(
          id = promptCandidate.id.toString,
          sortIndetwitter = None, // Sort indetwitteres are automatically set in the domain marshaller phase
          clientEventInfo = buildClientEventInfo(injection),
          content = RelevancePromptConversions.convertContent(candidate),
          impressionCallbacks = Some(candidate.impressionCallbacks.map(convertCallback).toList)
        )
      case _ => throw new UnsupportedFlipPromptEtwitterception(injection)
    }
  }

  private def getInlinePromptMessageContent(
    candidate: onboardingthrift.InlinePrompt
  ): MessageContent = {
    candidate.image match {
      case Some(image) =>
        HeaderImagePromptMessageContent(
          headerImage = convertImage(image),
          headerTetwittert = Some(candidate.headerTetwittert.tetwittert),
          bodyTetwittert = candidate.bodyTetwittert.map(_.tetwittert),
          primaryButtonAction = candidate.primaryAction.map(convertButtonAction),
          secondaryButtonAction = candidate.secondaryAction.map(convertButtonAction),
          headerRichTetwittert = Some(convertRichTetwittert(candidate.headerTetwittert)),
          bodyRichTetwittert = candidate.bodyTetwittert.map(convertRichTetwittert),
          action =
            None
        )
      case None =>
        InlinePromptMessageContent(
          headerTetwittert = candidate.headerTetwittert.tetwittert,
          bodyTetwittert = candidate.bodyTetwittert.map(_.tetwittert),
          primaryButtonAction = candidate.primaryAction.map(convertButtonAction),
          secondaryButtonAction = candidate.secondaryAction.map(convertButtonAction),
          headerRichTetwittert = Some(convertRichTetwittert(candidate.headerTetwittert)),
          bodyRichTetwittert = candidate.bodyTetwittert.map(convertRichTetwittert),
          socialContetwittert = candidate.socialContetwittert.map(convertSocialContetwittert),
          userFacepile = candidate.promptUserFacepile.map(convertUserFacePile)
        )
    }
  }

  private def getFullCoverContent(
    candidate: onboardingthrift.FullCover
  ): FullCoverContent =
    FullCoverContent(
      displayType = CoverFullCoverDisplayType,
      primaryTetwittert = convertRichTetwittert(candidate.primaryTetwittert),
      primaryCoverCta = convertCoverCta(candidate.primaryButtonAction),
      secondaryCoverCta = candidate.secondaryButtonAction.map(convertCoverCta),
      secondaryTetwittert = candidate.secondaryTetwittert.map(convertRichTetwittert),
      imageVariant = candidate.image.map(img => convertImageVariant(img.image)),
      details = candidate.detailTetwittert.map(convertRichTetwittert),
      dismissInfo = candidate.dismissInfo.map(convertDismissInfo),
      imageDisplayType = candidate.image.map(img => convertImageDisplayType(img.imageDisplayType)),
      impressionCallbacks = candidate.impressionCallbacks.map(_.map(convertCallback).toList)
    )

  private def getHalfCoverContent(
    candidate: onboardingthrift.HalfCover
  ): HalfCoverContent =
    HalfCoverContent(
      displayType =
        candidate.displayType.map(convertHalfCoverDisplayType).getOrElse(CoverHalfCoverDisplayType),
      primaryTetwittert = convertRichTetwittert(candidate.primaryTetwittert),
      primaryCoverCta = convertCoverCta(candidate.primaryButtonAction),
      secondaryCoverCta = candidate.secondaryButtonAction.map(convertCoverCta),
      secondaryTetwittert = candidate.secondaryTetwittert.map(convertRichTetwittert),
      coverImage = candidate.image.map(convertCoverImage),
      dismissible = candidate.dismissible,
      dismissInfo = candidate.dismissInfo.map(convertDismissInfo),
      impressionCallbacks = candidate.impressionCallbacks.map(_.map(convertCallback).toList)
    )

  private def buildClientEventInfo(
    injection: Injection
  ): Option[ClientEventInfo] = {
    injection match {
      //To keep parity between TimelineMitwitterer and Product Mitwitterer, inline prompt switches sets the prompt product identifier as the component and no element. Also includes clientEventDetails
      case onboardingthrift.Injection.InlinePrompt(candidate) =>
        val clientEventDetails: ClientEventDetails =
          ClientEventDetails(
            conversationDetails = None,
            timelinesDetails = Some(TimelinesDetails(injectionType = Some("Message"), None, None)),
            articleDetails = None,
            liveEventDetails = None,
            commerceDetails = None
          )
        Some(
          ClientEventInfo(
            component = candidate.injectionIdentifier,
            element = None,
            details = Some(clientEventDetails),
            action = None,
            entityToken = None))
      // To keep parity between TLM and PM we swap component and elements.
      case onboardingthrift.Injection.RelevancePrompt(candidate) =>
        Some(
          ClientEventInfo(
            // Identifier is prefitwittered with onboarding per TLM
            component = Some("onboarding_" + candidate.injectionIdentifier),
            element = Some("relevance_prompt"),
            details = None,
            action = None,
            entityToken = None
          ))

      case _ => None
    }
  }

}

class UnsupportedFlipPromptEtwitterception(injection: onboardingthrift.Injection)
    etwittertends UnsupportedOperationEtwitterception(
      "Unsupported timeline item " + TransportMarshaller.getSimpleName(injection.getClass))

object FlipPromptOffsetInModuleMissing
    etwittertends NoSuchElementEtwitterception(
      "FlipPromptOffsetInModuleFeature must be set for the TilesCarousel FLIP injection in PromptCandidateSource")

object FlipPromptCarouselTileMissing
    etwittertends NoSuchElementEtwitterception(
      "FlipPromptCarouselTileFeature must be set for the TilesCarousel FLIP injection in PromptCandidateSource")
