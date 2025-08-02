package com.twitter.product_mitwitterer.core.pipeline

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.state.HasEtwitterecutorResults
import com.twitter.product_mitwitterer.core.pipeline.state.HasResult
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.quality_factor.QualityFactorStatus
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.Etwitterecutor.Contetwittert
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult
import com.twitter.stitch.Arrow
import com.twitter.stitch.Arrow.Iso
import com.twitter.util.Return
import com.twitter.util.Throw

/**
 * Pipeline Arrow Builder used for constructing a final arrow for a pipeline after adding necessary
 * steps.
 *
 * @param steps The kept non-empty Pipeline Steps
 * @param addedSteps Steps that have been added, but not necessarily kept.
 * @param statsReceiver Stats Receiver for metric book keeping
 * @tparam Result sThe etwitterpected final result type of the pipeline.
 * @tparam State The input state type, which should implement [[HasResult]].
 */
case class NewPipelineArrowBuilder[
  Result,
  State <: HasEtwitterecutorResults[State] with HasResult[Result]
] private (
  private val steps: Seq[PipelineStep[State, _, _, _]],
  override val statsReceiver: StatsReceiver)
    etwittertends Etwitterecutor {

  def add[Config, EtwitterecutorInput, EtwitterResult <: EtwitterecutorResult](
    pipelineStepIdentifier: PipelineStepIdentifier,
    step: Step[State, Config, EtwitterecutorInput, EtwitterResult],
    etwitterecutorConfig: Config
  ): NewPipelineArrowBuilder[Result, State] = {
    require(
      !steps.contains(pipelineStepIdentifier),
      s"Found duplicate step $pipelineStepIdentifier when building pipeline arrow")

    // If the step has nothing to etwitterecute, drop it for simplification but still added it to the
    // "addedSteps" field for build time validation
    if (step.isEmpty(etwitterecutorConfig)) {
      this
    } else {
      val newPipelineStep =
        PipelineStep(pipelineStepIdentifier, etwitterecutorConfig, step)
      val newSteps = steps :+ newPipelineStep
      this.copy(steps = newSteps)
    }
  }

  def buildArrow(
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[State, NewPipelineResult[Result]] = {
    val initialArrow = Arrow
      .map { input: State => NewStepData[State](input) }
    val allStepArrows = steps.map { step =>
      Iso.onlyIf[NewStepData[State]] { stepData => !stepData.stopEtwitterecuting } {
        wrapStepWithEtwitterecutorBookkeeping(step, contetwittert)
      }
    }
    val combinedArrow = isoArrowsSequentially(allStepArrows)
    val resultArrow = Arrow.map { stepData: NewStepData[State] =>
      stepData.pipelineFailure match {
        case Some(failure) =>
          NewPipelineResult.Failure(failure, stepData.pipelineState.etwitterecutorResultsByPipelineStep)
        case None =>
          NewPipelineResult.Success(
            stepData.pipelineState.buildResult,
            stepData.pipelineState.etwitterecutorResultsByPipelineStep)
      }
    }
    initialArrow.andThen(combinedArrow).andThen(resultArrow)
  }

  private[this] def wrapStepWithEtwitterecutorBookkeeping(
    step: PipelineStep[State, _, _, _],
    contetwittert: Contetwittert
  ): Arrow.Iso[NewStepData[State]] = {
    val wrapped = wrapStepWithEtwitterecutorBookkeeping[NewStepData[State], NewStepData[State]](
      contetwittert = contetwittert,
      identifier = step.stepIdentifier,
      arrow = step.arrow(contetwittert),
      // etwittertract the failure only if it's present. Not sure if this is needed???
      transformer = _.pipelineFailure.map(Throw(_)).getOrElse(Return.Unit)
    )

    Arrow
      .zipWithArg(wrapped.liftToTry)
      .map {
        case (_: NewStepData[State], Return(result)) =>
          // if Step was successful, return the result
          result
        case (previous: NewStepData[State], Throw(pipelineFailure: PipelineFailure)) =>
          // if the Step failed in such a way that the failure was NOT captured
          // in the result object, then update the State with the failure
          previous.withFailure(pipelineFailure)
        case (_, Throw(etwitter)) =>
          // an etwitterception was thrown which was not handled by the failure classifier
          // this only happens with cancellation etwitterceptions which are re-thrown
          throw etwitter
      }
  }

  /**
   * Sets up stats [[com.twitter.finagle.stats.Gauge]]s for any [[QualityFactorStatus]]
   *
   * @note We use provideGauge so these gauges live forever even without a reference.
   */
  private[pipeline] def buildGaugesForQualityFactor(
    pipelineIdentifier: ComponentIdentifier,
    qualityFactorStatus: QualityFactorStatus,
    statsReceiver: StatsReceiver
  ): Unit = {
    qualityFactorStatus.qualityFactorByPipeline.foreach {
      case (identifier, qualityFactor) =>
        // QF is a relative stat (since the parent pipeline is monitoring a child pipeline)
        val scopes = pipelineIdentifier.toScopes ++ identifier.toScopes :+ "QualityFactor"
        statsReceiver.provideGauge(scopes: _*) { qualityFactor.currentValue.toFloat }
    }
  }
}

object NewPipelineArrowBuilder {
  def apply[Result, InputState <: HasEtwitterecutorResults[InputState] with HasResult[Result]](
    statsReceiver: StatsReceiver
  ): NewPipelineArrowBuilder[Result, InputState] = {
    NewPipelineArrowBuilder(
      Seq.empty,
      statsReceiver
    )
  }
}

/**
 * This is a pipeline specific instance of a step, i.e, a generic step with the step identifier
 * within the pipeline and its etwitterecutor configs.
 * @param stepIdentifier Step identifier of the step within a pipeline
 * @param etwitterecutorConfig Config to etwitterecute the step with
 * @param step The underlying step to be used
 * @tparam InputState The input state object
 * @tparam EtwitterecutorConfig The config etwitterpected for the given step
 * @tparam EtwitterecutorInput Input for the underlying etwitterecutor
 * @tparam EtwitterecResult The result type
 */
case class PipelineStep[
  State <: HasEtwitterecutorResults[State],
  PipelineStepConfig,
  EtwitterecutorInput,
  EtwitterecResult <: EtwitterecutorResult
](
  stepIdentifier: PipelineStepIdentifier,
  etwitterecutorConfig: PipelineStepConfig,
  step: Step[State, PipelineStepConfig, EtwitterecutorInput, EtwitterecResult]) {

  def arrow(
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow.Iso[NewStepData[State]] = {
    val inputArrow = Arrow.map { stepData: NewStepData[State] =>
      step.adaptInput(stepData.pipelineState, etwitterecutorConfig)
    }

    Arrow
      .zipWithArg(inputArrow.andThen(step.arrow(etwitterecutorConfig, contetwittert))).map {
        case (stepData: NewStepData[State], etwitterecutorResult: EtwitterecResult @unchecked) =>
          val updatedResultsByPipelineStep =
            stepData.pipelineState.etwitterecutorResultsByPipelineStep + (stepIdentifier -> etwitterecutorResult)
          val updatedPipelineState = step
            .updateState(stepData.pipelineState, etwitterecutorResult, etwitterecutorConfig).setEtwitterecutorResults(
              updatedResultsByPipelineStep)

          NewStepData(updatedPipelineState)
      }
  }
}
