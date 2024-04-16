package com.tw ter.product_m xer.core.serv ce.debug_query

 mport com.tw ter.f nagle.Serv ce
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.ProductD sabled
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neResult
 mport com.tw ter.scrooge.{Request => ScroogeRequest}
 mport com.tw ter.scrooge.{Response => ScroogeResponse}
 mport com.tw ter.ut l.Future
 mport com.tw ter.product_m xer.core.{thr ftscala => t}
 mport com.tw ter.ut l.jackson.ScalaObjectMapper

/**
 * All M xers must  mple nt a debug query  nterface. T  can be a problem for  n-place m grat ons
 * w re a serv ce may only part ally  mple nt Product M xer patterns. T  serv ce can be used as
 * a noop  mple ntat on of [[DebugQueryServ ce]] unt l t  M xer serv ce  s fully m grated.
 */
object DebugQueryNotSupportedServ ce
    extends Serv ce[ScroogeRequest[_], ScroogeResponse[t.P pel neExecut onResult]] {

  val fa lureJson: Str ng = {
    val  ssage = "T  serv ce does not support debug quer es, t   s usually due to an act ve " +
      " n-place m grat on to Product M xer. Please reach out  n #product-m xer  f   have any quest ons."

    ScalaObjectMapper().wr eValueAsStr ng(
      ProductP pel neResult(
        transfor dQuery = None,
        qual yFactorResult = None,
        gateResult = None,
        p pel neSelectorResult = None,
        m xerP pel neResult = None,
        recom ndat onP pel neResult = None,
        trace d = None,
        fa lure = So (P pel neFa lure(ProductD sabled,  ssage)),
        result = None,
      ))
  }

  overr de def apply(
    thr ftRequest: ScroogeRequest[_]
  ): Future[ScroogeResponse[t.P pel neExecut onResult]] =
    Future.value(ScroogeResponse(t.P pel neExecut onResult(fa lureJson)))
}
