package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * L m  t  number of results (for 1 or more modules) from a certa n cand date
 * s ce to P pel neQuery.requestedMaxResults.
 *
 * P pel neQuery.requestedMaxResults  s opt onally set  n t  p pel neQuery.
 *  f    s not set, t n t  default value of DefaultRequestedMaxModule emsParam  s used.
 *
 * For example,  f P pel neQuery.requestedMaxResults  s 3, and a cand dateP pel ne returned 1 module
 * conta n ng 10  ems  n t  cand date pool, t n t se module  ems w ll be reduced to t  f rst 3
 * module  ems. Note that to update t  order ng of t  cand dates, an
 * UpdateModule emsCand dateOrder ngSelector may be used pr or to us ng t  selector.
 *
 * Anot r example,  f P pel neQuery.requestedMaxResults  s 3, and a cand dateP pel ne returned 5
 * modules each conta n ng 10  ems  n t  cand date pool, t n t  module  ems  n each of t  5
 * modules w ll be reduced to t  f rst 3 module  ems.
 *
 * @note t  updates t  module  n t  `rema n ngCand dates`
 */
case class DropRequestedMaxModule emCand dates(
  overr de val p pel neScope: Cand dateScope,
  defaultRequestedMaxModule emResultsParam: Param[ nt])
    extends Selector[P pel neQuery] {
  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {

    val requestedMaxModule emSelect ons =
      query.maxResults(defaultRequestedMaxModule emResultsParam)
    assert(
      requestedMaxModule emSelect ons > 0,
      "Requested Max module  em select ons must be greater than zero")

    val resultUpdated = result.map {
      case module: ModuleCand dateW hDeta ls  f p pel neScope.conta ns(module) =>
        // t  appl es to all cand dates  n a module, even  f t y are from a d fferent
        // cand date s ce wh ch can happen  f  ems are added to a module dur ng select on
        module.copy(cand dates =
          DropSelector.takeUnt l(requestedMaxModule emSelect ons, module.cand dates))
      case cand date => cand date
    }

    SelectorResult(rema n ngCand dates = rema n ngCand dates, result = resultUpdated)
  }
}

object DropRequestedMaxModule emCand dates {
  def apply(
    cand dateP pel ne: Cand dateP pel ne dent f er,
    defaultRequestedMaxModule emResultsParam: Param[ nt]
  ) =
    new DropRequestedMaxModule emCand dates(
      Spec f cP pel ne(cand dateP pel ne),
      defaultRequestedMaxModule emResultsParam)
}
