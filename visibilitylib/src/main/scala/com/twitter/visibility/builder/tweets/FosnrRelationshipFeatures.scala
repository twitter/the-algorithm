package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.bu lder.users.V e rVerbsAuthor
 mport com.tw ter.v s b l y.common.User d
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.features._
 mport com.tw ter.v s b l y.models.V olat onLevel

class FosnrRelat onsh pFeatures(
  t etLabels: T etLabels,
  userRelat onsh pS ce: UserRelat onsh pS ce,
  statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("fonsr_relat onsh p_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  pr vate[t ] val v e rFollowsAuthorOfV olat ngT et =
    scopedStatsRece ver.scope(V e rFollowsAuthorOfV olat ngT et.na ).counter("requests")

  pr vate[t ] val v e rDoesNotFollowAuthorOfV olat ngT et =
    scopedStatsRece ver.scope(V e rDoesNotFollowAuthorOfV olat ngT et.na ).counter("requests")

  def forT etAndAuthor d(
    t et: T et,
    author d: Long,
    v e r d: Opt on[Long]
  ): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()
    _.w hFeature(
      V e rFollowsAuthorOfV olat ngT et,
      v e rFollowsAuthorOfV olat ngT et(t et, author d, v e r d))
      .w hFeature(
        V e rDoesNotFollowAuthorOfV olat ngT et,
        v e rDoesNotFollowAuthorOfV olat ngT et(t et, author d, v e r d))
  }

  def v e rFollowsAuthorOfV olat ngT et(
    t et: T et,
    author d: User d,
    v e r d: Opt on[User d]
  ): St ch[Boolean] =
    t etLabels.forT et(t et).flatMap { safetyLabels =>
       f (safetyLabels
          .map(V olat onLevel.fromT etSafetyLabelOpt).collect {
            case So (level) => level
          }. sEmpty) {
        St ch.False
      } else {
        V e rVerbsAuthor(
          author d,
          v e r d,
          userRelat onsh pS ce.follows,
          v e rFollowsAuthorOfV olat ngT et)
      }
    }

  def v e rDoesNotFollowAuthorOfV olat ngT et(
    t et: T et,
    author d: User d,
    v e r d: Opt on[User d]
  ): St ch[Boolean] =
    t etLabels.forT et(t et).flatMap { safetyLabels =>
       f (safetyLabels
          .map(V olat onLevel.fromT etSafetyLabelOpt).collect {
            case So (level) => level
          }. sEmpty) {
        St ch.False
      } else {
        V e rVerbsAuthor(
          author d,
          v e r d,
          userRelat onsh pS ce.follows,
          v e rDoesNotFollowAuthorOfV olat ngT et).map(follow ng => !follow ng)
      }
    }

}
