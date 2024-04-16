package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.AllP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * L m  t  number of results
 *
 * For example,  f maxResultsParam  s 3, and t  results conta n 10  ems, t n t se  ems w ll be
 * reduced to t  f rst 3 selected  ems. Note that t  order ng of results  s determ ned by t 
 * selector conf gurat on.
 *
 * Anot r example,  f maxResultsParam  s 3, and t  results conta n 10 modules, t n t se w ll be
 * reduced to t  f rst 3 modules. T   ems  ns de t  modules w ll not be affected by t 
 * selector.
 */
case class DropMaxResults(
  maxResultsParam: Param[ nt])
    extends Selector[P pel neQuery] {

  overr de val p pel neScope: Cand dateScope = AllP pel nes

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val maxResults = query.params(maxResultsParam)
    assert(maxResults > 0, "Max results must be greater than zero")

    val resultUpdated = DropSelector.takeUnt l(maxResults, result)

    SelectorResult(rema n ngCand dates = rema n ngCand dates, result = resultUpdated)
  }
}
