package com.tw ter.product_m xer.core.p pel ne.step.selector

 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.state.HasCand datesW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.state.HasQuery
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.selector_executor.SelectorExecutor
 mport com.tw ter.product_m xer.core.serv ce.selector_executor.SelectorExecutorResult
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * A select on step,   takes t   nput l st of cand dates w h deta ls and t  g ven
 * selectors and executes t m to dec de wh ch cand dates should be selected.
 *
 * @param selectorExecutor Selector Executor
 * @tparam Query Type of P pel neQuery doma n model
 * @tparam State T  p pel ne state doma n model.
 */
case class SelectorStep[
  Query <: P pel neQuery,
  State <: HasQuery[Query, State] w h HasCand datesW hDeta ls[State]] @ nject() (
  selectorExecutor: SelectorExecutor)
    extends Step[State, Seq[
      Selector[Query]
    ], SelectorExecutor. nputs[
      Query
    ], SelectorExecutorResult] {

  overr de def adapt nput(
    state: State,
    conf g: Seq[Selector[Query]]
  ): SelectorExecutor. nputs[Query] =
    SelectorExecutor. nputs(state.query, state.cand datesW hDeta ls)

  overr de def arrow(
    conf g: Seq[Selector[Query]],
    context: Executor.Context
  ): Arrow[SelectorExecutor. nputs[Query], SelectorExecutorResult] =
    selectorExecutor.arrow(conf g, context)

  overr de def updateState(
     nput: State,
    executorResult: SelectorExecutorResult,
    conf g: Seq[Selector[Query]]
  ): State =  nput.updateCand datesW hDeta ls(executorResult.selectedCand dates)

  // Select on  s a b  d fferent to ot r steps ( .e, ot r steps, empty  ans don't change anyth ng)
  // w re an empty select on l st drops all cand dates.
  overr de def  sEmpty(conf g: Seq[Selector[Query]]): Boolean = false
}
