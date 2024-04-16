package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.AllP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .BoundedParam

/**
 * L m  t  number of results to m n(P pel neQuery.requestedMaxResults, ServerMaxResultsParam)
 *
 * P pel neQuery.requestedMaxResults  s opt onally set  n t  p pel neQuery.
 *  f    s not set, t n t  default value of DefaultRequestedMaxResultsParam  s used.
 *
 * ServerMaxResultsParam spec f es t  max mum number of results supported,  rrespect ve of what  s
 * spec f ed by t  cl ent  n P pel neQuery.requestedMaxResults
 * (or t  DefaultRequestedMaxResultsParam default  f not spec f ed)
 *
 * For example,  f ServerMaxResultsParam  s 5, P pel neQuery.requestedMaxResults  s 3,
 * and t  results conta n 10  ems, t n t se  ems w ll be reduced to t  f rst 3 selected  ems.
 *
 *  f P pel neQuery.requestedMaxResults  s not set, DefaultRequestedMaxResultsParam  s 3,
 * ServerMaxResultsParam  s 5 and t  results conta n 10  ems,
 * t n t se  ems w ll be reduced to t  f rst 3 selected  ems.
 *
 * Anot r example,  f ServerMaxResultsParam  s 5, P pel neQuery.requestedMaxResults  s 8,
 * and t  results conta n 10  ems, t n t se w ll be reduced to t  f rst 5 selected  ems.
 *
 * T   ems  ns de t  modules w ll not be affected by t  selector.
 */
case class DropRequestedMaxResults(
  defaultRequestedMaxResultsParam: BoundedParam[ nt],
  serverMaxResultsParam: BoundedParam[ nt])
    extends Selector[P pel neQuery] {

  overr de val p pel neScope: Cand dateScope = AllP pel nes

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val requestedMaxResults = query.maxResults(defaultRequestedMaxResultsParam)
    val serverMaxResults = query.params(serverMaxResultsParam)
    assert(requestedMaxResults > 0, "Requested max results must be greater than zero")
    assert(serverMaxResults > 0, "Server max results must be greater than zero")

    val appl edMaxResults = Math.m n(requestedMaxResults, serverMaxResults)
    val resultUpdated = DropSelector.takeUnt l(appl edMaxResults, result)

    SelectorResult(rema n ngCand dates = rema n ngCand dates, result = resultUpdated)
  }
}
