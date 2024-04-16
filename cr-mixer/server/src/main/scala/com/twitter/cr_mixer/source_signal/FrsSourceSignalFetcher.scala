package com.tw ter.cr_m xer.s ce_s gnal

 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.model.S ce nfo
 mport com.tw ter.cr_m xer.param.FrsParams
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.cr_m xer.s ce_s gnal.FrsStore.FrsQueryResult
 mport com.tw ter.cr_m xer.s ce_s gnal.S ceFetc r.Fetc rQuery
 mport com.tw ter.cr_m xer.thr ftscala.S ceType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton
 mport javax. nject. nject
 mport javax. nject.Na d

@S ngleton
case class FrsS ceS gnalFetc r @ nject() (
  @Na d(ModuleNa s.FrsStore) frsStore: ReadableStore[FrsStore.Query, Seq[FrsQueryResult]],
  overr de val t  outConf g: T  outConf g,
  globalStats: StatsRece ver)
    extends S ceS gnalFetc r {

  overr de protected val stats: StatsRece ver = globalStats.scope( dent f er)
  overr de type S gnalConvertType = User d

  overr de def  sEnabled(query: Fetc rQuery): Boolean = {
    query.params(FrsParams.EnableS ceParam)
  }

  overr de def fetchAndProcess(query: Fetc rQuery): Future[Opt on[Seq[S ce nfo]]] = {
    // Fetch raw s gnals
    val rawS gnals = frsStore
      .get(FrsStore.Query(query.user d, query.params(GlobalParams.Un f edMaxS ceKeyNum)))
      .map {
        _.map {
          _.map {
            _.user d
          }
        }
      }
    // Process s gnals
    rawS gnals.map {
      _.map { frsUsers =>
        convertS ce nfo(S ceType.FollowRecom ndat on, frsUsers)
      }
    }
  }

  overr de def convertS ce nfo(
    s ceType: S ceType,
    s gnals: Seq[S gnalConvertType]
  ): Seq[S ce nfo] = {
    s gnals.map { s gnal =>
      S ce nfo(
        s ceType = s ceType,
         nternal d =  nternal d.User d(s gnal),
        s ceEventT   = None
      )
    }
  }
}
