package com.twitter.product_mitwitterer.core.pipeline.step.side_effect

import com.twitter.product_mitwitterer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.state.HasEtwitterecutorResults
import com.twitter.product_mitwitterer.core.pipeline.state.HasQuery
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.domain_marshaller_etwitterecutor.DomainMarshallerEtwitterecutor
import com.twitter.product_mitwitterer.core.service.pipeline_result_side_effect_etwitterecutor.PipelineResultSideEffectEtwitterecutor
import com.twitter.product_mitwitterer.core.service.selector_etwitterecutor.SelectorEtwitterecutorResult
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * A side effect step, it takes the input list of side effects and and etwitterecutes them.
 *
 * @param sideEffectEtwitterecutor Side Effect Etwitterecutor
 *
 * @tparam Query Type of PipelineQuery domain model
 * @tparam DomainResultType Domain Marshaller result type
 * @tparam State The pipeline state domain model.
 */
case class SideEffectStep[
  Query <: PipelineQuery,
  DomainResultType <: HasMarshalling,
  State <: HasQuery[Query, State] with HasEtwitterecutorResults[State]] @Inject() (
  sideEffectEtwitterecutor: PipelineResultSideEffectEtwitterecutor)
    etwittertends Step[
      State,
      PipelineStepConfig[Query, DomainResultType],
      PipelineResultSideEffect.Inputs[
        Query,
        DomainResultType
      ],
      PipelineResultSideEffectEtwitterecutor.Result
    ] {
  override def isEmpty(config: PipelineStepConfig[Query, DomainResultType]): Boolean =
    config.sideEffects.isEmpty

  override def adaptInput(
    state: State,
    config: PipelineStepConfig[Query, DomainResultType]
  ): PipelineResultSideEffect.Inputs[Query, DomainResultType] = {
    val selectorResults = state.etwitterecutorResultsByPipelineStep
      .getOrElse(
        config.selectorStepIdentifier,
        throw PipelineFailure(
          IllegalStateFailure,
          "Missing Selector Result in Side Effect Step")).asInstanceOf[SelectorEtwitterecutorResult]

    val domainMarshallerResult = state.etwitterecutorResultsByPipelineStep
      .getOrElse(
        config.domainMarshallerStepIdentifier,
        throw PipelineFailure(
          IllegalStateFailure,
          "Missing Domain Marshaller Result in Side Effect Step")).asInstanceOf[
        DomainMarshallerEtwitterecutor.Result[DomainResultType]]

    PipelineResultSideEffect.Inputs(
      query = state.query,
      selectedCandidates = selectorResults.selectedCandidates,
      remainingCandidates = selectorResults.remainingCandidates,
      droppedCandidates = selectorResults.droppedCandidates,
      response = domainMarshallerResult.result
    )
  }

  override def arrow(
    config: PipelineStepConfig[Query, DomainResultType],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[
    PipelineResultSideEffect.Inputs[Query, DomainResultType],
    PipelineResultSideEffectEtwitterecutor.Result
  ] = sideEffectEtwitterecutor.arrow(config.sideEffects, contetwittert)

  override def updateState(
    state: State,
    etwitterecutorResult: PipelineResultSideEffectEtwitterecutor.Result,
    config: PipelineStepConfig[Query, DomainResultType]
  ): State = state
}

/**
 * Wrapper case class containing side effects to be etwitterecuted and other information needed to etwitterecute
 * @param sideEffects The side effects to etwitterecute.
 * @param selectorStepIdentifier The identifier of the selector step in the parent
 *                               pipeline to get selection results from.
 * @param domainMarshallerStepIdentifier The identifier of the domain marshaller step in the parent
 *                                       pipeline to get domain marshalled results from.
 *
 * @tparam Query Type of PipelineQuery domain model
 * @tparam DomainResultType Domain Marshaller result type
 */
case class PipelineStepConfig[Query <: PipelineQuery, DomainResultType <: HasMarshalling](
  sideEffects: Seq[PipelineResultSideEffect[Query, DomainResultType]],
  selectorStepIdentifier: PipelineStepIdentifier,
  domainMarshallerStepIdentifier: PipelineStepIdentifier)
