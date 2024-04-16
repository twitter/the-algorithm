package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object DropF lteredModule emCand dates {
  def apply(cand dateP pel ne: Cand dateP pel ne dent f er, f lter: ShouldKeepCand date) =
    new DropF lteredModule emCand dates(Spec f cP pel ne(cand dateP pel ne), f lter)

  def apply(cand dateP pel nes: Set[Cand dateP pel ne dent f er], f lter: ShouldKeepCand date) =
    new DropF lteredModule emCand dates(Spec f cP pel nes(cand dateP pel nes), f lter)
}

/**
 * L m  cand dates  n modules from certa n cand dates s ces to those wh ch sat sfy
 * t  prov ded pred cate.
 *
 * T  acts l ke a [[DropF lteredCand dates]] but for modules  n `rema n ngCand dates`
 * from any of t  prov ded [[cand dateP pel nes]].
 *
 * @note t  updates t  module  n t  `rema n ngCand dates`
 */
case class DropF lteredModule emCand dates(
  overr de val p pel neScope: Cand dateScope,
  f lter: ShouldKeepCand date)
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val cand datesUpdated = rema n ngCand dates.map {
      case module: ModuleCand dateW hDeta ls  f p pel neScope.conta ns(module) =>
        // t  appl es to all cand dates  n a module, even  f t y are from a d fferent
        // cand date s ce, wh ch can happen  f  ems are added to a module dur ng select on
        module.copy(cand dates = module.cand dates.f lter(f lter.apply))
      case cand date => cand date
    }

    SelectorResult(rema n ngCand dates = cand datesUpdated, result = result)
  }
}
