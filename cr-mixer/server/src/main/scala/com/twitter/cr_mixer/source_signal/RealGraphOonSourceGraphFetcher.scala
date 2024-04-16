package com.tw ter.cr_m xer.s ce_s gnal

 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.model.GraphS ce nfo
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.param.RealGraphOonParams
 mport com.tw ter.cr_m xer.s ce_s gnal.S ceFetc r.Fetc rQuery
 mport com.tw ter.cr_m xer.thr ftscala.S ceType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport com.tw ter.wtf.cand date.thr ftscala.Cand dateSeq
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

/**
 * T  store fetch user recom ndat ons from RealGraphOON (go/realgraph) for a g ven user d
 */
@S ngleton
case class RealGraphOonS ceGraphFetc r @ nject() (
  @Na d(ModuleNa s.RealGraphOonStore) realGraphOonStore: ReadableStore[User d, Cand dateSeq],
  overr de val t  outConf g: T  outConf g,
  globalStats: StatsRece ver)
    extends S ceGraphFetc r {

  overr de protected val stats: StatsRece ver = globalStats.scope( dent f er)
  overr de protected val graphS ceType: S ceType = S ceType.RealGraphOon

  overr de def  sEnabled(query: Fetc rQuery): Boolean = {
    query.params(RealGraphOonParams.EnableS ceGraphParam)
  }

  overr de def fetchAndProcess(
    query: Fetc rQuery,
  ): Future[Opt on[GraphS ce nfo]] = {
    val rawS gnals = trackPer emStats(query)(
      realGraphOonStore.get(query.user d).map {
        _.map { cand dateSeq =>
          cand dateSeq.cand dates
            .map { cand date =>
              // Bundle t  user d w h  s score
              (cand date.user d, cand date.score)
            }.take(query.params(RealGraphOonParams.MaxConsu rSeedsNumParam))
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
