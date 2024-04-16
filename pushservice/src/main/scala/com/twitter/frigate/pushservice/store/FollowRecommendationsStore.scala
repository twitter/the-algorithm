package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.thr ftscala.FollowRecom ndat onsThr ftServ ce
 mport com.tw ter.follow_recom ndat ons.thr ftscala.Recom ndat on
 mport com.tw ter.follow_recom ndat ons.thr ftscala.Recom ndat onRequest
 mport com.tw ter.follow_recom ndat ons.thr ftscala.Recom ndat onResponse
 mport com.tw ter.follow_recom ndat ons.thr ftscala.UserRecom ndat on
 mport com.tw ter. nject.Logg ng
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

case class FollowRecom ndat onsStore(
  frsCl ent: FollowRecom ndat onsThr ftServ ce. thodPerEndpo nt,
  statsRece ver: StatsRece ver)
    extends ReadableStore[Recom ndat onRequest, Recom ndat onResponse]
    w h Logg ng {

  pr vate val scopedStats = statsRece ver.scope(getClass.getS mpleNa )
  pr vate val requests = scopedStats.counter("requests")
  pr vate val val d = scopedStats.counter("val d")
  pr vate val  nval d = scopedStats.counter(" nval d")
  pr vate val numTotalResults = scopedStats.stat("total_results")
  pr vate val numVal dResults = scopedStats.stat("val d_results")

  overr de def get(request: Recom ndat onRequest): Future[Opt on[Recom ndat onResponse]] = {
    requests. ncr()
    frsCl ent.getRecom ndat ons(request).map { response =>
      numTotalResults.add(response.recom ndat ons.s ze)
      val val dRecs = response.recom ndat ons.f lter {
        case Recom ndat on.User(_: UserRecom ndat on) =>
          val d. ncr()
          true
        case _ =>
           nval d. ncr()
          false
      }

      numVal dResults.add(val dRecs.s ze)
      So (
        Recom ndat onResponse(
          recom ndat ons = val dRecs
        ))
    }
  }
}
