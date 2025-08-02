package com.twitter.product_mitwitterer.core.pipeline.step.domain_marshaller

import com.twitter.product_mitwitterer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.state.HasCandidatesWithDetails
import com.twitter.product_mitwitterer.core.pipeline.state.HasQuery
import com.twitter.product_mitwitterer.core.pipeline.step.Step
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.domain_marshaller_etwitterecutor.DomainMarshallerEtwitterecutor
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject

/**
 * A domain marshaller step, it takes the input list of candidates and the given
 * domain marshaller and etwitterecutes its to return a marshalled result. The [[State]] object is
 * responsible for keeping a reference of the built Response.
 *
 * @param domainMarshallerEtwitterecutor Domain Marshaller etwitterecutor.
 * @tparam Query Type of PipelineQuery domain model
 * @tparam ResponseType the domain marshalling type etwitterpected to be returned.
 * @tparam State The pipeline state domain model.
 */
case class DomainMarshallerStep[
  Query <: PipelineQuery,
  ResponseType <: HasMarshalling,
  State <: HasQuery[Query, State] with HasCandidatesWithDetails[State]] @Inject() (
  domainMarshallerEtwitterecutor: DomainMarshallerEtwitterecutor)
    etwittertends Step[State, DomainMarshaller[Query, ResponseType], DomainMarshallerEtwitterecutor.Inputs[
      Query
    ], DomainMarshallerEtwitterecutor.Result[ResponseType]] {

  override def isEmpty(config: DomainMarshaller[Query, ResponseType]): Boolean = false

  override def adaptInput(
    state: State,
    config: DomainMarshaller[Query, ResponseType]
  ): DomainMarshallerEtwitterecutor.Inputs[Query] =
    DomainMarshallerEtwitterecutor.Inputs(state.query, state.candidatesWithDetails)

  override def arrow(
    config: DomainMarshaller[Query, ResponseType],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[DomainMarshallerEtwitterecutor.Inputs[Query], DomainMarshallerEtwitterecutor.Result[ResponseType]] =
    domainMarshallerEtwitterecutor.arrow(config, contetwittert)

  // Noop since the pipeline updates the etwitterecutor results for us
  override def updateState(
    state: State,
    etwitterecutorResult: DomainMarshallerEtwitterecutor.Result[ResponseType],
    config: DomainMarshaller[Query, ResponseType]
  ): State = state

}
