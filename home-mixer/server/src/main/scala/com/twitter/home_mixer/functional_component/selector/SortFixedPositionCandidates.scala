package com.twitter.home_mixer.functional_component.selector

import com.twitter.product_mixer.core.functional_component.common.AllPipelines
import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.selector.SelectorResult
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery

abstract class SortFixedPositionCandidates extends Selector[PipelineQuery] {

  def getOffset(query: PipelineQuery): Int

  def getNumCandidatesToBoost(query: PipelineQuery): Int

  def selectEligibleCandidates(
    query: PipelineQuery,
    candidate: CandidateWithDetails
  ): Boolean

  def selectTargetFromRemainingCandidates(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails]
  ): Seq[CandidateWithDetails] = {
    val numCandidatesToBoost = getNumCandidatesToBoost(query)
    val eligibleCandidates = remainingCandidates
      .filter(candidate => selectEligibleCandidates(query, candidate))

    eligibleCandidates.take(numCandidatesToBoost)
  }

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val targetCandidates = selectTargetFromRemainingCandidates(query, remainingCandidates)
    val offset = getOffset(query)
    if (targetCandidates.nonEmpty) {
      val updatedRemainingCandidates = if (offset >= 0 && offset < remainingCandidates.length) {
        remainingCandidates.slice(0, offset) ++ targetCandidates ++ remainingCandidates.slice(
          offset,
          remainingCandidates.length)
      } else {
        remainingCandidates ++ targetCandidates
      }
      SelectorResult(
        remainingCandidates = updatedRemainingCandidates.distinct,
        result = result
      )
    } else {
      SelectorResult(remainingCandidates = remainingCandidates, result = result)
    }
  }

  override def pipelineScope: CandidateScope = AllPipelines
}
