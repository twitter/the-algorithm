package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.component_l brary.selector.sorter.SorterFromOrder ng
 mport com.tw ter.product_m xer.component_l brary.selector.sorter.SorterProv der
 mport com.tw ter.product_m xer.core.funct onal_component.common.AllP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object UpdateSortResults {
  def apply(
    order ng: Order ng[Cand dateW hDeta ls]
  ) =
    new UpdateSortResults((_, _, _) => SorterFromOrder ng(order ng))
}

/**
 * Sort  em and module (not  ems  ns de modules) results.
 *
 * For example,   could spec fy t  follow ng order ng to sort by score descend ng:
 * Order ng
 *   .by[Cand dateW hDeta ls, Double](_.features.get(ScoreFeature) match {
 *     case Scored(score) => score
 *     case _ => Double.M nValue
 *   }).reverse
 */
case class UpdateSortResults(
  sorterProv der: SorterProv der,
  overr de val p pel neScope: Cand dateScope = AllP pel nes)
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val updatedResult = sorterProv der.sorter(query, rema n ngCand dates, result).sort(result)

    SelectorResult(rema n ngCand dates = rema n ngCand dates, result = updatedResult)
  }
}
