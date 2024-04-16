package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport Cand dateScope.Part  onedCand dates
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls

pr vate[selector] object  nsertSelector {

  /**
   *  nsert all cand dates from a cand date p pel ne at a 0- ndexed f xed pos  on.  f t  current
   * results are a shorter length than t  requested pos  on, t n t  cand dates w ll be appended
   * to t  results.
   */
  def  nsert ntoResultsAtPos  on(
    pos  on:  nt,
    p pel neScope: Cand dateScope,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    assert(pos  on >= 0, "Pos  on must be equal to or greater than zero")

    val Part  onedCand dates(selectedCand dates, ot rCand dates) =
      p pel neScope.part  on(rema n ngCand dates)

    val resultUpdated =  f (selectedCand dates.nonEmpty) {
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
