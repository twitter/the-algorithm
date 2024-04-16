package com.tw ter.product_m xer.core.p pel ne.cand date

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.asyncfeaturemap.AsyncFeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neResult
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.serv ce.async_feature_map_executor.AsyncFeatureMapExecutorResults
 mport com.tw ter.product_m xer.core.serv ce.cand date_decorator_executor.Cand dateDecoratorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor.Cand dateFeatureHydratorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.cand date_s ce_executor.Cand dateS ceExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.f lter_executor.F lterExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor

case class Cand dateP pel neResult(
  cand dateS ce dent f er: Cand dateS ce dent f er,
  gateResult: Opt on[GateExecutorResult],
  queryFeatures: Opt on[QueryFeatureHydratorExecutor.Result],
  queryFeaturesPhase2: Opt on[QueryFeatureHydratorExecutor.Result],
   rgedAsyncQueryFeatures: Opt on[AsyncFeatureMap],
  cand dateS ceResult: Opt on[Cand dateS ceExecutorResult[Un versalNoun[Any]]],
  preF lterHydrat onResult: Opt on[Cand dateFeatureHydratorExecutorResult[Un versalNoun[Any]]],
  preF lterHydrat onResultPhase2: Opt on[
    Cand dateFeatureHydratorExecutorResult[Un versalNoun[Any]]
  ],
  f lterResult: Opt on[F lterExecutorResult[Un versalNoun[Any]]],
  postF lterHydrat onResult: Opt on[Cand dateFeatureHydratorExecutorResult[Un versalNoun[Any]]],
  cand dateDecoratorResult: Opt on[Cand dateDecoratorExecutorResult],
  scorersResult: Opt on[Cand dateFeatureHydratorExecutorResult[Un versalNoun[Any]]],
  asyncFeatureHydrat onResults: Opt on[AsyncFeatureMapExecutorResults],
  fa lure: Opt on[P pel neFa lure],
  result: Opt on[Seq[Cand dateW hDeta ls]])
    extends P pel neResult[Seq[Cand dateW hDeta ls]] {

  overr de def w hFa lure(fa lure: P pel neFa lure): Cand dateP pel neResult =
    copy(fa lure = So (fa lure))

  overr de def w hResult(
    result: Seq[Cand dateW hDeta ls]
  ): Cand dateP pel neResult = copy(result = So (result))

  overr de val resultS ze:  nt = result.map(P pel neResult.resultS ze).getOrElse(0)
}

pr vate[cand date] object  nter d ateCand dateP pel neResult {
  def empty[Cand date <: Un versalNoun[Any]](
    cand dateS ce dent f er: Cand dateS ce dent f er
  ):  nter d ateCand dateP pel neResult[Cand date] = {
     nter d ateCand dateP pel neResult(
      Cand dateP pel neResult(
        cand dateS ce dent f er = cand dateS ce dent f er,
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
      ),
      None
    )
  }
}

pr vate[cand date] case class  nter d ateCand dateP pel neResult[Cand date <: Un versalNoun[Any]](
  underly ngResult: Cand dateP pel neResult,
  featureMaps: Opt on[Map[Cand date, FeatureMap]])
    extends P pel neResult[Seq[Cand dateW hDeta ls]] {
  overr de val fa lure: Opt on[P pel neFa lure] = underly ngResult.fa lure
  overr de val result: Opt on[Seq[Cand dateW hDeta ls]] = underly ngResult.result

  overr de def w hFa lure(
    fa lure: P pel neFa lure
  ):  nter d ateCand dateP pel neResult[Cand date] =
    copy(underly ngResult = underly ngResult.w hFa lure(fa lure))

  overr de def w hResult(
    result: Seq[Cand dateW hDeta ls]
  ):  nter d ateCand dateP pel neResult[Cand date] =
    copy(underly ngResult = underly ngResult.w hResult(result))

  overr de def resultS ze():  nt = underly ngResult.resultS ze
}
