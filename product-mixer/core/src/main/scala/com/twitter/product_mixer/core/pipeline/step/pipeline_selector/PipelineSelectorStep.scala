package com.tw ter.product_m xer.core.p pel ne.step.p pel ne_selector

 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.state.HasQuery
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * P pel ne Select on step to dec de wh ch p pel ne to execute. T  step doesn't update state, as
 * t  selected p pel ne  dent f er  s added to t  executor results l st map for later retr eval
 *
 * @tparam Query P pel ne query model
 * @tparam State T  p pel ne state doma n model.
 */
case class P pel neSelectorStep[Query <: P pel neQuery, State <: HasQuery[Query, State]] @ nject() (
) extends Step[State, Query => Component dent f er, Query, P pel neSelectorResult] {
  overr de def  sEmpty(conf g: Query => Component dent f er): Boolean = false

  overr de def adapt nput(
    state: State,
    conf g: Query => Component dent f er
  ): Query = state.query

  overr de def arrow(
    conf g: Query => Component dent f er,
    context: Executor.Context
  ): Arrow[Query, P pel neSelectorResult] = Arrow.map { query: Query =>
    P pel neSelectorResult(conf g(query))
  }

  // Noop s nce   keep t   dent f er  n t  executor results
  overr de def updateState(
    state: State,
    executorResult: P pel neSelectorResult,
    conf g: Query => Component dent f er
  ): State = state
}

case class P pel neSelectorResult(p pel ne dent f er: Component dent f er) extends ExecutorResult
