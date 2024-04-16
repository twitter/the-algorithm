package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neScr beConf g
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Tra  for   bu lder wh ch g ven a query and entr es w ll return an `Opt on[T  l neScr beConf g]`
 *
 * @tparam Query
 */
tra  T  l neScr beConf gBu lder[-Query <: P pel neQuery] {

  def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Opt on[T  l neScr beConf g]
}
