package com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline

import com.twitter.product_mixer.component_library.candidate_source.flexible_injection_pipeline.PromptCandidateSource
import com.twitter.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.transformer.HasFlipInjectionParams
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.decider.DeciderParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlipPromptDependentCandidatePipelineConfigBuilder @Inject() (
  promptCandidateSource: PromptCandidateSource) {

  /**
   * Build a FlipPromptDependentCandidatePipelineConfig
   *
   * @note If injected classes are needed to populate parameters in this method, consider creating a
   *       ProductFlipPromptDependentCandidatePipelineConfigBuilder with a single `def build()` method.
   *       That product-specific builder class can then inject everything it needs (including this
   *       class), and delegate to this class's build() method within its own build() method.
   */
  def build[Query <: PipelineQuery with HasFlipInjectionParams](
    identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier("FlipPrompt"),
    enabledDeciderParam: Option[DeciderParam[Boolean]] = None,
    supportedClientParam: Option[FSParam[Boolean]] = None,
  ): FlipPromptDependentCandidatePipelineConfig[Query] = {
    new FlipPromptDependentCandidatePipelineConfig(
      identifier = identifier,
      enabledDeciderParam = enabledDeciderParam,
      supportedClientParam = supportedClientParam,
      promptCandidateSource = promptCandidateSource)
  }
}
