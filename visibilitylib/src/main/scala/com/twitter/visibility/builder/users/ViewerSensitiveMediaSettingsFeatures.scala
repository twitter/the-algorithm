package com.tw ter.v s b l y.bu lder.users

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.User d
 mport com.tw ter.v s b l y.common.UserSens  ve d aSett ngsS ce
 mport com.tw ter.v s b l y.features.V e r d
 mport com.tw ter.v s b l y.features.V e rSens  ve d aSett ngs
 mport com.tw ter.v s b l y.models.UserSens  ve d aSett ngs


class V e rSens  ve d aSett ngsFeatures(
  userSens  ve d aSett ngsS ce: UserSens  ve d aSett ngsS ce,
  statsRece ver: StatsRece ver) {
  pr vate[t ] val scopedStatsRece ver =
    statsRece ver.scope("v e r_sens  ve_ d a_sett ngs_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  def forV e r d(v e r d: Opt on[User d]): FeatureMapBu lder => FeatureMapBu lder = { bu lder =>
    requests. ncr()

    bu lder
      .w hConstantFeature(V e r d, v e r d)
      .w hFeature(V e rSens  ve d aSett ngs, v e rSens  ve d aSett ngs(v e r d))
  }

  def v e rSens  ve d aSett ngs(v e r d: Opt on[User d]): St ch[UserSens  ve d aSett ngs] = {
    (v e r d match {
      case So (user d) =>
        userSens  ve d aSett ngsS ce
          .userSens  ve d aSett ngs(user d)
          .handle {
            case NotFound => None
          }
      case _ => St ch.value(None)
    }).map(UserSens  ve d aSett ngs)
  }
}
