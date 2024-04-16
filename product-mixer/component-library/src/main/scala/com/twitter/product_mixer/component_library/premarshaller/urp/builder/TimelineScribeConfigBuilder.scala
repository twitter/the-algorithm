package com.tw ter.product_m xer.component_l brary.premarshaller.urp.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.PageBody
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Page ader
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.PageNavBar
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neScr beConf g
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Tra  for   bu lder wh ch g ven a query and page  nfo w ll return an `Opt on[T  l neScr beConf g]`
 *
 * @tparam Query
 */
tra  T  l neScr beConf gBu lder[-Query <: P pel neQuery] {

  def bu ld(
    query: Query,
    pageBody: PageBody,
    page ader: Opt on[Page ader],
    pageNavBar: Opt on[PageNavBar]
  ): Opt on[T  l neScr beConf g]
}
