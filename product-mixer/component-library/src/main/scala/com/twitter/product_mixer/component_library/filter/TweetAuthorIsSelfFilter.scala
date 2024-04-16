package com.tw ter.product_m xer.component_l brary.f lter

 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etAuthor dFeature
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * A [[f lter]] that f lters based on w t r query user  s t  author of t  t et. T  w ll NOT f lter empty user  ds
 * @note    s recom nded to apply [[HasAuthor dFeatureF lter]] before t , as t  w ll FA L  f feature  s unava lable
 *
 * @tparam Cand date T  type of t  cand dates
 */
case class T etAuthor sSelfF lter[Cand date <: BaseT etCand date]()
    extends F lter[P pel neQuery, Cand date] {
  overr de val  dent f er: F lter dent f er = F lter dent f er("T etAuthor sSelf")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = {
    val (kept, removed) = cand dates.part  on { cand date =>
      val author d = cand date.features.get(T etAuthor dFeature)
      !query.getOpt onalUser d.conta ns(author d)
    }

    val f lterResult = F lterResult(
      kept = kept.map(_.cand date),
      removed = removed.map(_.cand date)
    )
    St ch.value(f lterResult)
  }
}
