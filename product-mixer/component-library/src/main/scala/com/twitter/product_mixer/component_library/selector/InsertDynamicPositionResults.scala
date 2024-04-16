package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector._
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object  nsertDynam cPos  onResults {
  def apply[Query <: P pel neQuery](
    cand dateP pel ne: Cand dateP pel ne dent f er,
    dynam c nsert onPos  on: Dynam c nsert onPos  on[Query],
  ):  nsertDynam cPos  onResults[Query] =
    new  nsertDynam cPos  onResults(Spec f cP pel ne(cand dateP pel ne), dynam c nsert onPos  on)

  def apply[Query <: P pel neQuery](
    cand dateP pel nes: Set[Cand dateP pel ne dent f er],
    dynam c nsert onPos  on: Dynam c nsert onPos  on[Query]
  ):  nsertDynam cPos  onResults[Query] =
    new  nsertDynam cPos  onResults(
      Spec f cP pel nes(cand dateP pel nes),
      dynam c nsert onPos  on)
}

/**
 * Compute a pos  on for  nsert ng t  cand dates  nto result.  f a `None`  s returned, t 
 * Selector us ng t  would not  nsert t  cand dates  nto t  result.
 */
tra  Dynam c nsert onPos  on[-Query <: P pel neQuery] {
  def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): Opt on[ nt]
}

/**
 *  nsert all cand dates  n a p pel ne scope at a 0- ndexed dynam c pos  on computed
 * us ng t  prov ded [[Dynam c nsert onPos  on]]  nstance.  f t  current results are a shorter
 * length than t  computed pos  on, t n t  cand dates w ll be appended to t  results.
 *  f t  [[Dynam c nsert onPos  on]] returns a `None`, t  cand dates are not
 * added to t  result.
 */
case class  nsertDynam cPos  onResults[-Query <: P pel neQuery](
  overr de val p pel neScope: Cand dateScope,
  dynam c nsert onPos  on: Dynam c nsert onPos  on[Query])
    extends Selector[Query] {

  overr de def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    dynam c nsert onPos  on(query, rema n ngCand dates, result) match {
      case So (pos  on) =>
         nsertSelector. nsert ntoResultsAtPos  on(
          pos  on = pos  on,
          p pel neScope = p pel neScope,
          rema n ngCand dates = rema n ngCand dates,
          result = result)
      case None =>
        // W n a val d pos  on  s not prov ded, do not  nsert t  cand dates.
        // Both t  rema n ngCand dates and result are unchanged.
        SelectorResult(rema n ngCand dates = rema n ngCand dates, result = result)
    }
  }
}
