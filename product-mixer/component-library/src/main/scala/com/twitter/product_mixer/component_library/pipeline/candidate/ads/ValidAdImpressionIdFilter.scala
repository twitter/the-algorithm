package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads

 mport com.tw ter.product_m xer.component_l brary.model.cand date.ads.AdsCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

object Val dAd mpress on dF lter extends F lter[P pel neQuery, AdsCand date] {
  overr de val  dent f er: F lter dent f er = F lter dent f er("Val dAd mpress on d")

  overr de def apply(
    query: P pel neQuery,
    cand datesW hFeatures: Seq[Cand dateW hFeatures[AdsCand date]]
  ): St ch[F lterResult[AdsCand date]] = {
    val (kept, removed) = cand datesW hFeatures
      .map(_.cand date)
      .part  on(cand date => cand date.ad mpress on. mpress onStr ng.ex sts(_.nonEmpty))

    St ch.value(F lterResult(kept, removed))
  }
}
