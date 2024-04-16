package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Pred cate wh ch w ll be appl ed to each cand date. True  nd cates that t  cand date w ll be
 * kept.
 */
tra  ShouldKeepCand date {
  def apply(cand dateW hDeta ls: Cand dateW hDeta ls): Boolean
}

object DropF lteredCand dates {
  def apply(cand dateP pel ne: Cand dateP pel ne dent f er, f lter: ShouldKeepCand date) =
    new DropF lteredCand dates(Spec f cP pel ne(cand dateP pel ne), f lter)

  def apply(cand dateP pel nes: Set[Cand dateP pel ne dent f er], f lter: ShouldKeepCand date) =
    new DropF lteredCand dates(Spec f cP pel nes(cand dateP pel nes), f lter)
}

/**
 * L m  cand dates from certa n cand dates s ces to those wh ch sat sfy t  prov ded pred cate.
 */
case class DropF lteredCand dates(
  overr de val p pel neScope: Cand dateScope,
  f lter: ShouldKeepCand date)
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val cand datesUpdated = rema n ngCand dates.f lter { cand date =>
       f (p pel neScope.conta ns(cand date)) f lter.apply(cand date)
      else true
    }

    SelectorResult(rema n ngCand dates = cand datesUpdated, result = result)
  }
}
