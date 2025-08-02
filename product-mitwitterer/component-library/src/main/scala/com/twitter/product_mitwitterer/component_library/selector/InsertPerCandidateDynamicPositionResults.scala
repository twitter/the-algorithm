package com.twitter.product_mitwitterer.component_library.selector

import com.twitter.product_mitwitterer.core.functional_component.common.CandidateScope
import com.twitter.product_mitwitterer.core.functional_component.common.SpecificPipeline
import com.twitter.product_mitwitterer.core.functional_component.common.SpecificPipelines
import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.functional_component.selector.SelectorResult
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object InsertPerCandidateDynamicPositionResults {
  def apply[Query <: PipelineQuery](
    candidatePipeline: CandidatePipelineIdentifier,
    candidatePositionInResults: CandidatePositionInResults[Query]
  ): InsertPerCandidateDynamicPositionResults[Query] =
    InsertPerCandidateDynamicPositionResults[Query](
      SpecificPipeline(candidatePipeline),
      candidatePositionInResults)

  def apply[Query <: PipelineQuery](
    candidatePipelines: Set[CandidatePipelineIdentifier],
    candidatePositionInResults: CandidatePositionInResults[Query]
  ): InsertPerCandidateDynamicPositionResults[Query] =
    InsertPerCandidateDynamicPositionResults[Query](
      SpecificPipelines(candidatePipelines),
      candidatePositionInResults)
}

/**
 * Insert each candidate in the [[CandidateScope]] at the indetwitter relative to the original candidate in the `result`
 * at that indetwitter using the provided [[CandidatePositionInResults]] instance. If the current results are shorter
 * length than the computed position, then the candidate will be appended to the results.
 *
 * When the [[CandidatePositionInResults]] returns a `None`, that candidate is not
 * added to the result. Negative position values are treated as 0 (front of the results).
 *
 * @etwitterample if [[CandidatePositionInResults]] results in a candidate mapping from indetwitter to candidate of
 *          `{0 -> a, 0 -> b, 0 -> c, 1 -> e, 2 -> g, 2 -> h} ` with  original `results` = `[D, F]`,
 *          then the resulting output would look like `[a, b, c, D, e, F, g, h]`
 */
case class InsertPerCandidateDynamicPositionResults[-Query <: PipelineQuery](
  pipelineScope: CandidateScope,
  candidatePositionInResults: CandidatePositionInResults[Query])
    etwittertends Selector[Query] {

  override def apply(
    query: Query,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val (candidatesToInsert, otherRemainingCandidatesTuples) = remainingCandidates
      .map { candidate: CandidateWithDetails =>
        val position =
          if (pipelineScope.contains(candidate))
            candidatePositionInResults(query, candidate, result)
          else
            None
        (position, candidate)
      }.partition { case (indetwitter, _) => indetwitter.isDefined }

    val otherRemainingCandidates = otherRemainingCandidatesTuples.map {
      case (_, candidate) => candidate
    }

    val positionAndCandidateList = candidatesToInsert.collect {
      case (Some(position), candidate) => (position, candidate)
    }

    val mergedResult = DynamicPositionSelector.mergeByIndetwitterIntoResult(
      positionAndCandidateList,
      result,
      DynamicPositionSelector.RelativeIndices
    )

    SelectorResult(remainingCandidates = otherRemainingCandidates, result = mergedResult)
  }
}
