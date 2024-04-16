package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.component_l brary.selector.sorter.SorterFromOrder ng
 mport com.tw ter.product_m xer.component_l brary.selector.sorter.SorterProv der
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope.Part  onedCand dates
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector._
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object UpdateSortCand dates {
  def apply(
    cand dateP pel ne: Cand dateP pel ne dent f er,
    sorterProv der: SorterProv der,
  ) = new UpdateSortCand dates(Spec f cP pel ne(cand dateP pel ne), sorterProv der)

  def apply(
    cand dateP pel ne: Cand dateP pel ne dent f er,
    order ng: Order ng[Cand dateW hDeta ls]
  ) =
    new UpdateSortCand dates(Spec f cP pel ne(cand dateP pel ne), SorterFromOrder ng(order ng))

  def apply(
    cand dateP pel nes: Set[Cand dateP pel ne dent f er],
    order ng: Order ng[Cand dateW hDeta ls]
  ) =
    new UpdateSortCand dates(Spec f cP pel nes(cand dateP pel nes), SorterFromOrder ng(order ng))

  def apply(
    cand dateP pel nes: Set[Cand dateP pel ne dent f er],
    sorterProv der: SorterProv der,
  ) = new UpdateSortCand dates(Spec f cP pel nes(cand dateP pel nes), sorterProv der)

  def apply(
    p pel neScope: Cand dateScope,
    order ng: Order ng[Cand dateW hDeta ls]
  ) = new UpdateSortCand dates(p pel neScope, SorterFromOrder ng(order ng))
}

/**
 * Sort  em and module (not  ems  ns de modules) cand dates  n a p pel ne scope.
 * Note that  f sort ng across mult ple cand date s ces, t  cand dates w ll be grouped toget r
 *  n sorted order, start ng from t  pos  on of t  f rst cand date.
 *
 * For example,   could spec fy t  follow ng order ng to sort by score descend ng:
 * Order ng
 *   .by[Cand dateW hDeta ls, Double](_.features.get(ScoreFeature) match {
 *     case Scored(score) => score
 *     case _ => Double.M nValue
 *   }).reverse
 */
case class UpdateSortCand dates(
  overr de val p pel neScope: Cand dateScope,
  sorterProv der: SorterProv der)
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val Part  onedCand dates(selectedCand dates, ot rCand dates) =
      p pel neScope.part  on(rema n ngCand dates)

    val updatedRema n ngCand dates =  f (selectedCand dates.nonEmpty) {
      // Safe . ad due to nonEmpty c ck
      val pos  on = rema n ngCand dates. ndexOf(selectedCand dates. ad)
      val orderedSelectedCand dates =
        sorterProv der.sorter(query, rema n ngCand dates, result).sort(selectedCand dates)

       f (pos  on < ot rCand dates.length) {
        val (left, r ght) = ot rCand dates.spl At(pos  on)
        left ++ orderedSelectedCand dates ++ r ght
      } else {
        ot rCand dates ++ orderedSelectedCand dates
      }
    } else {
      rema n ngCand dates
    }

    SelectorResult(rema n ngCand dates = updatedRema n ngCand dates, result = result)
  }
}
