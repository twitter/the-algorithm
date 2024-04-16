package com.tw ter.follow_recom ndat ons.serv ces

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l.prof leSt chSeqResults
 mport com.tw ter.follow_recom ndat ons.common.cl ents. mpress on_store.Wtf mpress onStore
 mport com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph.Soc alGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.rank ng.HydrateFeaturesTransform
 mport com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.rank ng.MlRanker
 mport com.tw ter.follow_recom ndat ons.common.ut ls.RescueW hStatsUt ls.rescueW hStats
 mport com.tw ter.follow_recom ndat ons.conf gap .dec ders.Dec derParams
 mport com.tw ter.follow_recom ndat ons.logg ng.FrsLogger
 mport com.tw ter.follow_recom ndat ons.models.Scor ngUserRequest
 mport com.tw ter.follow_recom ndat ons.models.Scor ngUserResponse
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class UserScor ngServ ce @ nject() (
  soc alGraph: Soc alGraphCl ent,
  wtf mpress onStore: Wtf mpress onStore,
  hydrateFeaturesTransform: HydrateFeaturesTransform[Scor ngUserRequest],
  mlRanker: MlRanker[Scor ngUserRequest],
  resultLogger: FrsLogger,
  stats: StatsRece ver) {

  pr vate val scopedStats: StatsRece ver = stats.scope(t .getClass.getS mpleNa )
  pr vate val d sabledCounter: Counter = scopedStats.counter("d sabled")

  def get(request: Scor ngUserRequest): St ch[Scor ngUserResponse] = {
     f (request.params(Dec derParams.EnableScoreUserCand dates)) {
      val hydratedRequest = hydrate(request)
      val cand datesSt ch = hydratedRequest.flatMap { req =>
        hydrateFeaturesTransform.transform(req, request.cand dates).flatMap {
          cand dateW hFeatures =>
            mlRanker.rank(req, cand dateW hFeatures)
        }
      }
      prof leSt chSeqResults(cand datesSt ch, scopedStats)
        .map(Scor ngUserResponse)
        .onSuccess { response =>
           f (resultLogger.shouldLog(request.debugParams)) {
            resultLogger.logScor ngResult(request, response)
          }
        }
    } else {
      d sabledCounter. ncr()
      St ch.value(Scor ngUserResponse(N l))
    }
  }

  pr vate def hydrate(request: Scor ngUserRequest): St ch[Scor ngUserRequest] = {
    val allSt c s = St ch.collect(request.cl entContext.user d.map { user d =>
      val recentFollo dUser dsSt ch =
        rescueW hStats(
          soc alGraph.getRecentFollo dUser ds(user d),
          stats,
          "recentFollo dUser ds")
      val recentFollo dByUser dsSt ch =
        rescueW hStats(
          soc alGraph.getRecentFollo dByUser ds(user d),
          stats,
          "recentFollo dByUser ds")
      val wtf mpress onsSt ch =
        rescueW hStats(
          wtf mpress onStore.get(user d, request.d splayLocat on),
          stats,
          "wtf mpress ons")
      St ch.jo n(recentFollo dUser dsSt ch, recentFollo dByUser dsSt ch, wtf mpress onsSt ch)
    })
    allSt c s.map {
      case So ((recentFollo dUser ds, recentFollo dByUser ds, wtf mpress ons)) =>
        request.copy(
          recentFollo dUser ds =
             f (recentFollo dUser ds. sEmpty) None else So (recentFollo dUser ds),
          recentFollo dByUser ds =
             f (recentFollo dByUser ds. sEmpty) None else So (recentFollo dByUser ds),
          wtf mpress ons =  f (wtf mpress ons. sEmpty) None else So (wtf mpress ons)
        )
      case _ => request
    }
  }
}
