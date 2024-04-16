package com.tw ter.product_m xer.core.p pel ne.step.decorator

 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.state.HasCand datesW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.state.HasCand datesW hFeatures
 mport com.tw ter.product_m xer.core.p pel ne.state.HasQuery
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.cand date_decorator_executor.Cand dateDecoratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_decorator_executor.Cand dateDecoratorExecutorResult
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * A cand date decorat on step, wh ch takes t  query and cand dates and outputs decorat ons for t m
 *
 * @param cand dateDecoratorExecutor Cand date S ce Executor
 * @tparam Query Type of P pel neQuery doma n model
 * @tparam Cand date Type of Cand dates to f lter
 * @tparam State T  p pel ne state doma n model.
 */
case class DecoratorStep[
  Query <: P pel neQuery,
  Cand date <: Un versalNoun[Any],
  State <: HasQuery[Query, State] w h HasCand datesW hDeta ls[
    State
  ] w h HasCand datesW hFeatures[
    Cand date,
    State
  ]] @ nject() (cand dateDecoratorExecutor: Cand dateDecoratorExecutor)
    extends Step[
      State,
      Opt on[Cand dateDecorator[Query, Cand date]],
      (Query, Seq[Cand dateW hFeatures[Cand date]]),
      Cand dateDecoratorExecutorResult
    ] {

  overr de def  sEmpty(conf g: Opt on[Cand dateDecorator[Query, Cand date]]): Boolean =
    conf g. sEmpty

  overr de def adapt nput(
    state: State,
    conf g: Opt on[Cand dateDecorator[Query, Cand date]]
  ): (Query, Seq[Cand dateW hFeatures[Cand date]]) =
    (state.query, state.cand datesW hFeatures)

  overr de def arrow(
    conf g: Opt on[Cand dateDecorator[Query, Cand date]],
    context: Executor.Context
  ): Arrow[(Query, Seq[Cand dateW hFeatures[Cand date]]), Cand dateDecoratorExecutorResult] =
    cand dateDecoratorExecutor.arrow(conf g, context)

  overr de def updateState(
    state: State,
    executorResult: Cand dateDecoratorExecutorResult,
    conf g: Opt on[Cand dateDecorator[Query, Cand date]]
  ): State = {
    state.updateDecorat ons(executorResult.result)
  }
}
