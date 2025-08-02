package com.twitter.product_mitwitterer.core.service.domain_marshaller_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.domain_marshaller_etwitterecutor.DomainMarshallerEtwitterecutor.Inputs
import com.twitter.product_mitwitterer.core.service.domain_marshaller_etwitterecutor.DomainMarshallerEtwitterecutor.Result
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Etwitterecutes a [[DomainMarshaller]].
 *
 * @note This is a synchronous transform, so we don't observe it directly. Failures and such
 *       can be observed at the parent pipeline.
 */
@Singleton
class DomainMarshallerEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver)
    etwittertends Etwitterecutor {
  def arrow[Query <: PipelineQuery, DomainResponseType <: HasMarshalling](
    marshaller: DomainMarshaller[Query, DomainResponseType],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[Inputs[Query], Result[DomainResponseType]] = {
    val arrow = Arrow
      .map[Inputs[Query], DomainMarshallerEtwitterecutor.Result[DomainResponseType]] {
        case Inputs(query, candidates) =>
          DomainMarshallerEtwitterecutor.Result(marshaller(query, candidates))
      }

    wrapComponentWithEtwitterecutorBookkeeping(contetwittert, marshaller.identifier)(arrow)
  }
}

object DomainMarshallerEtwitterecutor {
  case class Inputs[Query <: PipelineQuery](
    query: Query,
    candidatesWithDetails: Seq[CandidateWithDetails])
  case class Result[+DomainResponseType](result: DomainResponseType) etwittertends EtwitterecutorResult
}
