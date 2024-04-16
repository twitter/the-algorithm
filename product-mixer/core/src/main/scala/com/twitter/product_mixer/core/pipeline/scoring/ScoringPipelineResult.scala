package com.tw ter.product_m xer.core.p pel ne.scor ng

 mport com.tw ter.product_m xer.core.funct onal_component.scorer.ScoredCand dateResult
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neResult
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.serv ce.cand date_feature_hydrator_executor.Cand dateFeatureHydratorExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.gate_executor.GateExecutorResult
 mport com.tw ter.product_m xer.core.serv ce.selector_executor.SelectorExecutorResult

/**
 * T  Results of every step dur ng t  Scor ngP pel ne process. T  end result conta ns
 * only t  cand dates that  re actually scored (e.g, not dropped by a f lter) w h an updated,
 * comb ned feature map of all features that  re passed  n w h t  cand date plus all features
 * returned as part of scor ng.
 */
case class Scor ngP pel neResult[Cand date <: Un versalNoun[Any]](
  gateResults: Opt on[GateExecutorResult],
  selectorResults: Opt on[SelectorExecutorResult],
  preScor ngHydrat onPhase1Result: Opt on[Cand dateFeatureHydratorExecutorResult[Cand date]],
  preScor ngHydrat onPhase2Result: Opt on[Cand dateFeatureHydratorExecutorResult[Cand date]],
  scorerResults: Opt on[Cand dateFeatureHydratorExecutorResult[
    Cand date
  ]],
  fa lure: Opt on[P pel neFa lure],
  result: Opt on[Seq[ScoredCand dateResult[Cand date]]])
    extends P pel neResult[Seq[ScoredCand dateResult[Cand date]]] {
  overr de val resultS ze:  nt = result.map(_.s ze).getOrElse(0)

  overr de def w hFa lure(
    fa lure: P pel neFa lure
  ): Scor ngP pel neResult[Cand date] =
    copy(fa lure = So (fa lure))
  overr de def w hResult(
    result: Seq[ScoredCand dateResult[Cand date]]
  ): Scor ngP pel neResult[Cand date] =
    copy(result = So (result))
}

object Scor ngP pel neResult {
  def empty[Cand date <: Un versalNoun[Any]]: Scor ngP pel neResult[Cand date] =
    Scor ngP pel neResult(
      None,
      None,
      None,
      None,
      None,
      None,
      None
    )
}
