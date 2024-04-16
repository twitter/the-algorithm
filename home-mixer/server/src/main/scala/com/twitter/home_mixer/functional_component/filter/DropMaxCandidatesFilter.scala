package com.tw ter.ho _m xer.funct onal_component.f lter

 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam

case class DropMaxCand datesF lter[Cand date <: Un versalNoun[Any]](
  maxCand datesParam: FSBoundedParam[ nt])
    extends F lter[P pel neQuery, Cand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("DropMaxCand dates")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = {
    val maxCand dates = query.params(maxCand datesParam)
    val (kept, removed) = cand dates.map(_.cand date).spl At(maxCand dates)

    St ch.value(F lterResult(kept, removed))
  }
}
