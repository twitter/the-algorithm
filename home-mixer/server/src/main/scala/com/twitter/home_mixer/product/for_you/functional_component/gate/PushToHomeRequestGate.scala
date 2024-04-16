package com.tw ter.ho _m xer.product.for_ .funct onal_component.gate

 mport com.tw ter.ho _m xer.product.for_ .model.For Query
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.st ch.St ch

/**
 * Cont nues w n t  request  s a Push-To-Ho  not f cat on request
 */
object PushToHo RequestGate extends Gate[For Query] {
  overr de val  dent f er: Gate dent f er = Gate dent f er("PushToHo Request")

  overr de def shouldCont nue(query: For Query): St ch[Boolean] =
    St ch.value(query.pushToHo T et d. sDef ned)
}
