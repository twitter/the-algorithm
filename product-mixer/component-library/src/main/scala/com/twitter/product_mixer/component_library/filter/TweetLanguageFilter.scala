package com.tw ter.product_m xer.component_l brary.f lter

 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

case class T etLanguageF lter[Cand date <: BaseT etCand date](
  languageCodeFeature: Feature[Cand date, Opt on[Str ng]])
    extends F lter[P pel neQuery, Cand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("T etLanguage")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = {

    val userAppLanguage = query.getLanguageCode

    val (keptCand dates, removedCand dates) = cand dates.part  on { f lterCand date =>
      val t etLanguage = f lterCand date.features.get(languageCodeFeature)

      (t etLanguage, userAppLanguage) match {
        case (So (t etLanguageCode), So (userAppLanguageCode)) =>
          t etLanguageCode.equals gnoreCase(userAppLanguageCode)
        case _ => true
      }
    }

    St ch.value(
      F lterResult(
        kept = keptCand dates.map(_.cand date),
        removed = removedCand dates.map(_.cand date)))
  }
}
