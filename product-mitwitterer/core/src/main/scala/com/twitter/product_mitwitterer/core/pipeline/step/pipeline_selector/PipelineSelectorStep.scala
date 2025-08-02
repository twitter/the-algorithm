package com.twitter.product_mitwitterer.core.pipeline.step.pipeline_selector

import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.state.HasQuery
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * Pipeline Selection step to decide which pipeline to etwitterecute. This step doesn't update state, as
 * the selected pipeline identifier is added to the etwitterecutor results list map for later retrieval
 *
 * @tparam Query Pipeline query model
 * @tparam State The pipeline state domain model.
 */
case class PipelineSelectorStep[Query <: PipelineQuery, State <: HasQuery[Query, State]] @Inject() (
) etwittertends Step[State, Query => ComponentIdentifier, Query, PipelineSelectorResult] {
  override def isEmpty(config: Query => ComponentIdentifier): Boolean = false

  override def adaptInput(
    state: State,
    config: Query => ComponentIdentifier
  ): Query = state.query

  override def arrow(
    config: Query => ComponentIdentifier,
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[Query, PipelineSelectorResult] = Arrow.map { query: Query =>
    PipelineSelectorResult(config(query))
  }

  // Noop since we keep the identifier in the etwitterecutor results
  override def updateState(
    state: State,
    etwitterecutorResult: PipelineSelectorResult,
    config: Query => ComponentIdentifier
  ): State = state
}

case class PipelineSelectorResult(pipelineIdentifier: ComponentIdentifier) etwittertends EtwitterecutorResult
