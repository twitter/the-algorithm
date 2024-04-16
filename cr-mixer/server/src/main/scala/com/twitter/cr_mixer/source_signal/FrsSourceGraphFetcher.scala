package com.tw ter.cr_m xer.s ce_s gnal

 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.model.GraphS ce nfo
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.param.FrsParams
 mport com.tw ter.cr_m xer.s ce_s gnal.FrsStore.FrsQueryResult
 mport com.tw ter.cr_m xer.s ce_s gnal.S ceFetc r.Fetc rQuery
 mport com.tw ter.cr_m xer.thr ftscala.S ceType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

/***
 * T  store fetc s user recom ndat ons from FRS (go/frs) for a g ven user d
 */
@S ngleton
case class FrsS ceGraphFetc r @ nject() (
  @Na d(ModuleNa s.FrsStore) frsStore: ReadableStore[FrsStore.Query, Seq[FrsQueryResult]],
  overr de val t  outConf g: T  outConf g,
  globalStats: StatsRece ver)
    extends S ceGraphFetc r {

  overr de protected val stats: StatsRece ver = globalStats.scope( dent f er)
  overr de protected val graphS ceType: S ceType = S ceType.FollowRecom ndat on

  overr de def  sEnabled(query: Fetc rQuery): Boolean = {
    query.params(FrsParams.EnableS ceGraphParam)
  }

  overr de def fetchAndProcess(
    query: Fetc rQuery,
  ): Future[Opt on[GraphS ce nfo]] = {

    val rawS gnals = trackPer emStats(query)(
      frsStore
        .get(
          FrsStore
            .Query(query.user d, query.params(FrsParams.MaxConsu rSeedsNumParam))).map {
          _.map {
            _.map { v => (v.user d, v.score) }
          }
        }
    )
    rawS gnals.map {
      _.map { userW hScores =>
        convertGraphS ce nfo(userW hScores)
      }
    }
  }
}
