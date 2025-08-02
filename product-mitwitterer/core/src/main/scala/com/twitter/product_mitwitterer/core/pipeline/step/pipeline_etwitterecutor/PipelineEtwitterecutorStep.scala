package com.twitter.product_mitwitterer.core.pipeline.step.pipeline_etwitterecutor

import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.pipeline.Pipeline
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.state.HasEtwitterecutorResults
import com.twitter.product_mitwitterer.core.pipeline.state.HasQuery
import com.twitter.product_mitwitterer.core.pipeline.state.HasResult
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.pipeline.step.pipeline_selector.PipelineSelectorResult
import com.twitter.product_mitwitterer.core.quality_factor.QualityFactorObserver
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.pipeline_etwitterecutor.PipelineEtwitterecutor
import com.twitter.product_mitwitterer.core.service.pipeline_etwitterecutor.PipelineEtwitterecutorRequest
import com.twitter.product_mitwitterer.core.service.pipeline_etwitterecutor.PipelineEtwitterecutorResult
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * Pipeline Etwitterecution step that takes a selected pipeline and etwitterecutes it.
 *
 * @param pipelineEtwitterecutor Pipeline etwitterecutor that etwitterecutes the selected pipeline
 *
 * @tparam Query Pipeline query model with quality factor status
 * @tparam Result The etwitterpected result type
 * @tparam State The pipeline state domain model.
 */
case class PipelineEtwitterecutorStep[
  Query <: PipelineQuery,
  Result,
  State <: HasQuery[Query, State] with HasEtwitterecutorResults[State] with HasResult[Result]] @Inject() (
  pipelineEtwitterecutor: PipelineEtwitterecutor)
    etwittertends Step[
      State,
      PipelineEtwitterecutorStepConfig[Query, Result],
      PipelineEtwitterecutorRequest[Query],
      PipelineEtwitterecutorResult[Result]
    ] {

  override def isEmpty(config: PipelineEtwitterecutorStepConfig[Query, Result]): Boolean =
    false

  override def adaptInput(
    state: State,
    config: PipelineEtwitterecutorStepConfig[Query, Result]
  ): PipelineEtwitterecutorRequest[Query] = {
    val pipelineSelectorResult = state.etwitterecutorResultsByPipelineStep
      .getOrElse(
        config.selectedPipelineResultIdentifier,
        throw PipelineFailure(
          IllegalStateFailure,
          "Missing Selected Pipeline in Pipeline Etwitterecutor Step")).asInstanceOf[
        PipelineSelectorResult]
    PipelineEtwitterecutorRequest(state.query, pipelineSelectorResult.pipelineIdentifier)
  }

  override def arrow(
    config: PipelineEtwitterecutorStepConfig[Query, Result],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[PipelineEtwitterecutorRequest[Query], PipelineEtwitterecutorResult[Result]] = pipelineEtwitterecutor.arrow(
    config.pipelinesByIdentifier,
    config.qualityFactorObserversByIdentifier,
    contetwittert
  )

  // Noop since the platform will add the final result to the etwitterecutor result map then state
  // is responsible for reading it in [[WithResult]]
  override def updateState(
    state: State,
    etwitterecutorResult: PipelineEtwitterecutorResult[Result],
    config: PipelineEtwitterecutorStepConfig[Query, Result]
  ): State = state
}

case class PipelineEtwitterecutorStepConfig[Query <: PipelineQuery, Result](
  pipelinesByIdentifier: Map[ComponentIdentifier, Pipeline[Query, Result]],
  selectedPipelineResultIdentifier: PipelineStepIdentifier,
  qualityFactorObserversByIdentifier: Map[ComponentIdentifier, QualityFactorObserver])
