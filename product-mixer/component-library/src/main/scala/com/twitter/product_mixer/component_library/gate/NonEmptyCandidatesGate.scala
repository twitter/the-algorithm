package com.tw ter.product_m xer.component_l brary.gate

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.gate.QueryAndCand dateGate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * A Gate that only cont nues  f t  prev ously returned cand dates are not empty. T   s useful
 * for gat ng dependent cand date p pel nes that are  ntended to only be used  f a prev ous p pel ne
 * completed successfully.
 */
case class NonEmptyCand datesGate(scope: Cand dateScope)
    extends QueryAndCand dateGate[P pel neQuery] {
  overr de val  dent f er: Gate dent f er = Gate dent f er("NonEmptyCand dates")
  overr de def shouldCont nue(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hDeta ls]
  ): St ch[Boolean] = St ch.value(scope.part  on(cand dates).cand dates nScope.nonEmpty)
}
