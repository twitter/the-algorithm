package com.tw ter.product_m xer.component_l brary.gate

 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * Gate used  n f rst page. Use request cursor to determ ne  f t  gate should be open or closed.
 */
object F rstPageGate extends Gate[P pel neQuery w h HasP pel neCursor[_]] {

  overr de val  dent f er: Gate dent f er = Gate dent f er("F rstPage")

  //  f cursor  s f rst page, t n gate should return cont nue, ot rw se return stop
  overr de def shouldCont nue(query: P pel neQuery w h HasP pel neCursor[_]): St ch[Boolean] =
    St ch.value(query. sF rstPage)
}
