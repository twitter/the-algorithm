package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.component_l brary.selector.sorter.SorterFromOrder ng
 mport com.tw ter.product_m xer.component_l brary.selector.sorter.SorterProv der
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object UpdateSortModule emCand dates {
  def apply(
    cand dateP pel ne: Cand dateP pel ne dent f er,
    order ng: Order ng[Cand dateW hDeta ls]
  ): UpdateSortModule emCand dates =
    UpdateSortModule emCand dates(
      Spec f cP pel ne(cand dateP pel ne),
      SorterFromOrder ng(order ng))

  def apply(
    cand dateP pel ne: Cand dateP pel ne dent f er,
    sorterProv der: SorterProv der
  ): UpdateSortModule emCand dates =
    UpdateSortModule emCand dates(Spec f cP pel ne(cand dateP pel ne), sorterProv der)

  def apply(
    cand dateP pel nes: Set[Cand dateP pel ne dent f er],
    order ng: Order ng[Cand dateW hDeta ls]
  ): UpdateSortModule emCand dates =
    UpdateSortModule emCand dates(
      Spec f cP pel nes(cand dateP pel nes),
      SorterFromOrder ng(order ng))

  def apply(
    cand dateP pel nes: Set[Cand dateP pel ne dent f er],
    sorterProv der: SorterProv der
  ): UpdateSortModule emCand dates =
    UpdateSortModule emCand dates(Spec f cP pel nes(cand dateP pel nes), sorterProv der)
}

/**
 * Sort  ems  ns de a module from a cand date s ce and update t  rema n ngCand dates.
 *
 * For example,   could spec fy t  follow ng order ng to sort by score descend ng:
 *
 * {{{
 * Order ng
 *   .by[Cand dateW hDeta ls, Double](_.features.get(ScoreFeature) match {
 *     case Scored(score) => score
 *     case _ => Double.M nValue
 *   }).reverse
 *
 * // Before sort ng:
 * ModuleCand dateW hDeta ls(
 *  Seq(
 *     emCand dateW hLowScore,
 *     emCand dateW hM dScore,
 *     emCand dateW hH ghScore),
 *  ... ot r params
 * )
 *
 * // After sort ng:
 * ModuleCand dateW hDeta ls(
 *  Seq(
 *     emCand dateW hH ghScore,
 *     emCand dateW hM dScore,
 *     emCand dateW hLowScore),
 *  ... ot r params
 * )
 * }}}
 *
 * @note t  updates t  modules  n t  `rema n ngCand dates`
 */
case class UpdateSortModule emCand dates(
  overr de val p pel neScope: Cand dateScope,
  sorterProv der: SorterProv der)
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val updatedCand dates = rema n ngCand dates.map {
      case module: ModuleCand dateW hDeta ls  f p pel neScope.conta ns(module) =>
        module.copy(cand dates =
          sorterProv der.sorter(query, rema n ngCand dates, result).sort(module.cand dates))
      case cand date => cand date
    }
    SelectorResult(rema n ngCand dates = updatedCand dates, result = result)
  }
}
