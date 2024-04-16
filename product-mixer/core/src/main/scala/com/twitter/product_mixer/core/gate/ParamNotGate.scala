package com.tw ter.product_m xer.core.gate

 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

case class ParamNotGate(na : Str ng, param: Param[Boolean]) extends Gate[P pel neQuery] {
  overr de val  dent f er: Gate dent f er = Gate dent f er(na )

  overr de def shouldCont nue(query: P pel neQuery): St ch[Boolean] =
    St ch.value(!query.params(param))
}
