package com.tw ter.product_m xer.core.p pel ne.m xer

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neBu lderFactory
 mport com.tw ter.product_m xer.core.serv ce.cand date_p pel ne_executor.Cand dateP pel neExecutor
 mport com.tw ter.product_m xer.core.serv ce.doma n_marshaller_executor.Doma nMarshallerExecutor
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutor
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_result_s de_effect_executor.P pel neResultS deEffectExecutor
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutor
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.selector_executor.SelectorExecutor
 mport com.tw ter.product_m xer.core.serv ce.transport_marshaller_executor.TransportMarshallerExecutor

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class M xerP pel neBu lderFactory @ nject() (
  cand dateP pel neExecutor: Cand dateP pel neExecutor,
  gateExecutor: GateExecutor,
  selectorExecutor: SelectorExecutor,
  queryFeatureHydratorExecutor: QueryFeatureHydratorExecutor,
  asyncFeatureMapExecutor: AsyncFeatureMapExecutor,
  doma nMarshallerExecutor: Doma nMarshallerExecutor,
  transportMarshallerExecutor: TransportMarshallerExecutor,
  p pel neResultS deEffectExecutor: P pel neResultS deEffectExecutor,
  cand dateP pel neBu lderFactory: Cand dateP pel neBu lderFactory,
  statsRece ver: StatsRece ver) {
  def get[
    Query <: P pel neQuery,
    Doma nResultType <: HasMarshall ng,
    Result
  ]: M xerP pel neBu lder[Query, Doma nResultType, Result] = {
    new M xerP pel neBu lder[Query, Doma nResultType, Result](
      cand dateP pel neExecutor,
      gateExecutor,
      selectorExecutor,
      queryFeatureHydratorExecutor,
      asyncFeatureMapExecutor,
      doma nMarshallerExecutor,
      transportMarshallerExecutor,
      p pel neResultS deEffectExecutor,
      cand dateP pel neBu lderFactory,
      statsRece ver
    )
  }
}
