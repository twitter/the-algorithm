package com.twitter.home_mixer.candidate_pipeline

import com.twitter.home_mixer.functional_component.decorator.builder.VerifiedPromptBuilder
import com.twitter.home_mixer.functional_component.gate.RateLimitNotGate
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mixer.component_library.model.candidate.InlinePromptCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.candidate_source.IdentityCandidateExtractor
import com.twitter.product_mixer.core.functional_component.candidate_source.PassthroughCandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.DependentCandidatePipelineConfig
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.ExternalStringRegistry
import com.twitter.stringcenter.client.StringCenter
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class VerifiedPromptCandidatePipelineConfig @Inject() (
  identityCandidateExtractor: IdentityCandidateExtractor[PipelineQuery],
  @ProductScoped externalStringRegistryProvider: Provider[ExternalStringRegistry],
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    extends DependentCandidatePipelineConfig[
      PipelineQuery,
      PipelineQuery,
      PipelineQuery,
      InlinePromptCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("VerifiedPrompt")

  override val gates: Seq[Gate[PipelineQuery]] = Seq(RateLimitNotGate)

  override def candidateSource: CandidateSource[
    PipelineQuery,
    PipelineQuery
  ] = PassthroughCandidateSource(
    CandidateSourceIdentifier("VerifiedPassthroughCandidateSource"),
    identityCandidateExtractor
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[PipelineQuery, PipelineQuery] =
    identity

  override val resultTransformer: CandidatePipelineResultsTransformer[
    PipelineQuery,
    InlinePromptCandidate
  ] = { query => InlinePromptCandidate(id = query.getRequiredUserId.toString) }

  override val decorator: Option[
    CandidateDecorator[PipelineQuery, InlinePromptCandidate]
  ] = {
    val clientEventInfoBuilder =
      ClientEventInfoBuilder[PipelineQuery, InlinePromptCandidate]("verified_prompt")
    val stringCenter = stringCenterProvider.get()
    val externalStringRegistry = externalStringRegistryProvider.get()

    val verifiedPromptBuilder = VerifiedPromptBuilder(
      clientEventInfoBuilder,
      stringCenter,
      externalStringRegistry
    )

    Some(UrtItemCandidateDecorator(verifiedPromptBuilder))
  }
}
