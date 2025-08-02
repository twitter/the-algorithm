package com.twitter.product_mitwitterer.core.service.transport_marshaller_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.transport_marshaller_etwitterecutor.TransportMarshallerEtwitterecutor.Inputs
import com.twitter.product_mitwitterer.core.service.transport_marshaller_etwitterecutor.TransportMarshallerEtwitterecutor.Result
import com.twitter.stitch.Arrow
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Etwitterecutes a [[TransportMarshaller]].
 *
 * @note This is a synchronous transform, so we don't observe it directly. Failures and such
 *       can be observed at the parent pipeline.
 */
@Singleton
class TransportMarshallerEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver)
    etwittertends Etwitterecutor {

  def arrow[DomainResponseType <: HasMarshalling, TransportResponseType](
    marshaller: TransportMarshaller[DomainResponseType, TransportResponseType],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[Inputs[DomainResponseType], Result[TransportResponseType]] = {
    val arrow =
      Arrow.map[Inputs[DomainResponseType], Result[TransportResponseType]] {
        case Inputs(domainResponse) => Result(marshaller(domainResponse))
      }

    wrapComponentWithEtwitterecutorBookkeeping(contetwittert, marshaller.identifier)(arrow)
  }
}

object TransportMarshallerEtwitterecutor {
  case class Inputs[DomainResponseType <: HasMarshalling](domainResponse: DomainResponseType)
  case class Result[TransportResponseType](result: TransportResponseType) etwittertends EtwitterecutorResult
}
