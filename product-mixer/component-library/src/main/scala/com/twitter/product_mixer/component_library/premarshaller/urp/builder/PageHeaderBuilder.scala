package com.tw ter.product_m xer.component_l brary.premarshaller.urp.bu lder

 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Page ader
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Tra  for   bu lder wh ch g ven a query and select ons w ll return an `Opt on[Page ader]`
 *
 * @tparam Query
 */
tra  Page aderBu lder[-Query <: P pel neQuery] {

  def bu ld(
    query: Query,
    select ons: Seq[Cand dateW hDeta ls]
  ): Opt on[Page ader]
}
