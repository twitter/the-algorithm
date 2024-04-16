package com.tw ter.ho _m xer.product.for_ .f lter

 mport com.tw ter.ho _m xer.model.Ho Features.T etTextFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

object T etPrev ewTextF lter extends F lter[P pel neQuery, T etCand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("T etPrev ewText")

  pr vate val Prev ewTextLength = 50
  pr vate val M nT etLength = Prev ewTextLength * 2
  pr vate val MaxNewl nes = 2
  pr vate val HttpPref x = "http://"
  pr vate val HttpsPref x = "https://"

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[F lterResult[T etCand date]] = {

    val (kept, removed) = cand dates
      .part  on { cand date =>
        val text = cand date.features.get(T etTextFeature).getOrElse("")

        text.length > M nT etLength &&
        text.take(Prev ewTextLength).count(_ == '\n') <= MaxNewl nes &&
        !(text.startsW h(HttpPref x) || text.startsW h(HttpsPref x))
      }

    val f lterResult = F lterResult(
      kept = kept.map(_.cand date),
      removed = removed.map(_.cand date)
    )

    St ch.value(f lterResult)
  }

}
