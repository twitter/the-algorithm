package com.tw ter.product_m xer.component_l brary.gate

 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

object Def nedCountryCodeGate extends Gate[P pel neQuery] {
  overr de val  dent f er: Gate dent f er = Gate dent f er("Def nedCountryCode")

  overr de def shouldCont nue(query: P pel neQuery): St ch[Boolean] =
    St ch.value(query.getCountryCode. sDef ned)
}
