package com.tw ter.product_m xer.core.p pel ne.step.scorer

 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.state.HasCand datesW hFeatures
 mport com.tw ter.product_m xer.core.p pel ne.state.HasQuery
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor.Cand dateFeatureHydratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor.Cand dateFeatureHydratorExecutorResult
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * A scor ng step,   takes t   nput l st of cand dates and t  g ven
 * scorers and executes t m. T  [[State]] object  s respons ble for  rg ng t  result ng
 * feature maps w h t  scored ones  n  s updateCand datesW hFeatures.
 *
 * @param cand dateFeatureHydratorExecutor Hydrator Executor
 * @tparam Query Type of P pel neQuery doma n model
 * @tparam Cand date Type of Cand dates to hydrate features for.
 * @tparam State T  p pel ne state doma n model.
 */
case class ScorerStep[
  Query <: P pel neQuery,
  Cand date <: Un versalNoun[Any],
  State <: HasQuery[Query, State] w h HasCand datesW hFeatures[
    Cand date,
    State
  ]] @ nject() (
  cand dateFeatureHydratorExecutor: Cand dateFeatureHydratorExecutor)
    extends Step[State, Seq[
      Scorer[Query, Cand date]
    ], Cand dateFeatureHydratorExecutor. nputs[
      Query,
      Cand date
    ], Cand dateFeatureHydratorExecutorResult[Cand date]] {

  overr de def adapt nput(
    state: State,
    conf g: Seq[Scorer[Query, Cand date]]
  ): Cand dateFeatureHydratorExecutor. nputs[Query, Cand date] =
    Cand dateFeatureHydratorExecutor. nputs(state.query, state.cand datesW hFeatures)

  overr de def arrow(
    conf g: Seq[Scorer[Query, Cand date]],
    context: Executor.Context
  ): Arrow[
    Cand dateFeatureHydratorExecutor. nputs[Query, Cand date],
    Cand dateFeatureHydratorExecutorResult[Cand date]
  ] = cand dateFeatureHydratorExecutor.arrow(conf g, context)

  overr de def updateState(
     nput: State,
    executorResult: Cand dateFeatureHydratorExecutorResult[Cand date],
    conf g: Seq[Scorer[Query, Cand date]]
  ): State = {
    val resultCand dates = executorResult.results
     f (resultCand dates. sEmpty) {
       nput
    } else {
       nput.updateCand datesW hFeatures(resultCand dates)
    }
  }

  overr de def  sEmpty(conf g: Seq[Scorer[Query, Cand date]]): Boolean =
    conf g. sEmpty
}
