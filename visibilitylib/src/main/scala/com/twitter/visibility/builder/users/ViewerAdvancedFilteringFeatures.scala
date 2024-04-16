package com.tw ter.v s b l y.bu lder.users

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.g zmoduck.thr ftscala.AdvancedF lters
 mport com.tw ter.g zmoduck.thr ftscala. nt onF lter
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.features.V e rF ltersDefaultProf le mage
 mport com.tw ter.v s b l y.features.V e rF ltersNewUsers
 mport com.tw ter.v s b l y.features.V e rF ltersNoConf r dEma l
 mport com.tw ter.v s b l y.features.V e rF ltersNoConf r dPhone
 mport com.tw ter.v s b l y.features.V e rF ltersNotFollo dBy
 mport com.tw ter.v s b l y.features.V e r nt onF lter

class V e rAdvancedF lter ngFeatures(userS ce: UserS ce, statsRece ver: StatsRece ver) {
  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("v e r_advanced_f lter ng_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  pr vate[t ] val v e rF ltersNoConf r dEma l =
    scopedStatsRece ver.scope(V e rF ltersNoConf r dEma l.na ).counter("requests")
  pr vate[t ] val v e rF ltersNoConf r dPhone =
    scopedStatsRece ver.scope(V e rF ltersNoConf r dPhone.na ).counter("requests")
  pr vate[t ] val v e rF ltersDefaultProf le mage =
    scopedStatsRece ver.scope(V e rF ltersDefaultProf le mage.na ).counter("requests")
  pr vate[t ] val v e rF ltersNewUsers =
    scopedStatsRece ver.scope(V e rF ltersNewUsers.na ).counter("requests")
  pr vate[t ] val v e rF ltersNotFollo dBy =
    scopedStatsRece ver.scope(V e rF ltersNotFollo dBy.na ).counter("requests")
  pr vate[t ] val v e r nt onF lter =
    scopedStatsRece ver.scope(V e r nt onF lter.na ).counter("requests")

  def forV e r d(v e r d: Opt on[Long]): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()

    _.w hFeature(V e rF ltersNoConf r dEma l, v e rF ltersNoConf r dEma l(v e r d))
      .w hFeature(V e rF ltersNoConf r dPhone, v e rF ltersNoConf r dPhone(v e r d))
      .w hFeature(V e rF ltersDefaultProf le mage, v e rF ltersDefaultProf le mage(v e r d))
      .w hFeature(V e rF ltersNewUsers, v e rF ltersNewUsers(v e r d))
      .w hFeature(V e rF ltersNotFollo dBy, v e rF ltersNotFollo dBy(v e r d))
      .w hFeature(V e r nt onF lter, v e r nt onF lter(v e r d))
  }

  def v e rF ltersNoConf r dEma l(v e r d: Opt on[Long]): St ch[Boolean] =
    v e rAdvancedF lters(v e r d, af => af.f lterNoConf r dEma l, v e rF ltersNoConf r dEma l)

  def v e rF ltersNoConf r dPhone(v e r d: Opt on[Long]): St ch[Boolean] =
    v e rAdvancedF lters(v e r d, af => af.f lterNoConf r dPhone, v e rF ltersNoConf r dPhone)

  def v e rF ltersDefaultProf le mage(v e r d: Opt on[Long]): St ch[Boolean] =
    v e rAdvancedF lters(
      v e r d,
      af => af.f lterDefaultProf le mage,
      v e rF ltersDefaultProf le mage
    )

  def v e rF ltersNewUsers(v e r d: Opt on[Long]): St ch[Boolean] =
    v e rAdvancedF lters(v e r d, af => af.f lterNewUsers, v e rF ltersNewUsers)

  def v e rF ltersNotFollo dBy(v e r d: Opt on[Long]): St ch[Boolean] =
    v e rAdvancedF lters(v e r d, af => af.f lterNotFollo dBy, v e rF ltersNotFollo dBy)

  def v e r nt onF lter(v e r d: Opt on[Long]): St ch[ nt onF lter] = {
    v e r nt onF lter. ncr()
    v e r d match {
      case So ( d) =>
        userS ce.get nt onF lter( d).handle {
          case NotFound =>
             nt onF lter.Unf ltered
        }
      case _ => St ch.value( nt onF lter.Unf ltered)
    }
  }

  pr vate[t ] def v e rAdvancedF lters(
    v e r d: Opt on[Long],
    advancedF lterC ck: AdvancedF lters => Boolean,
    featureCounter: Counter
  ): St ch[Boolean] = {
    featureCounter. ncr()

    val advancedF lters = v e r d match {
      case So ( d) => userS ce.getAdvancedF lters( d)
      case _ => St ch.value(AdvancedF lters())
    }

    advancedF lters.map(advancedF lterC ck)
  }
}
