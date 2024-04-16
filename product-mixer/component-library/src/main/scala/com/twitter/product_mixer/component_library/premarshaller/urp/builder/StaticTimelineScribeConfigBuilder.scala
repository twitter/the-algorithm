package com.tw ter.product_m xer.component_l brary.premarshaller.urp.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.PageBody
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Page ader
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.PageNavBar
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neScr beConf g
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Stat cT  l neScr beConf gBu lder(
  t  l neScr beConf g: T  l neScr beConf g)
    extends T  l neScr beConf gBu lder[P pel neQuery] {

  overr de def bu ld(
    query: P pel neQuery,
    pageBody: PageBody,
    page ader: Opt on[Page ader],
    pageNavBar: Opt on[PageNavBar]
  ): Opt on[T  l neScr beConf g] = So (t  l neScr beConf g)
}
