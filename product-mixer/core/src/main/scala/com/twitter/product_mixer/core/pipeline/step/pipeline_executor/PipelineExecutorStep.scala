package com.tw ter.product_m xer.core.p pel ne.step.p pel ne_executor

 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel ne
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. llegalStateFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.state.HasExecutorResults
 mport com.tw ter.product_m xer.core.p pel ne.state.HasQuery
 mport com.tw ter.product_m xer.core.p pel ne.state.HasResult
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.p pel ne.step.p pel ne_selector.P pel neSelectorResult
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorObserver
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_executor.P pel neExecutor
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_executor.P pel neExecutorRequest
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_executor.P pel neExecutorResult
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * P pel ne Execut on step that takes a selected p pel ne and executes  .
 *
 * @param p pel neExecutor P pel ne executor that executes t  selected p pel ne
 *
 * @tparam Query P pel ne query model w h qual y factor status
 * @tparam Result T  expected result type
 * @tparam State T  p pel ne state doma n model.
 */
case class P pel neExecutorStep[
  Query <: P pel neQuery,
  Result,
  State <: HasQuery[Query, State] w h HasExecutorResults[State] w h HasResult[Result]] @ nject() (
  p pel neExecutor: P pel neExecutor)
    extends Step[
      State,
      P pel neExecutorStepConf g[Query, Result],
      P pel neExecutorRequest[Query],
      P pel neExecutorResult[Result]
    ] {

  overr de def  sEmpty(conf g: P pel neExecutorStepConf g[Query, Result]): Boolean =
    false

  overr de def adapt nput(
    state: State,
    conf g: P pel neExecutorStepConf g[Query, Result]
  ): P pel neExecutorRequest[Query] = {
    val p pel neSelectorResult = state.executorResultsByP pel neStep
      .getOrElse(
        conf g.selectedP pel neResult dent f er,
        throw P pel neFa lure(
           llegalStateFa lure,
          "M ss ng Selected P pel ne  n P pel ne Executor Step")).as nstanceOf[
        P pel neSelectorResult]
    P pel neExecutorRequest(state.query, p pel neSelectorResult.p pel ne dent f er)
  }

  overr de def arrow(
    conf g: P pel neExecutorStepConf g[Query, Result],
    context: Executor.Context
  ): Arrow[P pel neExecutorRequest[Query], P pel neExecutorResult[Result]] = p pel neExecutor.arrow(
    conf g.p pel nesBy dent f er,
    conf g.qual yFactorObserversBy dent f er,
    context
  )

  // Noop s nce t  platform w ll add t  f nal result to t  executor result map t n state
  //  s respons ble for read ng    n [[W hResult]]
  overr de def updateState(
    state: State,
    executorResult: P pel neExecutorResult[Result],
    conf g: P pel neExecutorStepConf g[Query, Result]
  ): State = state
}

case class P pel neExecutorStepConf g[Query <: P pel neQuery, Result](
  p pel nesBy dent f er: Map[Component dent f er, P pel ne[Query, Result]],
  selectedP pel neResult dent f er: P pel neStep dent f er,
  qual yFactorObserversBy dent f er: Map[Component dent f er, Qual yFactorObserver])
