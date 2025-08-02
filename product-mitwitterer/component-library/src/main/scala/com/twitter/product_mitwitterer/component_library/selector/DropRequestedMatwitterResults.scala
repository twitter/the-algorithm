package com.twitter.product_mitwitterer.component_library.selector

import com.twitter.product_mitwitterer.core.functional_component.common.AllPipelines
import com.twitter.product_mitwitterer.core.functional_component.common.CandidateScope
import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.functional_component.selector.SelectorResult
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.BoundedParam

/**
 * Limit the number of results to min(PipelineQuery.requestedMatwitterResults, ServerMatwitterResultsParam)
 *
 * PipelineQuery.requestedMatwitterResults is optionally set in the pipelineQuery.
 * If it is not set, then the default value of DefaultRequestedMatwitterResultsParam is used.
 *
 * ServerMatwitterResultsParam specifies the matwitterimum number of results supported, irrespective of what is
 * specified by the client in PipelineQuery.requestedMatwitterResults
 * (or the DefaultRequestedMatwitterResultsParam default if not specified)
 *
 * For etwitterample, if ServerMatwitterResultsParam is 5, PipelineQuery.requestedMatwitterResults is 3,
 * and the results contain 10 items, then these items will be reduced to the first 3 selected items.
 *
 * If PipelineQuery.requestedMatwitterResults is not set, DefaultRequestedMatwitterResultsParam is 3,
 * ServerMatwitterResultsParam is 5 and the results contain 10 items,
 * then these items will be reduced to the first 3 selected items.
 *
 * Another etwitterample, if ServerMatwitterResultsParam is 5, PipelineQuery.requestedMatwitterResults is 8,
 * and the results contain 10 items, then these will be reduced to the first 5 selected items.
 *
 * The items inside the modules will not be affected by this selector.
 */
case class DropRequestedMatwitterResults(
  defaultRequestedMatwitterResultsParam: BoundedParam[Int],
  serverMatwitterResultsParam: BoundedParam[Int])
    etwittertends Selector[PipelineQuery] {

  override val pipelineScope: CandidateScope = AllPipelines

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val requestedMatwitterResults = query.matwitterResults(defaultRequestedMatwitterResultsParam)
    val serverMatwitterResults = query.params(serverMatwitterResultsParam)
    assert(requestedMatwitterResults > 0, "Requested matwitter results must be greater than zero")
    assert(serverMatwitterResults > 0, "Server matwitter results must be greater than zero")

    val appliedMatwitterResults = Math.min(requestedMatwitterResults, serverMatwitterResults)
    val resultUpdated = DropSelector.takeUntil(appliedMatwitterResults, result)

    SelectorResult(remainingCandidates = remainingCandidates, result = resultUpdated)
  }
}
