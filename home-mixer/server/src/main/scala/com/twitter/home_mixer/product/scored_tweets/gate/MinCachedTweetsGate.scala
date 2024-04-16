package com.tw ter.ho _m xer.product.scored_t ets.gate

 mport com.tw ter.ho _m xer.product.scored_t ets.gate.M nCac dT etsGate. dent f erSuff x
 mport com.tw ter.ho _m xer.ut l.Cac dScoredT ets lper
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

case class M nCac dT etsGate(
  cand dateP pel ne dent f er: Cand dateP pel ne dent f er,
  m nCac dT etsParam: Param[ nt])
    extends Gate[P pel neQuery] {

  overr de val  dent f er: Gate dent f er =
    Gate dent f er(cand dateP pel ne dent f er +  dent f erSuff x)

  overr de def shouldCont nue(query: P pel neQuery): St ch[Boolean] = {
    val m nCac dT ets = query.params(m nCac dT etsParam)
    val cac dScoredT ets =
      query.features.map(Cac dScoredT ets lper.unseenCac dScoredT ets).getOrElse(Seq.empty)
    val numCac dT ets = cac dScoredT ets.count { t et =>
      t et.cand dateP pel ne dent f er.ex sts(
        Cand dateP pel ne dent f er(_).equals(cand dateP pel ne dent f er))
    }
    St ch.value(numCac dT ets < m nCac dT ets)
  }
}

object M nCac dT etsGate {
  val  dent f erSuff x = "M nCac dT ets"
}
