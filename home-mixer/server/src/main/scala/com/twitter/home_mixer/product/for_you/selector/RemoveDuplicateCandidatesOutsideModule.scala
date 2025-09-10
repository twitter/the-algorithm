package com.twitter.home_mixer.product.for_you.selector

import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.selector.SelectorResult
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ModuleCandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

case class RemoveDuplicateCandidatesOutsideModule(
  override val pipelineScope: CandidateScope,
  candidatePipelinesOutsideModule: Set[CandidatePipelineIdentifier],
  numCandidatesToCompareAgainst: Param[Int])
    extends Selector[PipelineQuery] {
  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val candidatesOutsideModule = getCandidateIdsOutsideModule(query, remainingCandidates)
    val finalRemainingCandidates = remainingCandidates.map {
      case module: ModuleCandidateWithDetails if pipelineScope.contains(module) =>
        module.copy(candidates = module.candidates.filterNot { candidate =>
          candidatesOutsideModule.contains(candidate.candidateIdLong)
        })
      case candidate => candidate
    }

    SelectorResult(remainingCandidates = finalRemainingCandidates, result = result)
  }

  private def getCandidateIdsOutsideModule(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails]
  ): Set[Long] = {
    remainingCandidates
      .collect {
        case candidate: ItemCandidateWithDetails
            if candidatePipelinesOutsideModule.contains(candidate.source) =>
          candidate.candidateIdLong
      }.take(query.params(numCandidatesToCompareAgainst)).toSet
  }
}
