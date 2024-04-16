package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

object  nsertF xedPos  onResults {
  def apply(
    cand dateP pel ne: Cand dateP pel ne dent f er,
    pos  onParam: Param[ nt],
  ):  nsertF xedPos  onResults =
    new  nsertF xedPos  onResults(Spec f cP pel ne(cand dateP pel ne), pos  onParam)

  def apply(
    cand dateP pel nes: Set[Cand dateP pel ne dent f er],
    pos  onParam: Param[ nt]
  ):  nsertF xedPos  onResults =
    new  nsertF xedPos  onResults(Spec f cP pel nes(cand dateP pel nes), pos  onParam)
}

/**
 *  nsert all cand dates  n a p pel ne scope at a 0- ndexed f xed pos  on.  f t  current
 * results are a shorter length than t  requested pos  on, t n t  cand dates w ll be appended
 * to t  results.
 */
case class  nsertF xedPos  onResults(
  overr de val p pel neScope: Cand dateScope,
  pos  onParam: Param[ nt])
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult =  nsertSelector. nsert ntoResultsAtPos  on(
    pos  on = query.params(pos  onParam),
    p pel neScope = p pel neScope,
    rema n ngCand dates = rema n ngCand dates,
    result = result)
}
