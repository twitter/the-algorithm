package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.AllP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.P pel neResult
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * Drop all results  f t  m n mum  em threshold  s not  t. So  products would rat r return
 * noth ng than, for example, a s ngle t et. T  lets us leverage ex st ng cl ent log c for
 * handl ng no results such as log c to not render t  product at all.
 */
case class DropTooFewResults(m nResultsParam: Param[ nt]) extends Selector[P pel neQuery] {

  overr de val p pel neScope: Cand dateScope = AllP pel nes

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val m nResults = query.params(m nResultsParam)
    assert(m nResults > 0, "M n results must be greater than zero")

     f (P pel neResult.resultS ze(result) < m nResults) {
      SelectorResult(rema n ngCand dates = rema n ngCand dates, result = Seq.empty)
    } else {
      SelectorResult(rema n ngCand dates = rema n ngCand dates, result = result)
    }
  }
}
