package com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline

import com.twitter.onboarding.task.service.thriftscala.GetInjectionsRequest
import com.twitter.onboarding.task.service.{thriftscala => servicethrift}
import com.twitter.product_mixer.component_library.candidate_source.flexible_injection_pipeline.IntermediatePrompt
import com.twitter.product_mixer.component_library.candidate_source.flexible_injection_pipeline.PromptCandidateSource
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.UrtMultipleModulesDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.flexible_injection_pipeline.FlipPromptCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.flexible_injection_pipeline.FlipPromptModuleGrouping
import com.twitter.product_mixer.component_library.decorator.urt.builder.flexible_injection_pipeline.FlipPromptUrtModuleBuilder
import com.twitter.product_mixer.component_library.model.candidate.BasePromptCandidate
import com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.transformer.FlipCandidateFeatureTransformer
import com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.transformer.FlipQueryTransformer
import com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.transformer.HasFlipInjectionParams
import com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.transformer.PromptResultsTransformer
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.DependentCandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.decider.DeciderParam

/**
 * A dependent candidate pipeline for Flexible Injection Pipeline Candidates.
 * Fetches prompts from FLIP (inside onboarding-task-service).
 */
class FlipPromptDependentCandidatePipelineConfig[
  Query <: PipelineQuery with HasFlipInjectionParams
](
  override val identifier: CandidatePipelineIdentifier,
  override val enabledDeciderParam: Option[DeciderParam[Boolean]],
  override val supportedClientParam: Option[FSParam[Boolean]],
  promptCandidateSource: PromptCandidateSource)
    extends DependentCandidatePipelineConfig[
      Query,
      servicethrift.GetInjectionsRequest,
      IntermediatePrompt,
      BasePromptCandidate[Any]
    ] {

  override val candidateSource: CandidateSource[GetInjectionsRequest, IntermediatePrompt] =
    promptCandidateSource

  override val queryTransformer: CandidatePipelineQueryTransformer[Query, GetInjectionsRequest] =
    FlipQueryTransformer

  override val resultTransformer: CandidatePipelineResultsTransformer[
    IntermediatePrompt,
    BasePromptCandidate[Any]
  ] = PromptResultsTransformer

  override val decorator: Option[
    CandidateDecorator[Query, BasePromptCandidate[Any]]
  ] = Some(
    UrtMultipleModulesDecorator(
      urtItemCandidateDecorator = UrtItemCandidateDecorator(FlipPromptCandidateUrtItemBuilder()),
      moduleBuilder = FlipPromptUrtModuleBuilder(),
      groupByKey = FlipPromptModuleGrouping
    ))

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[IntermediatePrompt]
  ] = Seq(FlipCandidateFeatureTransformer)
}
