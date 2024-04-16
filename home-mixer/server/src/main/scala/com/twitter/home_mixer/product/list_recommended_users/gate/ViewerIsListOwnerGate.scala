package com.tw ter.ho _m xer.product.l st_recom nded_users.gate

 mport com.tw ter.ho _m xer.model.request.HasL st d
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.soc algraph.{thr ftscala => sg}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.soc algraph.Soc alGraph

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class V e r sL stOwnerGate @ nject() (soc alGraph: Soc alGraph)
    extends Gate[P pel neQuery w h HasL st d] {

  overr de val  dent f er: Gate dent f er = Gate dent f er("V e r sL stOwner")

  pr vate val relat onsh p = sg.Relat onsh p(relat onsh pType = sg.Relat onsh pType.L stOwn ng)

  overr de def shouldCont nue(query: P pel neQuery w h HasL st d): St ch[Boolean] = {
    val request = sg.Ex stsRequest(
      s ce = query.getRequ redUser d,
      target = query.l st d,
      relat onsh ps = Seq(relat onsh p))
    soc alGraph.ex sts(request).map(_.ex sts)
  }
}
