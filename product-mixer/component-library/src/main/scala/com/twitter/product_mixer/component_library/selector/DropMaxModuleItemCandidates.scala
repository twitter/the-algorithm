package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * L m  t  number of module  em cand dates (for 1 or more modules) from a certa n cand date
 * s ce.
 *
 * For example,  f maxModule emsParam  s 3, and a cand dateP pel ne returned 1 module conta n ng 10
 *  ems  n t  cand date pool, t n t se module  ems w ll be reduced to t  f rst 3 module  ems.
 * Note that to update t  order ng of t  cand dates, an UpdateModule emsCand dateOrder ngSelector
 * may be used pr or to us ng t  selector.
 *
 * Anot r example,  f maxModule emsParam  s 3, and a cand dateP pel ne returned 5 modules each
 * conta n ng 10  ems  n t  cand date pool, t n t  module  ems  n each of t  5 modules w ll be
 * reduced to t  f rst 3 module  ems.
 *
 * @note t  updates t  module  n t  `rema n ngCand dates`
 */
case class DropMaxModule emCand dates(
  cand dateP pel ne: Cand dateP pel ne dent f er,
  maxModule emsParam: Param[ nt])
    extends Selector[P pel neQuery] {

  overr de val p pel neScope: Cand dateScope = Spec f cP pel nes(cand dateP pel ne)

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {

    val maxModule emSelect ons = query.params(maxModule emsParam)
    assert(maxModule emSelect ons > 0, "Max module  em select ons must be greater than zero")

    val rema n ngCand datesL m ed = rema n ngCand dates.map {
      case module: ModuleCand dateW hDeta ls  f p pel neScope.conta ns(module) =>
        // t  appl es to all cand dates  n a module, even  f t y are from a d fferent
        // cand date s ce wh ch can happen  f  ems are added to a module dur ng select on
        module.copy(cand dates = DropSelector.takeUnt l(maxModule emSelect ons, module.cand dates))
      case cand date => cand date
    }

    SelectorResult(rema n ngCand dates = rema n ngCand datesL m ed, result = result)
  }
}
