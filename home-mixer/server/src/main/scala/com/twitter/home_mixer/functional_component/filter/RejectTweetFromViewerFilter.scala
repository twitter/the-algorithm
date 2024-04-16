package com.tw ter.ho _m xer.funct onal_component.f lter

 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

object RejectT etFromV e rF lter extends F lter[P pel neQuery, T etCand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("RejectT etFromV e r")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[F lterResult[T etCand date]] = {
    val (removed, kept) = cand dates.part  on(cand date =>
      Cand datesUt l. sAuthoredByV e r(query, cand date.features))
    St ch.value(F lterResult(kept = kept.map(_.cand date), removed = removed.map(_.cand date)))
  }
}
