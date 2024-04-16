package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport Cand dateScope.Part  onedCand dates
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object  nsertAppendResults {
  def apply(cand dateP pel ne: Cand dateP pel ne dent f er):  nsertAppendResults[P pel neQuery] =
    new  nsertAppendResults(Spec f cP pel ne(cand dateP pel ne))

  def apply(
    cand dateP pel nes: Set[Cand dateP pel ne dent f er]
  ):  nsertAppendResults[P pel neQuery] = new  nsertAppendResults(
    Spec f cP pel nes(cand dateP pel nes))
}

/**
 * Select all cand dates from cand date p pel ne(s) and append to t  end of t  result.
 *
 * @note that  f mult ple cand date p pel nes are spec f ed, t  r cand dates w ll be added
 *       to t  result  n t  order  n wh ch t y appear  n t  cand date pool. T  order ng often
 *       reflects t  order  n wh ch t  cand date p pel nes  re l sted  n t  m xer/recom ndat ons
 *       p pel ne, unless for example an UpdateSortCand dates selector was run pr or to runn ng
 *       t  selector wh ch could change t  order ng.
 *
 * @note  f  nsert ng results from mult ple cand date p pel nes (see note above related to order ng),
 *          s more performant to  nclude all (or a subset) of t  cand date p pel nes  n a s ngle
 *        nsertAppendResults, as opposed to call ng  nsertAppendResults  nd v dually for each
 *       cand date p pel ne because each selector does an O(n) pass on t  cand date pool.
 */
case class  nsertAppendResults[-Query <: P pel neQuery](
  overr de val p pel neScope: Cand dateScope)
    extends Selector[Query] {

  overr de def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val Part  onedCand dates(selectedCand dates, ot rCand dates) =
      p pel neScope.part  on(rema n ngCand dates)

    val resultUpdated = result ++ selectedCand dates

    SelectorResult(rema n ngCand dates = ot rCand dates, result = resultUpdated)
  }
}
