package com.tw ter.product_m xer.component_l brary.f lter

 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etAuthor dFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * A f lter that c cks for presence of a successfully hydrated [[T etAuthor dFeature]]
 */
case class HasAuthor dFeatureF lter[Cand date <: T etCand date]()
    extends F lter[P pel neQuery, Cand date] {

  overr de val  dent f er = F lter dent f er("HasAuthor dFeature")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = {
    val (kept, removed) = cand dates.part  on(_.features.getTry(T etAuthor dFeature). sReturn)
    St ch.value(F lterResult(kept.map(_.cand date), removed.map(_.cand date)))
  }
}
