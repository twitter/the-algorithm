package com.tw ter.product_m xer.core.p pel ne

 mport com.tw ter.product_m xer.component_l brary.model.cand date.CursorCand date
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.Execut onFa led
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try

/**
 * P pel nes return a P pel neResult.
 *
 * T  allows us to return a s ngle ma n result (opt onally,  ncase t  p pel ne d dn't execute successfully), but
 * st ll have a deta led response object to show how that result was produced.
 */
tra  P pel neResult[ResultType] {
  val fa lure: Opt on[P pel neFa lure]
  val result: Opt on[ResultType]

  def w hFa lure(fa lure: P pel neFa lure): P pel neResult[ResultType]
  def w hResult(result: ResultType): P pel neResult[ResultType]

  def resultS ze():  nt

  pr vate[p pel ne] def stopExecut ng: Boolean = fa lure. sDef ned || result. sDef ned

  f nal def toTry: Try[t .type] = (result, fa lure) match {
    case (_, So (fa lure)) =>
      Throw(fa lure)
    case (_: So [ResultType], _) =>
      Return(t )
    // P pel nes should always f n sh w h e  r a result or a fa lure
    case _ => Throw(P pel neFa lure(Execut onFa led, "P pel ne d d not execute"))
  }

  f nal def toResultTry: Try[ResultType] = {
    // `.get`  s safe  re because `toTry` guarantees a value  n t  `Return` case
    toTry.map(_.result.get)
  }
}

object P pel neResult {

  /**
   * Track number of cand dates returned by a P pel ne. Cursors are excluded from t 
   * count and modules are counted as t  sum of t  r cand dates.
   *
   * @note t   s a so what subject ve  asure of 's ze' and    s spread across p pel ne
   *       def n  ons as  ll as selectors.
   */
  def resultS ze(results: Seq[Cand dateW hDeta ls]):  nt = results.map {
    case module: ModuleCand dateW hDeta ls => resultS ze(module.cand dates)
    case  emCand dateW hDeta ls(_: CursorCand date, _, _) => 0
    case _ => 1
  }.sum
}
