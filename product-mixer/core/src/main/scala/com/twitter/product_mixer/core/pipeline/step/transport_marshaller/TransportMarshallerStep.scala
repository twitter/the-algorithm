package com.tw ter.product_m xer.core.p pel ne.step.transport_marshaller

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. llegalStateFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.state.HasExecutorResults
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.doma n_marshaller_executor.Doma nMarshallerExecutor
 mport com.tw ter.product_m xer.core.serv ce.transport_marshaller_executor.TransportMarshallerExecutor
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * A transport marshaller step,   takes doma n marshalled result as  nput and returns trasnport
 * ready marshalled object.
 * T  [[State]] object  s respons ble for keep ng a reference of t  bu lt marshalled response.
 *
 * @param transportMarshallerExecutor Doma n Marshaller executor.
 * @tparam Query Type of P pel neQuery doma n model
 * @tparam Doma nResponseType t  doma n marshall ng type used as  nput
 * @tparam TransportResponseType t  expected returned transport type
 * @tparam State T  p pel ne state doma n model.
 */
case class TransportMarshallerStep[
  Doma nResponseType <: HasMarshall ng,
  TransportResponseType,
  State <: HasExecutorResults[State]] @ nject() (
  transportMarshallerExecutor: TransportMarshallerExecutor)
    extends Step[
      State,
      TransportMarshallerConf g[Doma nResponseType, TransportResponseType],
      TransportMarshallerExecutor. nputs[Doma nResponseType],
      TransportMarshallerExecutor.Result[TransportResponseType]
    ] {

  overr de def  sEmpty(
    conf g: TransportMarshallerConf g[Doma nResponseType, TransportResponseType]
  ): Boolean = false

  overr de def adapt nput(
    state: State,
    conf g: TransportMarshallerConf g[Doma nResponseType, TransportResponseType]
  ): TransportMarshallerExecutor. nputs[Doma nResponseType] = {
    val doma nMarshallerResult = state.executorResultsByP pel neStep
      .getOrElse(
        conf g.doma nMarshallerStep dent f er,
        throw P pel neFa lure(
           llegalStateFa lure,
          "M ss ng Doma n Marshaller  n Transport Marshaller Step")).as nstanceOf[
        Doma nMarshallerExecutor.Result[Doma nResponseType]]
    TransportMarshallerExecutor. nputs(doma nMarshallerResult.result)
  }

  // Noop as platform updates executor result
  overr de def updateState(
    state: State,
    executorResult: TransportMarshallerExecutor.Result[TransportResponseType],
    conf g: TransportMarshallerConf g[Doma nResponseType, TransportResponseType]
  ): State = state

  overr de def arrow(
    conf g: TransportMarshallerConf g[Doma nResponseType, TransportResponseType],
    context: Executor.Context
  ): Arrow[TransportMarshallerExecutor. nputs[
    Doma nResponseType
  ], TransportMarshallerExecutor.Result[TransportResponseType]] =
    transportMarshallerExecutor.arrow(conf g.transportMarshaller, context)

}

case class TransportMarshallerConf g[Doma nResponseType <: HasMarshall ng, TransportResponseType](
  transportMarshaller: TransportMarshaller[Doma nResponseType, TransportResponseType],
  doma nMarshallerStep dent f er: P pel neStep dent f er)
