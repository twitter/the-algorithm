package com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.transformer

import com.twitter.onboarding.injections.{thriftscala => flipinjection}
import com.twitter.product_mixer.component_library.candidate_source.flexible_injection_pipeline.IntermediatePrompt
import com.twitter.product_mixer.component_library.model.candidate.BasePromptCandidate
import com.twitter.product_mixer.component_library.model.candidate.FullCoverPromptCandidate
import com.twitter.product_mixer.component_library.model.candidate.HalfCoverPromptCandidate
import com.twitter.product_mixer.component_library.model.candidate.InlinePromptCandidate
import com.twitter.product_mixer.component_library.model.candidate.PromptCarouselTileCandidate
import com.twitter.product_mixer.component_library.model.candidate.RelevancePromptCandidate
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.functional_component.marshaller.TransportMarshaller

object PromptResultsTransformer
    extends CandidatePipelineResultsTransformer[
      IntermediatePrompt,
      BasePromptCandidate[Any]
    ] {

  /**
   * Transforms a Flip Injection to a Product Mixer domain object deriving from BasePromptCandidate.
   * Supported injection types have to match those declared in com.twitter.product_mixer.component_library.transformer.flexible_injection_pipeline.FlipQueryTransformer#supportedPromptFormats
   */
  override def transform(input: IntermediatePrompt): BasePromptCandidate[Any] =
    input.injection match {
      case inlinePrompt: flipinjection.Injection.InlinePrompt =>
        InlinePromptCandidate(id = inlinePrompt.inlinePrompt.injectionIdentifier
          .getOrElse(throw new MissingInjectionId(input.injection)))
      case _: flipinjection.Injection.FullCover =>
        FullCoverPromptCandidate(id = "0")
      case _: flipinjection.Injection.HalfCover =>
        HalfCoverPromptCandidate(id = "0")
      case _: flipinjection.Injection.TilesCarousel =>
        PromptCarouselTileCandidate(id =
          input.offsetInModule.getOrElse(throw FlipPromptOffsetInModuleMissing))
      case relevancePrompt: flipinjection.Injection.RelevancePrompt =>
        RelevancePromptCandidate(
          id = relevancePrompt.relevancePrompt.injectionIdentifier,
          position = relevancePrompt.relevancePrompt.requestedPosition.map(_.toInt))
      case injection => throw new UnsupportedInjectionType(injection)
    }
}

class MissingInjectionId(injection: flipinjection.Injection)
    extends IllegalArgumentException(
      s"Injection identifier is missing ${TransportMarshaller.getSimpleName(injection.getClass)}")

class UnsupportedInjectionType(injection: flipinjection.Injection)
    extends UnsupportedOperationException(
      s"Unsupported FLIP injection Type : ${TransportMarshaller.getSimpleName(injection.getClass)}")

object FlipPromptOffsetInModuleMissing
    extends NoSuchElementException(
      "FlipPromptOffsetInModuleFeature must be set for the TilesCarousel FLIP injection in PromptCandidateSource")
