package com.twitter.product_mitwitterer.core.pipeline.step.selector

import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.state.HasCandidatesWithDetails
import com.twitter.product_mitwitterer.core.pipeline.state.HasQuery
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutor
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutorResult
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * A selection step, it takes the input list of candidates with details and the given
 * selectors and etwitterecutes them to decide which candidates should be selected.
 *
 * @param selectorEtwitterecutor Selector Etwitterecutor
 * @tparam Query Type of PipelineQuery domain model
 * @tparam State The pipeline state domain model.
 */
case class SelectorStep[
  Query <: PipelineQuery,
  State <: HasQuery[Query, State] with HasCandidatesWithDetails[State]] @Inject() (
  selectorEtwitterecutor: SelectorEtwitterecutor)
    etwittertends Step[State, Seq[
      Selector[Query]
    ], SelectorEtwitterecutor.Inputs[
      Query
    ], SelectorEtwitterecutorResult] {

  override def adaptInput(
    state: State,
    config: Seq[Selector[Query]]
  ): SelectorEtwitterecutor.Inputs[Query] =
    SelectorEtwitterecutor.Inputs(state.query, state.candidatesWithDetails)

  override def arrow(
    config: Seq[Selector[Query]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[SelectorEtwitterecutor.Inputs[Query], SelectorEtwitterecutorResult] =
    selectorEtwitterecutor.arrow(config, contetwittert)

  override def updateState(
    input: State,
    etwitterecutorResult: SelectorEtwitterecutorResult,
    config: Seq[Selector[Query]]
  ): State = input.updateCandidatesWithDetails(etwitterecutorResult.selectedCandidates)

  // Selection is a bit different to other steps (i.e, other steps, empty means don't change anything)
  // where an empty selection list drops all candidates.
  override def isEmpty(config: Seq[Selector[Query]]): Boolean = false
}
