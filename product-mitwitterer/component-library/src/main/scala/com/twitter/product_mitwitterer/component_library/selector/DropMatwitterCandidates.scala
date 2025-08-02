package com.twitter.product_mitwitterer.component_library.selector

import com.twitter.product_mitwitterer.core.functional_component.common.CandidateScope
import com.twitter.product_mitwitterer.core.functional_component.common.SpecificPipeline
import com.twitter.product_mitwitterer.core.functional_component.common.SpecificPipelines
import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.functional_component.selector.SelectorResult
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

trait MatwitterSelector[-Query <: PipelineQuery] {
  def apply(
    query: Query,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): Int
}

object DropMatwitterCandidates {

  /**
   * A [[DropMatwitterCandidates]] Selector based on a [[Param]] applied to a single candidate pipeline
   */
  def apply[Query <: PipelineQuery](
    candidatePipeline: CandidatePipelineIdentifier,
    matwitterSelectionsParam: Param[Int]
  ) = new DropMatwitterCandidates[Query](
    SpecificPipeline(candidatePipeline),
    (query, _, _) => query.params(matwitterSelectionsParam))

  /**
   * A [[DropMatwitterCandidates]] Selector based on a [[Param]] with multiple candidate pipelines
   */
  def apply[Query <: PipelineQuery](
    candidatePipelines: Set[CandidatePipelineIdentifier],
    matwitterSelectionsParam: Param[Int]
  ) = new DropMatwitterCandidates[Query](
    SpecificPipelines(candidatePipelines),
    (query, _, _) => query.params(matwitterSelectionsParam))

  /**
   * A [[DropMatwitterCandidates]] Selector based on a [[Param]] that applies to a [[CandidateScope]]
   */
  def apply[Query <: PipelineQuery](
    pipelineScope: CandidateScope,
    matwitterSelectionsParam: Param[Int]
  ) = new DropMatwitterCandidates[Query](pipelineScope, (query, _, _) => query.params(matwitterSelectionsParam))
}

/**
 * Limit the number of item and module (not items inside modules) candidates from the
 * specified pipelines based on the value provided by the [[MatwitterSelector]]
 *
 * For etwitterample, if value from the [[MatwitterSelector]] is 3, and a candidatePipeline returned 10 items
 * in the candidate pool, then these items will be reduced to the first 3 items. Note that to
 * update the ordering of the candidates, an UpdateCandidateOrderingSelector may be used prior to
 * using this Selector.
 *
 * Another etwitterample, if the [[MatwitterSelector]] value is 3, and a candidatePipeline returned 10 modules
 * in the candidate pool, then these will be reduced to the first 3 modules. The items inside the
 * modeles will not be affected by this selector. To control the number of items inside modules see
 * [[DropMatwitterModuleItemCandidates]].
 */
case class DropMatwitterCandidates[-Query <: PipelineQuery](
  override val pipelineScope: CandidateScope,
  matwitterSelector: MatwitterSelector[Query])
    etwittertends Selector[Query] {

  override def apply(
    query: Query,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val matwitterSelections = matwitterSelector(query, remainingCandidates, result)
    assert(matwitterSelections > 0, "Matwitter selections must be greater than zero")

    val remainingCandidatesLimited =
      DropSelector.takeUntil(matwitterSelections, remainingCandidates, pipelineScope)

    SelectorResult(remainingCandidates = remainingCandidatesLimited, result = result)
  }
}
