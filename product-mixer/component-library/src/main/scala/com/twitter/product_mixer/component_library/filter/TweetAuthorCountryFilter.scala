package com.tw ter.product_m xer.component_l brary.f lter

 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * A [[f lter]] that f lters cand dates based on a country code feature
 *
 * @param countryCodeFeature t  feature to f lter cand dates on
 */
case class T etAuthorCountryF lter[Cand date <: BaseT etCand date](
  countryCodeFeature: Feature[Cand date, Opt on[Str ng]])
    extends F lter[P pel neQuery, Cand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("T etAuthorCountry")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = {

    val userCountry = query.getCountryCode

    val (keptCand dates, removedCand dates) = cand dates.part  on { f lteredCand date =>
      val authorCountry = f lteredCand date.features.get(countryCodeFeature)

      (authorCountry, userCountry) match {
        case (So (authorCountryCode), So (userCountryCode)) =>
          authorCountryCode.equals gnoreCase(userCountryCode)
        case _ => true
      }
    }

    St ch.value(
      F lterResult(
        kept = keptCand dates.map(_.cand date),
        removed = removedCand dates.map(_.cand date)
      )
    )
  }
}
