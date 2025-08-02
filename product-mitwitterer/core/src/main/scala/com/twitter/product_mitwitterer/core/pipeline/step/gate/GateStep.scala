package com.twitter.product_mitwitterer.core.pipeline.step.gate

import com.twitter.product_mitwitterer.core.functional_component.gate.BaseGate
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.state.HasQuery
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutor
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutorResult
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * A gate step, it takes the query and the given gates and etwitterecutes them. Gates do not update state
 * if they return continue, and throw an etwitterception if any gate says stopped, thus no state changes
 * are etwitterpected in this step. The [[NewPipelineArrowBuilder]] and [[PipelineStep]] handle short
 * circuiting the pipeline's etwitterecution if this throws.
 *
 * @param gateEtwitterecutor Gate Etwitterecutor for etwitterecuting the gates
 * @tparam Query Type of PipelineQuery domain model
 * @tparam State The pipeline state domain model.
 */
case class GateStep[Query <: PipelineQuery, State <: HasQuery[Query, State]] @Inject() (
  gateEtwitterecutor: GateEtwitterecutor)
    etwittertends Step[State, Seq[BaseGate[Query]], Query, GateEtwitterecutorResult] {

  override def adaptInput(state: State, config: Seq[BaseGate[Query]]): Query = state.query

  override def arrow(
    config: Seq[BaseGate[Query]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[Query, GateEtwitterecutorResult] = gateEtwitterecutor.arrow(config, contetwittert)

  // Gate Etwitterecutor is a noop, if it continues, the state isn't changed. If it stops the world,
  // an etwitterception gets thrown.
  override def updateState(
    input: State,
    etwitterecutorResult: GateEtwitterecutorResult,
    config: Seq[BaseGate[Query]]
  ): State = input

  override def isEmpty(config: Seq[BaseGate[Query]]): Boolean = config.isEmpty
}
