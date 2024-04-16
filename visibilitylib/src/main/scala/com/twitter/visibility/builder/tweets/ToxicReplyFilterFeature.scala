package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.content alth.tox creplyf lter.thr ftscala.F lterState
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.features.Tox cReplyF lterConversat onAuthor sV e r
 mport com.tw ter.v s b l y.features.Tox cReplyF lterState

class Tox cReplyF lterFeature(
  statsRece ver: StatsRece ver) {

  def forT et(t et: T et, v e r d: Opt on[Long]): FeatureMapBu lder => FeatureMapBu lder = {
    bu lder =>
      requests. ncr()

      bu lder
        .w hConstantFeature(Tox cReplyF lterState,  sT etF lteredFromAuthor(t et))
        .w hConstantFeature(
          Tox cReplyF lterConversat onAuthor sV e r,
           sRootAuthorV e r(t et, v e r d))
  }

  pr vate[t ] def  sRootAuthorV e r(t et: T et, maybeV e r d: Opt on[Long]): Boolean = {
    val maybeAuthor d = t et.f lteredReplyDeta ls.map(_.conversat onAuthor d)

    (maybeV e r d, maybeAuthor d) match {
      case (So (v e r d), So (author d))  f v e r d == author d => {
        rootAuthorV e rStats. ncr()
        true
      }
      case _ => false
    }
  }

  pr vate[t ] def  sT etF lteredFromAuthor(
    t et: T et,
  ): F lterState = {
    val result = t et.f lteredReplyDeta ls.map(_.f lterState).getOrElse(F lterState.Unf ltered)

     f (result == F lterState.F lteredFromAuthor) {
      f lteredFromAuthorStats. ncr()
    }
    result
  }

  pr vate[t ] val scopedStatsRece ver =
    statsRece ver.scope("tox creplyf lter")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  pr vate[t ] val rootAuthorV e rStats =
    scopedStatsRece ver.scope(Tox cReplyF lterConversat onAuthor sV e r.na ).counter("requests")

  pr vate[t ] val f lteredFromAuthorStats =
    scopedStatsRece ver.scope(Tox cReplyF lterState.na ).counter("requests")
}
