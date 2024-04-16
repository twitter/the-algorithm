package com.tw ter.v s b l y.bu lder.users

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.User d
 mport com.tw ter.v s b l y.common.UserSearchSafetyS ce
 mport com.tw ter.v s b l y.features.V e r d
 mport com.tw ter.v s b l y.features.V e rOpt nBlock ng
 mport com.tw ter.v s b l y.features.V e rOpt nF lter ng

class V e rSearchSafetyFeatures(
  userSearchSafetyS ce: UserSearchSafetyS ce,
  statsRece ver: StatsRece ver) {
  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("v e r_search_safety_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  pr vate[t ] val v e rOpt nBlock ngRequests =
    scopedStatsRece ver.scope(V e rOpt nBlock ng.na ).counter("requests")

  pr vate[t ] val v e rOpt nF lter ngRequests =
    scopedStatsRece ver.scope(V e rOpt nF lter ng.na ).counter("requests")

  def forV e r d(v e r d: Opt on[User d]): FeatureMapBu lder => FeatureMapBu lder = { bu lder =>
    requests. ncr()

    bu lder
      .w hConstantFeature(V e r d, v e r d)
      .w hFeature(V e rOpt nBlock ng, v e rOpt nBlock ng(v e r d))
      .w hFeature(V e rOpt nF lter ng, v e rOpt nF lter ng(v e r d))
  }

  def v e rOpt nBlock ng(v e r d: Opt on[User d]): St ch[Boolean] = {
    v e rOpt nBlock ngRequests. ncr()
    v e r d match {
      case So (user d) => userSearchSafetyS ce.opt nBlock ng(user d)
      case _ => St ch.False
    }
  }

  def v e rOpt nF lter ng(v e r d: Opt on[User d]): St ch[Boolean] = {
    v e rOpt nF lter ngRequests. ncr()
    v e r d match {
      case So (user d) => userSearchSafetyS ce.opt nF lter ng(user d)
      case _ => St ch.False
    }
  }
}
