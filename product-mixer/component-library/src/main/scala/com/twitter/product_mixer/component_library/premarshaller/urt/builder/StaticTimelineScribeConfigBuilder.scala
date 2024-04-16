package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neScr beConf g
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Stat cT  l neScr beConf gBu lder(
  t  l neScr beConf g: T  l neScr beConf g)
    extends T  l neScr beConf gBu lder[P pel neQuery] {

  def bu ld(
    query: P pel neQuery,
    entr es: Seq[T  l neEntry]
  ): Opt on[T  l neScr beConf g] = So (t  l neScr beConf g)
}
