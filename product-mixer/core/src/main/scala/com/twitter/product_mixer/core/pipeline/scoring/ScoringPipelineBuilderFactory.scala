package com.tw ter.product_m xer.core.p pel ne.scor ng

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor.Cand dateFeatureHydratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutor
 mport com.tw ter.product_m xer.core.serv ce.selector_executor.SelectorExecutor
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Scor ngP pel neBu lderFactory @ nject() (
  gateExecutor: GateExecutor,
  selectorExecutor: SelectorExecutor,
  cand dateFeatureHydratorExecutor: Cand dateFeatureHydratorExecutor,
  statsRece ver: StatsRece ver) {

  def get[
    Query <: P pel neQuery,
    Cand date <: Un versalNoun[Any]
  ]: Scor ngP pel neBu lder[Query, Cand date] = {
    new Scor ngP pel neBu lder[Query, Cand date](
      gateExecutor,
      selectorExecutor,
      cand dateFeatureHydratorExecutor,
      statsRece ver
    )
  }
}
