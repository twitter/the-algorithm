package com.tw ter.ho _m xer.product.scored_t ets.gate

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.model.Ho Features.LastNonPoll ngT  Feature
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * Gate cont nues  f t  amount of t   passed s nce t  prev ous request  s greater
 * than t  conf gured amount or  f t  prev ous request t    n not ava lable
 */
object M nT  S nceLastRequestGate extends Gate[P pel neQuery] {

  overr de val  dent f er: Gate dent f er = Gate dent f er("T  S nceLastRequest")

  pr vate val M nT  S nceLastRequest = 24.h s

  overr de def shouldCont nue(query: P pel neQuery): St ch[Boolean] = St ch.value {
    query.features.ex sts { features =>
      features
        .getOrElse(LastNonPoll ngT  Feature, None)
        .forall(lnpt => (query.queryT   - lnpt) > M nT  S nceLastRequest)
    }
  }
}
