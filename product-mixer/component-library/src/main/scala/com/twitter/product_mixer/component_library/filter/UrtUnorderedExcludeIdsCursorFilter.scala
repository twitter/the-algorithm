package com.tw ter.product_m xer.component_l brary.f lter

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtUnorderedExclude dsCursor
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

case class UrtUnorderedExclude dsCursorF lter[
  Cand date <: Un versalNoun[Long],
  Query <: P pel neQuery w h HasP pel neCursor[UrtUnorderedExclude dsCursor]
]() extends F lter[Query, Cand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("UnorderedExclude dsCursor")

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = {

    val exclude ds = query.p pel neCursor.map(_.excluded ds.toSet).getOrElse(Set.empty)
    val (kept, removed) =
      cand dates.map(_.cand date).part  on(cand date => !exclude ds.conta ns(cand date. d))

    val f lterResult = F lterResult(kept = kept, removed = removed)
    St ch.value(f lterResult)
  }
}
