package com.tw ter.product_m xer.core.p pel ne.recom ndat on

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neBu lderFactory
 mport com.tw ter.product_m xer.core.p pel ne.scor ng.Scor ngP pel neBu lderFactory
 mport com.tw ter.product_m xer.core.serv ce.cand date_decorator_executor.Cand dateDecoratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor.Cand dateFeatureHydratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.cand date_p pel ne_executor.Cand dateP pel neExecutor
 mport com.tw ter.product_m xer.core.serv ce.doma n_marshaller_executor.Doma nMarshallerExecutor
 mport com.tw ter.product_m xer.core.serv ce.f lter_executor.F lterExecutor
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutor
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_result_s de_effect_executor.P pel neResultS deEffectExecutor
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutor
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.scor ng_p pel ne_executor.Scor ngP pel neExecutor
 mport com.tw ter.product_m xer.core.serv ce.selector_executor.SelectorExecutor
 mport com.tw ter.product_m xer.core.serv ce.transport_marshaller_executor.TransportMarshallerExecutor

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Recom ndat onP pel neBu lderFactory @ nject() (
  cand dateP pel neExecutor: Cand dateP pel neExecutor,
  gateExecutor: GateExecutor,
  selectorExecutor: SelectorExecutor,
  queryFeatureHydratorExecutor: QueryFeatureHydratorExecutor,
  asyncFeatureMapExecutor: AsyncFeatureMapExecutor,
  cand dateFeatureHydratorExecutor: Cand dateFeatureHydratorExecutor,
  f lterExecutor: F lterExecutor,
  scor ngP pel neExecutor: Scor ngP pel neExecutor,
  cand dateDecoratorExecutor: Cand dateDecoratorExecutor,
  doma nMarshallerExecutor: Doma nMarshallerExecutor,
  transportMarshallerExecutor: TransportMarshallerExecutor,
  p pel neResultS deEffectExecutor: P pel neResultS deEffectExecutor,
  cand dateP pel neBu lderFactory: Cand dateP pel neBu lderFactory,
  scor ngP pel neBu lderFactory: Scor ngP pel neBu lderFactory,
  statsRece ver: StatsRece ver) {

  def get[
    Query <: P pel neQuery,
    Cand date <: Un versalNoun[Any],
    Doma nResultType <: HasMarshall ng,
    Result
  ]: Recom ndat onP pel neBu lder[Query, Cand date, Doma nResultType, Result] = {
    new Recom ndat onP pel neBu lder[Query, Cand date, Doma nResultType, Result](
      cand dateP pel neExecutor,
      gateExecutor,
      selectorExecutor,
      queryFeatureHydratorExecutor,
      asyncFeatureMapExecutor,
      cand dateFeatureHydratorExecutor,
      f lterExecutor,
      scor ngP pel neExecutor,
      cand dateDecoratorExecutor,
      doma nMarshallerExecutor,
      transportMarshallerExecutor,
      p pel neResultS deEffectExecutor,
      cand dateP pel neBu lderFactory,
      scor ngP pel neBu lderFactory,
      statsRece ver
    )
  }
}
