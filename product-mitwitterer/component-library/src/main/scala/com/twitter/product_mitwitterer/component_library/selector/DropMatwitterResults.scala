package com.twitter.product_mitwitterer.component_library.selector

import com.twitter.product_mitwitterer.core.functional_component.common.AllPipelines
import com.twitter.product_mitwitterer.core.functional_component.common.CandidateScope
import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.functional_component.selector.SelectorResult
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

/**
 * Limit the number of results
 *
 * For etwitterample, if matwitterResultsParam is 3, and the results contain 10 items, then these items will be
 * reduced to the first 3 selected items. Note that the ordering of results is determined by the
 * selector configuration.
 *
 * Another etwitterample, if matwitterResultsParam is 3, and the results contain 10 modules, then these will be
 * reduced to the first 3 modules. The items inside the modules will not be affected by this
 * selector.
 */
case class DropMatwitterResults(
  matwitterResultsParam: Param[Int])
    etwittertends Selector[PipelineQuery] {

  override val pipelineScope: CandidateScope = AllPipelines

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val matwitterResults = query.params(matwitterResultsParam)
    assert(matwitterResults > 0, "Matwitter results must be greater than zero")

    val resultUpdated = DropSelector.takeUntil(matwitterResults, result)

    SelectorResult(remainingCandidates = remainingCandidates, result = resultUpdated)
  }
}
