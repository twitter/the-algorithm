package com.twitter.product_mitwitterer.core.pipeline.step.transport_marshaller

import com.twitter.product_mitwitterer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.state.HasEtwitterecutorResults
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.domain_marshaller_etwitterecutor.DomainMarshallerEtwitterecutor
import com.twitter.product_mitwitterer.core.service.transport_marshaller_etwitterecutor.TransportMarshallerEtwitterecutor
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * A transport marshaller step, it takes domain marshalled result as input and returns trasnport
 * ready marshalled object.
 * The [[State]] object is responsible for keeping a reference of the built marshalled response.
 *
 * @param transportMarshallerEtwitterecutor Domain Marshaller etwitterecutor.
 * @tparam Query Type of PipelineQuery domain model
 * @tparam DomainResponseType the domain marshalling type used as input
 * @tparam TransportResponseType the etwitterpected returned transport type
 * @tparam State The pipeline state domain model.
 */
case class TransportMarshallerStep[
  DomainResponseType <: HasMarshalling,
  TransportResponseType,
  State <: HasEtwitterecutorResults[State]] @Inject() (
  transportMarshallerEtwitterecutor: TransportMarshallerEtwitterecutor)
    etwittertends Step[
      State,
      TransportMarshallerConfig[DomainResponseType, TransportResponseType],
      TransportMarshallerEtwitterecutor.Inputs[DomainResponseType],
      TransportMarshallerEtwitterecutor.Result[TransportResponseType]
    ] {

  override def isEmpty(
    config: TransportMarshallerConfig[DomainResponseType, TransportResponseType]
  ): Boolean = false

  override def adaptInput(
    state: State,
    config: TransportMarshallerConfig[DomainResponseType, TransportResponseType]
  ): TransportMarshallerEtwitterecutor.Inputs[DomainResponseType] = {
    val domainMarshallerResult = state.etwitterecutorResultsByPipelineStep
      .getOrElse(
        config.domainMarshallerStepIdentifier,
        throw PipelineFailure(
          IllegalStateFailure,
          "Missing Domain Marshaller in Transport Marshaller Step")).asInstanceOf[
        DomainMarshallerEtwitterecutor.Result[DomainResponseType]]
    TransportMarshallerEtwitterecutor.Inputs(domainMarshallerResult.result)
  }

  // Noop as platform updates etwitterecutor result
  override def updateState(
    state: State,
    etwitterecutorResult: TransportMarshallerEtwitterecutor.Result[TransportResponseType],
    config: TransportMarshallerConfig[DomainResponseType, TransportResponseType]
  ): State = state

  override def arrow(
    config: TransportMarshallerConfig[DomainResponseType, TransportResponseType],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[TransportMarshallerEtwitterecutor.Inputs[
    DomainResponseType
  ], TransportMarshallerEtwitterecutor.Result[TransportResponseType]] =
    transportMarshallerEtwitterecutor.arrow(config.transportMarshaller, contetwittert)

}

case class TransportMarshallerConfig[DomainResponseType <: HasMarshalling, TransportResponseType](
  transportMarshaller: TransportMarshaller[DomainResponseType, TransportResponseType],
  domainMarshallerStepIdentifier: PipelineStepIdentifier)
