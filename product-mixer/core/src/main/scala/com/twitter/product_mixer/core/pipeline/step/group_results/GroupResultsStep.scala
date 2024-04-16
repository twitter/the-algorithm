package com.tw ter.product_m xer.core.p pel ne.step.group_results

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.p pel ne.state.HasCand datesW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.state.HasCand datesW hFeatures
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.group_results_executor.GroupResultsExecutor
 mport com.tw ter.product_m xer.core.serv ce.group_results_executor.GroupResultsExecutor nput
 mport com.tw ter.product_m xer.core.serv ce.group_results_executor.GroupResultsExecutorResult
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * A group results step,   takes t   nput l st of cand dates and decorat ons, and assembles
 * properly decorated cand dates w h deta ls.
 *
 * @param groupResultsExecutor Group results executor
 * @tparam Cand date Type of cand dates
 * @tparam State T  p pel ne state doma n model.
 */
case class GroupResultsStep[
  Cand date <: Un versalNoun[Any],
  State <: HasCand datesW hDeta ls[State] w h HasCand datesW hFeatures[
    Cand date,
    State
  ]] @ nject() (
  groupResultsExecutor: GroupResultsExecutor)
    extends Step[State, Cand dateP pel neContext, GroupResultsExecutor nput[
      Cand date
    ], GroupResultsExecutorResult] {

  overr de def  sEmpty(conf g: Cand dateP pel neContext): Boolean = false
  overr de def adapt nput(
    state: State,
    conf g: Cand dateP pel neContext
  ): GroupResultsExecutor nput[Cand date] = {
    val presentat onMap = state.cand datesW hDeta ls.flatMap { cand dateW hDeta ls =>
      cand dateW hDeta ls.presentat on
        .map { presentat on =>
          cand dateW hDeta ls.getCand date[Un versalNoun[Any]] -> presentat on
        }
    }.toMap
    GroupResultsExecutor nput(state.cand datesW hFeatures, presentat onMap)
  }

  overr de def arrow(
    conf g: Cand dateP pel neContext,
    context: Executor.Context
  ): Arrow[GroupResultsExecutor nput[Cand date], GroupResultsExecutorResult] =
    groupResultsExecutor.arrow(
      conf g.cand dateP pel ne dent f er,
      conf g.cand dateS ce dent f er,
      context)

  overr de def updateState(
    state: State,
    executorResult: GroupResultsExecutorResult,
    conf g: Cand dateP pel neContext
  ): State = state.updateCand datesW hDeta ls(executorResult.cand datesW hDeta ls)
}

case class Cand dateP pel neContext(
  cand dateP pel ne dent f er: Cand dateP pel ne dent f er,
  cand dateS ce dent f er: Cand dateS ce dent f er)
