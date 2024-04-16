package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport Cand dateScope.Part  onedCand dates
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

/**
 *  nsert all cand dates from a cand date p pel ne at a pos  on below, relat ve to t  last
 * select on of t  relat ve to cand date p pel ne.  f t  relat ve to cand date p pel ne does not
 * conta n cand dates, t n t  cand dates w ll be  nserted w h padd ng relat ve to pos  on zero.
 *  f t  current results are a shorter length than t  requested padd ng, t n t  cand dates w ll
 * be appended to t  results.
 */
case class  nsertRelat vePos  onResults(
  cand dateP pel ne: Cand dateP pel ne dent f er,
  relat veToCand dateP pel ne: Cand dateP pel ne dent f er,
  padd ngAboveParam: Param[ nt])
    extends Selector[P pel neQuery] {

  overr de val p pel neScope: Cand dateScope = Spec f cP pel nes(cand dateP pel ne)

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {

    val padd ngAbove = query.params(padd ngAboveParam)
    assert(padd ngAbove >= 0, "Padd ng above must be equal to or greater than zero")

    val Part  onedCand dates(selectedCand dates, ot rCand dates) =
      p pel neScope.part  on(rema n ngCand dates)

    val resultUpdated =  f (selectedCand dates.nonEmpty) {
      //  f t  relat veToCand dateP pel ne has zero cand dates, last ndexW re w ll return -1 wh ch
      // w ll start padd ng from t  zero pos  on
      val relat vePos  on = result.last ndexW re(_.s ce == relat veToCand dateP pel ne) + 1
      val pos  on = relat vePos  on + padd ngAbove

       f (pos  on < result.length) {
        val (left, r ght) = result.spl At(pos  on)
        left ++ selectedCand dates ++ r ght
      } else {
        result ++ selectedCand dates
      }
    } else {
      result
    }

    SelectorResult(rema n ngCand dates = ot rCand dates, result = resultUpdated)
  }
}
