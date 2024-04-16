package com.tw ter.product_m xer.component_l brary.gate

 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.st ch.St ch

/**
 * A Gate that only cont nues  f t  qual y factor value of t  p pel ne  s above t  g ven
 * threshold. T   s useful for d sabl ng an expens ve funct on w n t  p pel ne  s under pressure
 * (qual y factor  s low).
 */
case class Qual yFactorGate(p pel ne dent f er: Component dent f er, threshold: Double)
    extends Gate[P pel neQuery w h HasQual yFactorStatus] {

  overr de val  dent f er: Gate dent f er = Gate dent f er(
    s"${p pel ne dent f er.na }Qual yFactor")

  overr de def shouldCont nue(
    query: P pel neQuery w h HasQual yFactorStatus
  ): St ch[Boolean] =
    St ch.value(query.getQual yFactorCurrentValue(p pel ne dent f er) >= threshold)
}
