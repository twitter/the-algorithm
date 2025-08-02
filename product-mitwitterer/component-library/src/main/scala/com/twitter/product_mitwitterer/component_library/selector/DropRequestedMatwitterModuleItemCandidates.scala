package com.twitter.product_mitwitterer.component_library.selector

import com.twitter.product_mitwitterer.core.functional_component.common.CandidateScope
import com.twitter.product_mitwitterer.core.functional_component.common.SpecificPipeline
import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.functional_component.selector.SelectorResult
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.model.common.presentation.ModuleCandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

/**
 * Limit the number of results (for 1 or more modules) from a certain candidate
 * source to PipelineQuery.requestedMatwitterResults.
 *
 * PipelineQuery.requestedMatwitterResults is optionally set in the pipelineQuery.
 * If it is not set, then the default value of DefaultRequestedMatwitterModuleItemsParam is used.
 *
 * For etwitterample, if PipelineQuery.requestedMatwitterResults is 3, and a candidatePipeline returned 1 module
 * containing 10 items in the candidate pool, then these module items will be reduced to the first 3
 * module items. Note that to update the ordering of the candidates, an
 * UpdateModuleItemsCandidateOrderingSelector may be used prior to using this selector.
 *
 * Another etwitterample, if PipelineQuery.requestedMatwitterResults is 3, and a candidatePipeline returned 5
 * modules each containing 10 items in the candidate pool, then the module items in each of the 5
 * modules will be reduced to the first 3 module items.
 *
 * @note this updates the module in the `remainingCandidates`
 */
case class DropRequestedMatwitterModuleItemCandidates(
  override val pipelineScope: CandidateScope,
  defaultRequestedMatwitterModuleItemResultsParam: Param[Int])
    etwittertends Selector[PipelineQuery] {
  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {

    val requestedMatwitterModuleItemSelections =
      query.matwitterResults(defaultRequestedMatwitterModuleItemResultsParam)
    assert(
      requestedMatwitterModuleItemSelections > 0,
      "Requested Matwitter module item selections must be greater than zero")

    val resultUpdated = result.map {
      case module: ModuleCandidateWithDetails if pipelineScope.contains(module) =>
        // this applies to all candidates in a module, even if they are from a different
        // candidate source which can happen if items are added to a module during selection
        module.copy(candidates =
          DropSelector.takeUntil(requestedMatwitterModuleItemSelections, module.candidates))
      case candidate => candidate
    }

    SelectorResult(remainingCandidates = remainingCandidates, result = resultUpdated)
  }
}

object DropRequestedMatwitterModuleItemCandidates {
  def apply(
    candidatePipeline: CandidatePipelineIdentifier,
    defaultRequestedMatwitterModuleItemResultsParam: Param[Int]
  ) =
    new DropRequestedMatwitterModuleItemCandidates(
      SpecificPipeline(candidatePipeline),
      defaultRequestedMatwitterModuleItemResultsParam)
}
