package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object  nsertPerCand dateDynam cPos  onResults {
  def apply[Query <: P pel neQuery](
    cand dateP pel ne: Cand dateP pel ne dent f er,
    cand datePos  on nResults: Cand datePos  on nResults[Query]
  ):  nsertPerCand dateDynam cPos  onResults[Query] =
     nsertPerCand dateDynam cPos  onResults[Query](
      Spec f cP pel ne(cand dateP pel ne),
      cand datePos  on nResults)

  def apply[Query <: P pel neQuery](
    cand dateP pel nes: Set[Cand dateP pel ne dent f er],
    cand datePos  on nResults: Cand datePos  on nResults[Query]
  ):  nsertPerCand dateDynam cPos  onResults[Query] =
     nsertPerCand dateDynam cPos  onResults[Query](
      Spec f cP pel nes(cand dateP pel nes),
      cand datePos  on nResults)
}

/**
 *  nsert each cand date  n t  [[Cand dateScope]] at t   ndex relat ve to t  or g nal cand date  n t  `result`
 * at that  ndex us ng t  prov ded [[Cand datePos  on nResults]]  nstance.  f t  current results are shorter
 * length than t  computed pos  on, t n t  cand date w ll be appended to t  results.
 *
 * W n t  [[Cand datePos  on nResults]] returns a `None`, that cand date  s not
 * added to t  result. Negat ve pos  on values are treated as 0 (front of t  results).
 *
 * @example  f [[Cand datePos  on nResults]] results  n a cand date mapp ng from  ndex to cand date of
 *          `{0 -> a, 0 -> b, 0 -> c, 1 -> e, 2 -> g, 2 -> h} ` w h  or g nal `results` = `[D, F]`,
 *          t n t  result ng output would look l ke `[a, b, c, D, e, F, g, h]`
 */
case class  nsertPerCand dateDynam cPos  onResults[-Query <: P pel neQuery](
  p pel neScope: Cand dateScope,
  cand datePos  on nResults: Cand datePos  on nResults[Query])
    extends Selector[Query] {

  overr de def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val (cand datesTo nsert, ot rRema n ngCand datesTuples) = rema n ngCand dates
      .map { cand date: Cand dateW hDeta ls =>
        val pos  on =
           f (p pel neScope.conta ns(cand date))
            cand datePos  on nResults(query, cand date, result)
          else
            None
        (pos  on, cand date)
      }.part  on { case ( ndex, _) =>  ndex. sDef ned }

    val ot rRema n ngCand dates = ot rRema n ngCand datesTuples.map {
      case (_, cand date) => cand date
    }

    val pos  onAndCand dateL st = cand datesTo nsert.collect {
      case (So (pos  on), cand date) => (pos  on, cand date)
    }

    val  rgedResult = Dynam cPos  onSelector. rgeBy ndex ntoResult(
      pos  onAndCand dateL st,
      result,
      Dynam cPos  onSelector.Relat ve nd ces
    )

    SelectorResult(rema n ngCand dates = ot rRema n ngCand dates, result =  rgedResult)
  }
}
