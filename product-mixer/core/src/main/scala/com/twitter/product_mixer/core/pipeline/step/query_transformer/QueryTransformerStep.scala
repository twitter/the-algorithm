package com.tw ter.product_m xer.core.p pel ne.step.query_transfor r

 mport com.tw ter.product_m xer.core.model.marshall ng.request.Request
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.state.HasParams
 mport com.tw ter.product_m xer.core.p pel ne.state.HasQuery
 mport com.tw ter.product_m xer.core.p pel ne.state.HasRequest
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.t  l nes.conf gap .Params

/**
 * Query Transformat on Step that takes an  ncom ng thr ft request model object and returns a
 * p pel ne query. T  p pel ne state  s respons ble for keep ng t  updated query.
 *
 * @tparam TRequest Thr ft request doma n model
 * @tparam Query P pel neQuery type to transform to h
 * @tparam State T  request doma n model
 */
case class QueryTransfor rStep[
  TRequest <: Request,
  Query <: P pel neQuery,
  State <: HasQuery[Query, State] w h HasRequest[TRequest] w h HasParams
]() extends Step[State, (TRequest, Params) => Query, (TRequest, Params), QueryTransfor rResult[
      Query
    ]] {

  overr de def  sEmpty(conf g: (TRequest, Params) => Query): Boolean = false

  overr de def arrow(
    conf g: (TRequest, Params) => Query,
    context: Executor.Context
  ): Arrow[(TRequest, Params), QueryTransfor rResult[Query]] = Arrow.map {
    case (request: TRequest @unc cked, params: Params) =>
      QueryTransfor rResult(conf g(request, params))
  }

  overr de def updateState(
    state: State,
    executorResult: QueryTransfor rResult[Query],
    conf g: (TRequest, Params) => Query
  ): State = state.updateQuery(executorResult.query)

  overr de def adapt nput(
    state: State,
    conf g: (TRequest, Params) => Query
  ): (TRequest, Params) = (state.request, state.params)
}

case class QueryTransfor rResult[Query <: P pel neQuery](query: Query) extends ExecutorResult
