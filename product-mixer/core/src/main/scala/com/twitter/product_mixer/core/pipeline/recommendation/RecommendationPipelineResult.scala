package com.tw ter.product_m xer.core.p pel ne.recom ndat on

 mport com.tw ter.product_m xer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neResult
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutorResults
 mport com.tw ter.product_m xer.core.serv ce.cand date_decorator_executor.Cand dateDecoratorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor.Cand dateFeatureHydratorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.cand date_p pel ne_executor.Cand dateP pel neExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.doma n_marshaller_executor.Doma nMarshallerExecutor
 mport com.tw ter.product_m xer.core.serv ce.f lter_executor.F lterExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.p pel ne_result_s de_effect_executor.P pel neResultS deEffectExecutor
 mport com.tw ter.product_m xer.core.serv ce.qual y_factor_executor.Qual yFactorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor
 mport com.tw ter.product_m xer.core.serv ce.scor ng_p pel ne_executor.Scor ngP pel neExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.selector_executor.SelectorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.transport_marshaller_executor.TransportMarshallerExecutor

case class Recom ndat onP pel neResult[Cand date <: Un versalNoun[Any], ResultType](
  qual yFactorResult: Opt on[Qual yFactorExecutorResult],
  gateResult: Opt on[GateExecutorResult],
  queryFeatures: Opt on[QueryFeatureHydratorExecutor.Result],
  queryFeaturesPhase2: Opt on[QueryFeatureHydratorExecutor.Result],
   rgedAsyncQueryFeatures: Opt on[AsyncFeatureMap],
  cand dateP pel neResults: Opt on[Cand dateP pel neExecutorResult],
  dependentCand dateP pel neResults: Opt on[Cand dateP pel neExecutorResult],
  postCand dateP pel nesSelectorResults: Opt on[SelectorExecutorResult],
  postCand dateP pel nesFeatureHydrat onResults: Opt on[
    Cand dateFeatureHydratorExecutorResult[Cand date]
  ],
  globalF lterResults: Opt on[F lterExecutorResult[Cand date]],
  scor ngP pel neResults: Opt on[Scor ngP pel neExecutorResult[Cand date]],
  resultSelectorResults: Opt on[SelectorExecutorResult],
  postSelect onF lterResults: Opt on[F lterExecutorResult[Cand date]],
  cand dateDecoratorResult: Opt on[Cand dateDecoratorExecutorResult],
  doma nMarshallerResults: Opt on[Doma nMarshallerExecutor.Result[HasMarshall ng]],
  resultS deEffectResults: Opt on[P pel neResultS deEffectExecutor.Result],
  asyncFeatureHydrat onResults: Opt on[AsyncFeatureMapExecutorResults],
  transportMarshallerResults: Opt on[TransportMarshallerExecutor.Result[ResultType]],
  fa lure: Opt on[P pel neFa lure],
  result: Opt on[ResultType])
    extends P pel neResult[ResultType] {
  overr de val resultS ze:  nt = result match {
    case So (seqResult @ Seq(_)) => seqResult.length
    case So (_) => 1
    case None => 0
  }

  overr de def w hFa lure(
    fa lure: P pel neFa lure
  ): Recom ndat onP pel neResult[Cand date, ResultType] =
    copy(fa lure = So (fa lure))
  overr de def w hResult(result: ResultType): Recom ndat onP pel neResult[Cand date, ResultType] =
    copy(result = So (result))
}

object Recom ndat onP pel neResult {
  def empty[A <: Un versalNoun[Any], B]: Recom ndat onP pel neResult[A, B] =
    Recom ndat onP pel neResult(
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
      None,
      None,
      None,
      None,
      None,
      None,
      None
    )
}
