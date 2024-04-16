package com.tw ter.follow_recom ndat ons.common.cand date_s ces.ppm _locale_follow

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.ppm _locale_follow.PPM LocaleFollowS ceParams.Cand dateS ceEnabled
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.ppm _locale_follow.PPM LocaleFollowS ceParams.LocaleToExcludeFromRecom ndat on
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch

 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.strato.generated.cl ent.onboard ng.UserPreferredLanguagesOnUserCl entColumn
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.LocaleFollowPpm Cl entColumn
 mport com.tw ter.t  l nes.conf gap .HasParams

/**
 * Fetc s cand dates based on t  Pos  ve Po ntw se Mutual  nformat on (PPM ) stat st c
 * for a set of locales
 * */
@S ngleton
class PPM LocaleFollowS ce @ nject() (
  userPreferredLanguagesOnUserCl entColumn: UserPreferredLanguagesOnUserCl entColumn,
  localeFollowPpm Cl entColumn: LocaleFollowPpm Cl entColumn,
  statsRece ver: StatsRece ver)
    extends Cand dateS ce[HasCl entContext w h HasParams, Cand dateUser] {

  overr de val  dent f er: Cand dateS ce dent f er = PPM LocaleFollowS ce. dent f er
  pr vate val stats = statsRece ver.scope("PPM LocaleFollowS ce")

  overr de def apply(target: HasCl entContext w h HasParams): St ch[Seq[Cand dateUser]] = {
    (for {
      countryCode <- target.getCountryCode
      user d <- target.getOpt onalUser d
    } y eld {
      getPreferredLocales(user d, countryCode.toLo rCase())
        .flatMap { locale =>
          stats.addGauge("allLocale") {
            locale.length
          }
          val f lteredLocale =
            locale.f lter(!target.params(LocaleToExcludeFromRecom ndat on).conta ns(_))
          stats.addGauge("postF lterLocale") {
            f lteredLocale.length
          }
           f (target.params(Cand dateS ceEnabled)) {
            getPPM LocaleFollowCand dates(f lteredLocale)
          } else St ch(Seq.empty)
        }
        .map(_.sortBy(_.score)(Order ng[Opt on[Double]].reverse)
          .take(PPM LocaleFollowS ce.DefaultMaxCand datesToReturn))
    }).getOrElse(St ch.N l)
  }

  pr vate def getPPM LocaleFollowCand dates(
    locales: Seq[Str ng]
  ): St ch[Seq[Cand dateUser]] = {
    St ch
      .traverse(locales) { locale =>
        // Get PPM  cand dates for each locale
        localeFollowPpm Cl entColumn.fetc r
          .fetch(locale)
          .map(_.v
            .map(_.cand dates).getOrElse(N l).map { cand date =>
              Cand dateUser( d = cand date.user d, score = So (cand date.score))
            }.map(_.w hCand dateS ce( dent f er)))
      }.map(_.flatten)
  }

  pr vate def getPreferredLocales(user d: Long, countryCode: Str ng): St ch[Seq[Str ng]] = {
    userPreferredLanguagesOnUserCl entColumn.fetc r
      .fetch(user d)
      .map(_.v.map(_.languages).getOrElse(N l).map { lang =>
        s"$countryCode-$lang".toLo rCase
      })
  }
}

object PPM LocaleFollowS ce {
  val  dent f er = Cand dateS ce dent f er(Algor hm.PPM LocaleFollow.toStr ng)
  val DefaultMaxCand datesToReturn = 100
}
