package com.tw ter.product_m xer.core.p pel ne.cand date

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_decorator_executor.Cand dateDecoratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor.Cand dateFeatureHydratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_s ce_executor.Cand dateS ceExecutor
 mport com.tw ter.product_m xer.core.serv ce.f lter_executor.F lterExecutor
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutor
 mport com.tw ter.product_m xer.core.serv ce.group_results_executor.GroupResultsExecutor
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cand dateP pel neBu lderFactory @ nject() (
  queryFeatureHydratorExecutor: QueryFeatureHydratorExecutor,
  asyncFeatureMapExecutor: AsyncFeatureMapExecutor,
  cand dateDecoratorExecutor: Cand dateDecoratorExecutor,
  cand dateFeatureHydratorExecutor: Cand dateFeatureHydratorExecutor,
  cand dateS ceExecutor: Cand dateS ceExecutor,
  groupResultsExecutor: GroupResultsExecutor,
  f lterExecutor: F lterExecutor,
  gateExecutor: GateExecutor,
  statsRece ver: StatsRece ver) {
  def get[
    Query <: P pel neQuery,
    Cand dateS ceQuery,
    Cand dateS ceResult,
    Result <: Un versalNoun[Any]
  ]: Cand dateP pel neBu lder[
    Query,
    Cand dateS ceQuery,
    Cand dateS ceResult,
    Result
  ] = {
    new Cand dateP pel neBu lder[
      Query,
      Cand dateS ceQuery,
      Cand dateS ceResult,
      Result
    ](
      queryFeatureHydratorExecutor,
      asyncFeatureMapExecutor,
      cand dateDecoratorExecutor,
      cand dateFeatureHydratorExecutor,
      cand dateS ceExecutor,
      groupResultsExecutor,
      f lterExecutor,
      gateExecutor,
      statsRece ver
    )
  }
}
