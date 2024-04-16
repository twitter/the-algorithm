package com.tw ter.product_m xer.core.p pel ne.step.f lter

 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.state.HasCand datesW hFeatures
 mport com.tw ter.product_m xer.core.p pel ne.state.HasQuery
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.f lter_executor.F lterExecutor
 mport com.tw ter.product_m xer.core.serv ce.f lter_executor.F lterExecutorResult
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * A cand date f lter step,   takes t   nput l st of cand dates and t  g ven f lter and appl es
 * t  f lters on t  cand dates  n sequence, return ng t  f nal kept cand dates l st to State.
 *
 * @param f lterExecutor F lter Executor
 * @tparam Query Type of P pel neQuery doma n model
 * @tparam Cand date Type of Cand dates to f lter
 * @tparam State T  p pel ne state doma n model.
 */
case class F lterStep[
  Query <: P pel neQuery,
  Cand date <: Un versalNoun[Any],
  State <: HasQuery[Query, State] w h HasCand datesW hFeatures[
    Cand date,
    State
  ]] @ nject() (f lterExecutor: F lterExecutor)
    extends Step[State, Seq[
      F lter[Query, Cand date]
    ], (Query, Seq[Cand dateW hFeatures[Cand date]]), F lterExecutorResult[Cand date]] {

  overr de def  sEmpty(conf g: Seq[F lter[Query, Cand date]]): Boolean = conf g. sEmpty

  overr de def adapt nput(
    state: State,
    conf g: Seq[F lter[Query, Cand date]]
  ): (Query, Seq[Cand dateW hFeatures[Cand date]]) =
    (state.query, state.cand datesW hFeatures)

  overr de def arrow(
    conf g: Seq[F lter[Query, Cand date]],
    context: Executor.Context
  ): Arrow[(Query, Seq[Cand dateW hFeatures[Cand date]]), F lterExecutorResult[Cand date]] =
    f lterExecutor.arrow(conf g, context)

  overr de def updateState(
    state: State,
    executorResult: F lterExecutorResult[Cand date],
    conf g: Seq[F lter[Query, Cand date]]
  ): State = {
    val keptCand dates = executorResult.result
    val cand datesMap = state.cand datesW hFeatures.map { cand datesW hFeatures =>
      cand datesW hFeatures.cand date -> cand datesW hFeatures
    }.toMap
    val newCand dates = keptCand dates.flatMap { cand date =>
      cand datesMap.get(cand date)
    }
    state.updateCand datesW hFeatures(newCand dates)
  }
}
