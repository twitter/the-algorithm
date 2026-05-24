package com.twitter.home_mixer.product.for_you.selector

import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.functional_component.common.CandidateScope.PartitionedCandidates
import com.twitter.product_mixer.core.functional_component.common.SpecificPipeline
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.selector.SelectorResult
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery

// This selector is used for debugging Ads. Tt will put all the Ads candidates to the top
case class DebugUpdateSortAdsResult(
  adsCandidatePipeline: CandidatePipelineIdentifier)
    extends Selector[PipelineQuery] {

  override val pipelineScope: CandidateScope = SpecificPipeline(adsCandidatePipeline)

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val PartitionedCandidates(adCandidates, otherRemainingCandidates) =
      pipelineScope.partition(result)

    SelectorResult(
      remainingCandidates = remainingCandidates,
      result = adCandidates ++ otherRemainingCandidates)
  }
}
