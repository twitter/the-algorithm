package com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.transformer

import com.twitter.onboarding.injections.{thriftscala => onboardingthrift}
import com.twitter.product_mixer.component_library.candidate_source.flexible_injection_pipeline.IntermediatePrompt
import com.twitter.product_mixer.component_library.model.candidate.BasePromptCandidate
import com.twitter.product_mixer.component_library.model.candidate.PromptCarouselTileCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier

case object FlipPromptCarouselTileFeature
    extends Feature[PromptCarouselTileCandidate, Option[onboardingthrift.Tile]]

case object FlipPromptInjectionsFeature
    extends Feature[BasePromptCandidate[String], onboardingthrift.Injection]

case object FlipPromptOffsetInModuleFeature
    extends Feature[PromptCarouselTileCandidate, Option[Int]]

object FlipCandidateFeatureTransformer extends CandidateFeatureTransformer[IntermediatePrompt] {

  override val identifier: TransformerIdentifier = TransformerIdentifier("FlipCandidateFeature")

  override val features: Set[Feature[_, _]] =
    Set(FlipPromptInjectionsFeature, FlipPromptOffsetInModuleFeature, FlipPromptCarouselTileFeature)

  /** Hydrates a [[FeatureMap]] for a given [[Inputs]] */
  override def transform(input: IntermediatePrompt): FeatureMap = {
    FeatureMapBuilder()
      .add(FlipPromptInjectionsFeature, input.injection)
      .add(FlipPromptOffsetInModuleFeature, input.offsetInModule)
      .add(FlipPromptCarouselTileFeature, input.carouselTile)
      .build()
  }
}
