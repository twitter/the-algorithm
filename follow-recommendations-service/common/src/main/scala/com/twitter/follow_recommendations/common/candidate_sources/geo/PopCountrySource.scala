package com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo

 mport com.google. nject.S ngleton
 mport com.tw ter.core_workflows.user_model.thr ftscala.UserState
 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasGeohashAndCountryCode
 mport com.tw ter.follow_recom ndat ons.common.models.HasUserState
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport javax. nject. nject

@S ngleton
class PopCountryS ce @ nject() (
  popGeoS ce: PopGeoS ce,
  statsRece ver: StatsRece ver)
    extends Cand dateS ce[
      HasCl entContext w h HasParams w h HasUserState w h HasGeohashAndCountryCode,
      Cand dateUser
    ] {

  overr de val  dent f er: Cand dateS ce dent f er = PopCountryS ce. dent f er
  val stats: StatsRece ver = statsRece ver.scope("PopCountryS ce")

  // counter to c ck  f   found a country code value  n t  request
  val foundCountryCodeCounter: Counter = stats.counter("found_country_code_value")
  // counter to c ck  f   are m ss ng a country code value  n t  request
  val m ss ngCountryCodeCounter: Counter = stats.counter("m ss ng_country_code_value")

  overr de def apply(
    target: HasCl entContext w h HasParams w h HasUserState w h HasGeohashAndCountryCode
  ): St ch[Seq[Cand dateUser]] = {
    target.geohashAndCountryCode
      .flatMap(_.countryCode).map { countryCode =>
        foundCountryCodeCounter. ncr()
         f (target.userState.ex sts(PopCountryS ce.Blackl stedTargetUserStates.conta ns)) {
          St ch.N l
        } else {
          popGeoS ce("country_" + countryCode)
            .map(_.take(PopCountryS ce.MaxResults).map(_.w hCand dateS ce( dent f er)))
        }
      }.getOrElse {
        m ss ngCountryCodeCounter. ncr()
        St ch.N l
      }
  }
}

object PopCountryS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.PopCountry.toStr ng)
  val MaxResults = 40
  val Blackl stedTargetUserStates: Set[UserState] = Set(
    UserState. avyT eter,
    UserState. avyNonT eter,
    UserState. d umT eter,
    UserState. d umNonT eter)
}
