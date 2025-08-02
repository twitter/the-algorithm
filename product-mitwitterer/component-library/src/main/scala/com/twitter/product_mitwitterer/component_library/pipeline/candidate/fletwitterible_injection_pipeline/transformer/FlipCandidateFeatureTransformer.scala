package com.twitter.product_mitwitterer.component_library.pipeline.candidate.fletwitterible_injection_pipeline.transformer

import com.twitter.onboarding.injections.{thriftscala => onboardingthrift}
import com.twitter.product_mitwitterer.component_library.candidate_source.fletwitterible_injection_pipeline.IntermediatePrompt
import com.twitter.product_mitwitterer.component_library.model.candidate.BasePromptCandidate
import com.twitter.product_mitwitterer.component_library.model.candidate.PromptCarouselTileCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier

case object FlipPromptCarouselTileFeature
    etwittertends Feature[PromptCarouselTileCandidate, Option[onboardingthrift.Tile]]

case object FlipPromptInjectionsFeature
    etwittertends Feature[BasePromptCandidate[String], onboardingthrift.Injection]

case object FlipPromptOffsetInModuleFeature
    etwittertends Feature[PromptCarouselTileCandidate, Option[Int]]

object FlipCandidateFeatureTransformer etwittertends CandidateFeatureTransformer[IntermediatePrompt] {

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
