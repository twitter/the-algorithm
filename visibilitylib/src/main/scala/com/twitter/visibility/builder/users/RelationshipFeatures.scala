package com.tw ter.v s b l y.bu lder.users

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.User d
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.features._

class Relat onsh pFeatures(
  userRelat onsh pS ce: UserRelat onsh pS ce,
  statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("relat onsh p_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  pr vate[t ] val authorFollowsV e r =
    scopedStatsRece ver.scope(AuthorFollowsV e r.na ).counter("requests")
  pr vate[t ] val v e rFollowsAuthor =
    scopedStatsRece ver.scope(V e rFollowsAuthor.na ).counter("requests")
  pr vate[t ] val authorBlocksV e r =
    scopedStatsRece ver.scope(AuthorBlocksV e r.na ).counter("requests")
  pr vate[t ] val v e rBlocksAuthor =
    scopedStatsRece ver.scope(V e rBlocksAuthor.na ).counter("requests")
  pr vate[t ] val authorMutesV e r =
    scopedStatsRece ver.scope(AuthorMutesV e r.na ).counter("requests")
  pr vate[t ] val v e rMutesAuthor =
    scopedStatsRece ver.scope(V e rMutesAuthor.na ).counter("requests")
  pr vate[t ] val authorHasReportedV e r =
    scopedStatsRece ver.scope(AuthorReportsV e rAsSpam.na ).counter("requests")
  pr vate[t ] val v e rHasReportedAuthor =
    scopedStatsRece ver.scope(V e rReportsAuthorAsSpam.na ).counter("requests")
  pr vate[t ] val v e rMutesRet etsFromAuthor =
    scopedStatsRece ver.scope(V e rMutesRet etsFromAuthor.na ).counter("requests")

  def forAuthor d(
    author d: Long,
    v e r d: Opt on[Long]
  ): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()

    _.w hFeature(AuthorFollowsV e r, authorFollowsV e r(author d, v e r d))
      .w hFeature(V e rFollowsAuthor, v e rFollowsAuthor(author d, v e r d))
      .w hFeature(AuthorBlocksV e r, authorBlocksV e r(author d, v e r d))
      .w hFeature(V e rBlocksAuthor, v e rBlocksAuthor(author d, v e r d))
      .w hFeature(AuthorMutesV e r, authorMutesV e r(author d, v e r d))
      .w hFeature(V e rMutesAuthor, v e rMutesAuthor(author d, v e r d))
      .w hFeature(AuthorReportsV e rAsSpam, authorHasReportedV e r(author d, v e r d))
      .w hFeature(V e rReportsAuthorAsSpam, v e rHasReportedAuthor(author d, v e r d))
      .w hFeature(V e rMutesRet etsFromAuthor, v e rMutesRet etsFromAuthor(author d, v e r d))
  }

  def forNoAuthor(): FeatureMapBu lder => FeatureMapBu lder = {
    _.w hConstantFeature(AuthorFollowsV e r, false)
      .w hConstantFeature(V e rFollowsAuthor, false)
      .w hConstantFeature(AuthorBlocksV e r, false)
      .w hConstantFeature(V e rBlocksAuthor, false)
      .w hConstantFeature(AuthorMutesV e r, false)
      .w hConstantFeature(V e rMutesAuthor, false)
      .w hConstantFeature(AuthorReportsV e rAsSpam, false)
      .w hConstantFeature(V e rReportsAuthorAsSpam, false)
      .w hConstantFeature(V e rMutesRet etsFromAuthor, false)
  }

  def forAuthor(author: User, v e r d: Opt on[Long]): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()


    _.w hFeature(AuthorFollowsV e r, authorFollowsV e r(author, v e r d))
      .w hFeature(V e rFollowsAuthor, v e rFollowsAuthor(author, v e r d))
      .w hFeature(AuthorBlocksV e r, authorBlocksV e r(author, v e r d))
      .w hFeature(V e rBlocksAuthor, v e rBlocksAuthor(author, v e r d))
      .w hFeature(AuthorMutesV e r, authorMutesV e r(author, v e r d))
      .w hFeature(V e rMutesAuthor, v e rMutesAuthor(author, v e r d))
      .w hFeature(AuthorReportsV e rAsSpam, authorHasReportedV e r(author. d, v e r d))
      .w hFeature(V e rReportsAuthorAsSpam, v e rHasReportedAuthor(author. d, v e r d))
      .w hFeature(V e rMutesRet etsFromAuthor, v e rMutesRet etsFromAuthor(author, v e r d))
  }

  def v e rFollowsAuthor(author d: User d, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(author d, v e r d, userRelat onsh pS ce.follows, v e rFollowsAuthor)

  def v e rFollowsAuthor(author: User, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(
      author,
      v e r d,
      p => p.follow ng,
      userRelat onsh pS ce.follows,
      v e rFollowsAuthor)

  def authorFollowsV e r(author d: User d, v e r d: Opt on[User d]): St ch[Boolean] =
    AuthorVerbsV e r(author d, v e r d, userRelat onsh pS ce.follows, authorFollowsV e r)

  def authorFollowsV e r(author: User, v e r d: Opt on[User d]): St ch[Boolean] =
    AuthorVerbsV e r(
      author,
      v e r d,
      p => p.follo dBy,
      userRelat onsh pS ce.follows,
      authorFollowsV e r)

  def v e rBlocksAuthor(author d: User d, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(author d, v e r d, userRelat onsh pS ce.blocks, v e rBlocksAuthor)

  def v e rBlocksAuthor(author: User, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(
      author,
      v e r d,
      p => p.block ng,
      userRelat onsh pS ce.blocks,
      v e rBlocksAuthor)

  def authorBlocksV e r(author d: User d, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(author d, v e r d, userRelat onsh pS ce.blockedBy, authorBlocksV e r)

  def authorBlocksV e r(author: User, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(
      author,
      v e r d,
      p => p.blockedBy,
      userRelat onsh pS ce.blockedBy,
      authorBlocksV e r)

  def v e rMutesAuthor(author d: User d, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(author d, v e r d, userRelat onsh pS ce.mutes, v e rMutesAuthor)

  def v e rMutesAuthor(author: User, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(
      author,
      v e r d,
      p => p.mut ng,
      userRelat onsh pS ce.mutes,
      v e rMutesAuthor)

  def authorMutesV e r(author d: User d, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(author d, v e r d, userRelat onsh pS ce.mutedBy, authorMutesV e r)

  def authorMutesV e r(author: User, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(
      author,
      v e r d,
      p => p.mutedBy,
      userRelat onsh pS ce.mutedBy,
      authorMutesV e r)

  def v e rHasReportedAuthor(author d: User d, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(
      author d,
      v e r d,
      userRelat onsh pS ce.reportsAsSpam,
      v e rHasReportedAuthor)

  def authorHasReportedV e r(author d: User d, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(
      author d,
      v e r d,
      userRelat onsh pS ce.reportedAsSpamBy,
      authorHasReportedV e r)

  def v e rMutesRet etsFromAuthor(author d: User d, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(
      author d,
      v e r d,
      userRelat onsh pS ce.noRet etsFrom,
      v e rMutesRet etsFromAuthor)

  def v e rMutesRet etsFromAuthor(author: User, v e r d: Opt on[User d]): St ch[Boolean] =
    V e rVerbsAuthor(
      author,
      v e r d,
      p => p.noRet etsFrom,
      userRelat onsh pS ce.noRet etsFrom,
      v e rMutesRet etsFromAuthor)
}
