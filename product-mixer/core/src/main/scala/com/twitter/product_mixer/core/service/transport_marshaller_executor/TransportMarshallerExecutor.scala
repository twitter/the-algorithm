package com.tw ter.product_m xer.core.serv ce.transport_marshaller_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.transport_marshaller_executor.TransportMarshallerExecutor. nputs
 mport com.tw ter.product_m xer.core.serv ce.transport_marshaller_executor.TransportMarshallerExecutor.Result
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Executes a [[TransportMarshaller]].
 *
 * @note T   s a synchronous transform, so   don't observe   d rectly. Fa lures and such
 *       can be observed at t  parent p pel ne.
 */
@S ngleton
class TransportMarshallerExecutor @ nject() (overr de val statsRece ver: StatsRece ver)
    extends Executor {

  def arrow[Doma nResponseType <: HasMarshall ng, TransportResponseType](
    marshaller: TransportMarshaller[Doma nResponseType, TransportResponseType],
    context: Executor.Context
  ): Arrow[ nputs[Doma nResponseType], Result[TransportResponseType]] = {
    val arrow =
      Arrow.map[ nputs[Doma nResponseType], Result[TransportResponseType]] {
        case  nputs(doma nResponse) => Result(marshaller(doma nResponse))
      }

    wrapComponentW hExecutorBookkeep ng(context, marshaller. dent f er)(arrow)
  }
}

object TransportMarshallerExecutor {
  case class  nputs[Doma nResponseType <: HasMarshall ng](doma nResponse: Doma nResponseType)
  case class Result[TransportResponseType](result: TransportResponseType) extends ExecutorResult
}
