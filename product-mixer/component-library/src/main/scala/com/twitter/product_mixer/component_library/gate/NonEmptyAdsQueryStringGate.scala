package com.tw ter.product_m xer.component_l brary.gate

 mport com.tw ter.product_m xer.component_l brary.model.query.ads.AdsQuery
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

object NonEmptyAdsQueryStr ngGate extends Gate[P pel neQuery w h AdsQuery] {
  overr de val  dent f er: Gate dent f er = Gate dent f er("NonEmptyAdsQueryStr ng")

  overr de def shouldCont nue(query: P pel neQuery w h AdsQuery): St ch[Boolean] = {
    val queryStr ng = query.searchRequestContext.flatMap(_.queryStr ng)
    St ch.value(queryStr ng.ex sts(_.tr m.nonEmpty))
  }
}
