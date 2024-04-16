package com.tw ter.product_m xer.core.p pel ne.m xer

 mport com.tw ter.product_m xer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neResult
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutorResults
 mport com.tw ter.product_m xer.core.serv ce.cand date_p pel ne_executor.Cand dateP pel neExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.doma n_marshaller_executor.Doma nMarshallerExecutor
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_result_s de_effect_executor.P pel neResultS deEffectExecutor
 mport com.tw ter.product_m xer.core.serv ce.qual y_factor_executor.Qual yFactorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.selector_executor.SelectorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.transport_marshaller_executor.TransportMarshallerExecutor

/**
 * A [[M xerP pel neResult]]  ncludes both t  user-v s ble [[P pel neResult]] and all t 
 * Execut on deta ls poss ble -  nter d ate results, what components d d, etc.
 */
case class M xerP pel neResult[Result](
  qual yFactorResult: Opt on[Qual yFactorExecutorResult],
  gateResult: Opt on[GateExecutorResult],
  queryFeatures: Opt on[QueryFeatureHydratorExecutor.Result],
  queryFeaturesPhase2: Opt on[QueryFeatureHydratorExecutor.Result],
   rgedAsyncQueryFeatures: Opt on[AsyncFeatureMap],
  cand dateP pel neResults: Opt on[Cand dateP pel neExecutorResult],
  dependentCand dateP pel neResults: Opt on[Cand dateP pel neExecutorResult],
  resultSelectorResults: Opt on[SelectorExecutorResult],
  doma nMarshallerResults: Opt on[Doma nMarshallerExecutor.Result[HasMarshall ng]],
  resultS deEffectResults: Opt on[P pel neResultS deEffectExecutor.Result],
  asyncFeatureHydrat onResults: Opt on[AsyncFeatureMapExecutorResults],
  transportMarshallerResults: Opt on[TransportMarshallerExecutor.Result[Result]],
  fa lure: Opt on[P pel neFa lure],
  result: Opt on[Result])
    extends P pel neResult[Result] {

  overr de def w hFa lure(fa lure: P pel neFa lure): P pel neResult[Result] =
    copy(fa lure = So (fa lure))

  overr de def w hResult(result: Result): P pel neResult[Result] = copy(result = So (result))

  /**
   * resultS ze  s calculated based on t  selector results rat r than t  marshalled results. T 
   * structure of t  marshalled format  s unknown, mak ng operat ng on selector results more
   * conven ent. T  w ll  mpl c ly excluded cursors bu lt dur ng marshall ng but cursors don't
   * contr bute to t  result s ze anyway.
   */
  overr de val resultS ze:  nt =
    resultSelectorResults.map(_.selectedCand dates).map(P pel neResult.resultS ze).getOrElse(0)
}

object M xerP pel neResult {
  def empty[A]: M xerP pel neResult[A] = M xerP pel neResult(
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None
  )
}
