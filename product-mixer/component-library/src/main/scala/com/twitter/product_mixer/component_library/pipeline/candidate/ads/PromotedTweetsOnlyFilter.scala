package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads

 mport com.tw ter.product_m xer.component_l brary.model.cand date.ads.AdsCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.ads.AdsT etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

case class PromotedT etsOnlyF lter[Query <: P pel neQuery](
  underly ngF lter: F lter[Query, AdsT etCand date])
    extends F lter[Query, AdsCand date] {

  overr de val  dent f er: F lter dent f er =
    F lter dent f er(s"PromotedT ets${underly ngF lter. dent f er.na }")

  overr de def apply(
    query: Query,
    cand datesW hFeatures: Seq[Cand dateW hFeatures[AdsCand date]]
  ): St ch[F lterResult[AdsCand date]] = {

    val adsT etCand dates: Seq[Cand dateW hFeatures[AdsT etCand date]] =
      cand datesW hFeatures.flatMap {
        case t etCand dateW hFeatures @ Cand dateW hFeatures(_: AdsT etCand date, _) =>
          So (t etCand dateW hFeatures.as nstanceOf[Cand dateW hFeatures[AdsT etCand date]])
        case _ => None
      }

    underly ngF lter
      .apply(query, adsT etCand dates)
      .map { f lterResult =>
        val removedSet = f lterResult.removed.toSet[AdsCand date]
        val (removed, kept) = cand datesW hFeatures.map(_.cand date).part  on(removedSet.conta ns)
        F lterResult(kept, removed)
      }
  }
}
