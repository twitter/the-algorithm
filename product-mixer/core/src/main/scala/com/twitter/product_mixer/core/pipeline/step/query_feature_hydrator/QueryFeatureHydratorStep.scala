package com.tw ter.product_m xer.core.p pel ne.step.query_feature_hydrator

 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseQueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.state.HasAsyncFeatureMap
 mport com.tw ter.product_m xer.core.p pel ne.state.HasQuery
 mport com.tw ter.product_m xer.core.p pel ne.step.Step
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject

/**
 * A query level feature hydrat on step,   takes t   nput l st of cand dates and t  g ven
 * hydrators and executes t m. T  [[State]] object  s respons ble for  rg ng t  result ng
 * feature maps w h t  hydrated ones  n  s updateCand datesW hFeatures.
 *
 * @param queryFeatureHydratorExecutor Hydrator Executor
 * @tparam Query Type of P pel neQuery doma n model
 * @tparam State T  p pel ne state doma n model.
 */
case class QueryFeatureHydratorStep[
  Query <: P pel neQuery,
  State <: HasQuery[Query, State] w h HasAsyncFeatureMap[State]] @ nject() (
  queryFeatureHydratorExecutor: QueryFeatureHydratorExecutor)
    extends Step[State, QueryFeatureHydratorStepConf g[
      Query
    ], Query, QueryFeatureHydratorExecutor.Result] {
  overr de def  sEmpty(conf g: QueryFeatureHydratorStepConf g[Query]): Boolean =
    conf g.hydrators. sEmpty

  overr de def adapt nput(state: State, conf g: QueryFeatureHydratorStepConf g[Query]): Query =
    state.query

  overr de def arrow(
    conf g: QueryFeatureHydratorStepConf g[Query],
    context: Executor.Context
  ): Arrow[Query, QueryFeatureHydratorExecutor.Result] =
    queryFeatureHydratorExecutor.arrow(
      conf g.hydrators,
      conf g.val dP pel neStep dent f ers,
      context)

  overr de def updateState(
    state: State,
    executorResult: QueryFeatureHydratorExecutor.Result,
    conf g: QueryFeatureHydratorStepConf g[Query]
  ): State = {
    val updatedQuery = state.query
      .w hFeatureMap(executorResult.featureMap).as nstanceOf[Query]
    state
      .updateQuery(updatedQuery).addAsyncFeatureMap(executorResult.asyncFeatureMap)
  }
}

case class QueryFeatureHydratorStepConf g[Query <: P pel neQuery](
  hydrators: Seq[BaseQueryFeatureHydrator[Query, _]],
  val dP pel neStep dent f ers: Set[P pel neStep dent f er])
