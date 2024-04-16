package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.bu lder.users.V e rVerbsAuthor
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.features.T et sExclus veT et
 mport com.tw ter.v s b l y.features.V e r sExclus veT etRootAuthor
 mport com.tw ter.v s b l y.features.V e rSuperFollowsExclus veT etRootAuthor
 mport com.tw ter.v s b l y.models.V e rContext

class Exclus veT etFeatures(
  userRelat onsh pS ce: UserRelat onsh pS ce,
  statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("exclus ve_t et_features")
  pr vate[t ] val v e rSuperFollowsAuthor =
    scopedStatsRece ver.scope(V e rSuperFollowsExclus veT etRootAuthor.na ).counter("requests")

  def rootAuthor d(t et: T et): Opt on[Long] =
    t et.exclus veT etControl.map(_.conversat onAuthor d)

  def v e r sRootAuthor(
    t et: T et,
    v e r dOpt: Opt on[Long]
  ): Boolean =
    (rootAuthor d(t et), v e r dOpt) match {
      case (So (rootAuthor d), So (v e r d))  f rootAuthor d == v e r d => true
      case _ => false
    }

  def v e rSuperFollowsRootAuthor(
    t et: T et,
    v e r d: Opt on[Long]
  ): St ch[Boolean] =
    rootAuthor d(t et) match {
      case So (author d) =>
        V e rVerbsAuthor(
          author d,
          v e r d,
          userRelat onsh pS ce.superFollows,
          v e rSuperFollowsAuthor)
      case None =>
        St ch.False
    }

  def forT et(
    t et: T et,
    v e rContext: V e rContext
  ): FeatureMapBu lder => FeatureMapBu lder = {
    val v e r d = v e rContext.user d

    _.w hConstantFeature(T et sExclus veT et, t et.exclus veT etControl. sDef ned)
      .w hConstantFeature(V e r sExclus veT etRootAuthor, v e r sRootAuthor(t et, v e r d))
      .w hFeature(
        V e rSuperFollowsExclus veT etRootAuthor,
        v e rSuperFollowsRootAuthor(t et, v e r d))
  }

  def forT etOnly(t et: T et): FeatureMapBu lder => FeatureMapBu lder = {
    _.w hConstantFeature(T et sExclus veT et, t et.exclus veT etControl. sDef ned)
  }
}
