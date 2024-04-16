package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.AllP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope.Part  onedCand dates
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Drops all Cand dates on t  `rema n ngCand dates` s de wh ch are  n t  [[p pel neScope]]
 *
 * T   s typ cally used as a placeholder w n templat ng out a new p pel ne or
 * as a s mple f lter to drop cand dates based only on t  [[Cand dateScope]]
 */
case class DropAllCand dates(overr de val p pel neScope: Cand dateScope = AllP pel nes)
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val Part  onedCand dates( nScope, outOfScope) = p pel neScope.part  on(rema n ngCand dates)

    SelectorResult(rema n ngCand dates = outOfScope, result = result)
  }
}
