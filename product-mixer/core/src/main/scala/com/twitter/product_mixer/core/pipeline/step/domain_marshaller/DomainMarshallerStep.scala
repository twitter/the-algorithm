package com.tw ter.product_m xer.core.p pel ne.step.doma n_marshaller

 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.state.HasCand datesW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.state.HasQuery
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.doma n_marshaller_executor.Doma nMarshallerExecutor
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * A doma n marshaller step,   takes t   nput l st of cand dates and t  g ven
 * doma n marshaller and executes  s to return a marshalled result. T  [[State]] object  s
 * respons ble for keep ng a reference of t  bu lt Response.
 *
 * @param doma nMarshallerExecutor Doma n Marshaller executor.
 * @tparam Query Type of P pel neQuery doma n model
 * @tparam ResponseType t  doma n marshall ng type expected to be returned.
 * @tparam State T  p pel ne state doma n model.
 */
case class Doma nMarshallerStep[
  Query <: P pel neQuery,
  ResponseType <: HasMarshall ng,
  State <: HasQuery[Query, State] w h HasCand datesW hDeta ls[State]] @ nject() (
  doma nMarshallerExecutor: Doma nMarshallerExecutor)
    extends Step[State, Doma nMarshaller[Query, ResponseType], Doma nMarshallerExecutor. nputs[
      Query
    ], Doma nMarshallerExecutor.Result[ResponseType]] {

  overr de def  sEmpty(conf g: Doma nMarshaller[Query, ResponseType]): Boolean = false

  overr de def adapt nput(
    state: State,
    conf g: Doma nMarshaller[Query, ResponseType]
  ): Doma nMarshallerExecutor. nputs[Query] =
    Doma nMarshallerExecutor. nputs(state.query, state.cand datesW hDeta ls)

  overr de def arrow(
    conf g: Doma nMarshaller[Query, ResponseType],
    context: Executor.Context
  ): Arrow[Doma nMarshallerExecutor. nputs[Query], Doma nMarshallerExecutor.Result[ResponseType]] =
    doma nMarshallerExecutor.arrow(conf g, context)

  // Noop s nce t  p pel ne updates t  executor results for us
  overr de def updateState(
    state: State,
    executorResult: Doma nMarshallerExecutor.Result[ResponseType],
    conf g: Doma nMarshaller[Query, ResponseType]
  ): State = state

}
