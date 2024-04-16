package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.Esc rb rdEnt yAnnotat ons
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.M s nformat onPol cyS ce
 mport com.tw ter.v s b l y.features._
 mport com.tw ter.v s b l y.models.M s nformat onPol cy
 mport com.tw ter.v s b l y.models.Semant cCoreM s nformat on
 mport com.tw ter.v s b l y.models.V e rContext

class M s nformat onPol cyFeatures(
  m s nformat onPol cyS ce: M s nformat onPol cyS ce,
  statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver =
    statsRece ver.scope("m s nformat on_pol cy_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")
  pr vate[t ] val t etM s nformat onPol c es =
    scopedStatsRece ver.scope(T etM s nformat onPol c es.na ).counter("requests")

  def forT et(
    t et: T et,
    v e rContext: V e rContext
  ): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()
    t etM s nformat onPol c es. ncr()

    _.w hFeature(
      T etM s nformat onPol c es,
      m s nformat onPol cy(t et.esc rb rdEnt yAnnotat ons, v e rContext))
      .w hFeature(
        T etEngl shM s nformat onPol c es,
        m s nformat onPol cyEngl shOnly(t et.esc rb rdEnt yAnnotat ons))
  }

  def m s nformat onPol cyEngl shOnly(
    esc rb rdEnt yAnnotat ons: Opt on[Esc rb rdEnt yAnnotat ons],
  ): St ch[Seq[M s nformat onPol cy]] = {
    val locale = So (
      M s nformat onPol cyS ce.LanguageAndCountry(
        language = So ("en"),
        country = So ("us")
      ))
    fetchM s nformat onPol cy(esc rb rdEnt yAnnotat ons, locale)
  }

  def m s nformat onPol cy(
    esc rb rdEnt yAnnotat ons: Opt on[Esc rb rdEnt yAnnotat ons],
    v e rContext: V e rContext
  ): St ch[Seq[M s nformat onPol cy]] = {
    val locale = v e rContext.requestLanguageCode.map { language =>
      M s nformat onPol cyS ce.LanguageAndCountry(
        language = So (language),
        country = v e rContext.requestCountryCode
      )
    }
    fetchM s nformat onPol cy(esc rb rdEnt yAnnotat ons, locale)
  }

  def fetchM s nformat onPol cy(
    esc rb rdEnt yAnnotat ons: Opt on[Esc rb rdEnt yAnnotat ons],
    locale: Opt on[M s nformat onPol cyS ce.LanguageAndCountry]
  ): St ch[Seq[M s nformat onPol cy]] = {
    St ch.collect(
      esc rb rdEnt yAnnotat ons
        .map(_.ent yAnnotat ons)
        .getOrElse(Seq.empty)
        .f lter(_.doma n d == Semant cCoreM s nformat on.doma n d)
        .map(annotat on =>
          m s nformat onPol cyS ce
            .fetch(
              annotat on,
              locale
            )
            .map(m s nformat on =>
              M s nformat onPol cy(
                annotat on = annotat on,
                m s nformat on = m s nformat on
              )))
    )
  }
}
