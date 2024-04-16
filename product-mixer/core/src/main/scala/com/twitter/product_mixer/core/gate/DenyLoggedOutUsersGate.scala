package com.tw ter.product_m xer.core.gate

 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.gate.DenyLoggedOutUsersGate.Suff x
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.Aut nt cat on
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.st ch.St ch

case class DenyLoggedOutUsersGate(p pel ne dent f er: Component dent f er)
    extends Gate[P pel neQuery] {
  overr de val  dent f er: Gate dent f er = Gate dent f er(p pel ne dent f er + Suff x)

  overr de def shouldCont nue(query: P pel neQuery): St ch[Boolean] = {
     f (query.getUserOrGuest d.nonEmpty) {
      St ch.value(!query. sLoggedOut)
    } else {
      St ch.except on(
        P pel neFa lure(
          Aut nt cat on,
          "Expected e  r a `user d` (for logged  n users) or `guest d` (for logged out users) but found ne  r"
        ))
    }
  }
}

object DenyLoggedOutUsersGate {
  val Suff x = "DenyLoggedOutUsers"
}
