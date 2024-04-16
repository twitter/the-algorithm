package com.tw ter.ho _m xer.funct onal_component.gate

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nem xer.cl ents.manhattan.D sm ss nfo
 mport com.tw ter.t  l neserv ce.suggests.thr ftscala.SuggestType
 mport com.tw ter.ut l.Durat on

object D sm ssFat gueGate {
  // how long a d sm ss act on from user needs to be respected
  val DefaultBaseD sm ssDurat on = 7.days
  val Max mumD sm ssalCountMult pl er = 4
}

case class D sm ssFat gueGate(
  suggestType: SuggestType,
  d sm ss nfoFeature: Feature[P pel neQuery, Map[SuggestType, Opt on[D sm ss nfo]]],
  baseD sm ssDurat on: Durat on = D sm ssFat gueGate.DefaultBaseD sm ssDurat on,
) extends Gate[P pel neQuery] {

  overr de val  dent f er: Gate dent f er = Gate dent f er("D sm ssFat gue")

  overr de def shouldCont nue(query: P pel neQuery): St ch[Boolean] = {
    val d sm ss nfoMap = query.features.map(
      _.getOrElse(d sm ss nfoFeature, Map.empty[SuggestType, Opt on[D sm ss nfo]]))

    val  sV s ble = d sm ss nfoMap
      .flatMap(_.get(suggestType))
      .flatMap(_.map {  nfo =>
        val currentD sm ssalDurat on = query.queryT  .s nce( nfo.lastD sm ssed)
        val targetD sm ssalDurat on = d sm ssDurat onForCount( nfo.count, baseD sm ssDurat on)

        currentD sm ssalDurat on > targetD sm ssalDurat on
      }).getOrElse(true)
    St ch.value( sV s ble)
  }

  pr vate def d sm ssDurat onForCount(
    d sm ssCount:  nt,
    d sm ssDurat on: Durat on
  ): Durat on =
    // l m  to max mum d sm ssal durat on
    d sm ssDurat on * Math.m n(d sm ssCount, D sm ssFat gueGate.Max mumD sm ssalCountMult pl er)
}
