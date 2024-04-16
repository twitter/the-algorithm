package com.tw ter.cr_m xer.cand date_generat on

 mport com.tw ter.cr_m xer.blender.AdsBlender
 mport com.tw ter.cr_m xer.logg ng.AdsRecom ndat onsScr beLogger
 mport com.tw ter.cr_m xer.model.AdsCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.BlendedAdsCand date
 mport com.tw ter.cr_m xer.model. n  alAdsCand date
 mport com.tw ter.cr_m xer.model.RankedAdsCand date
 mport com.tw ter.cr_m xer.model.S ce nfo
 mport com.tw ter.cr_m xer.param.AdsParams
 mport com.tw ter.cr_m xer.param.Consu rsBasedUserAdGraphParams
 mport com.tw ter.cr_m xer.s ce_s gnal.RealGraph nS ceGraphFetc r
 mport com.tw ter.cr_m xer.s ce_s gnal.S ceFetc r.Fetc rQuery
 mport com.tw ter.cr_m xer.s ce_s gnal.UssS ceS gnalFetc r
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.ut l.Future

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class AdsCand dateGenerator @ nject() (
  ussS ceS gnalFetc r: UssS ceS gnalFetc r,
  realGraph nS ceGraphFetc r: RealGraph nS ceGraphFetc r,
  adsCand dateS ceRouter: AdsCand dateS cesRouter,
  adsBlender: AdsBlender,
  scr beLogger: AdsRecom ndat onsScr beLogger,
  globalStats: StatsRece ver) {

  pr vate val stats: StatsRece ver = globalStats.scope(t .getClass.getCanon calNa )
  pr vate val fetchS cesStats = stats.scope("fetchS ces")
  pr vate val fetchRealGraphSeedsStats = stats.scope("fetchRealGraphSeeds")
  pr vate val fetchCand datesStats = stats.scope("fetchCand dates")
  pr vate val  nterleaveStats = stats.scope(" nterleave")
  pr vate val rankStats = stats.scope("rank")

  def get(query: AdsCand dateGeneratorQuery): Future[Seq[RankedAdsCand date]] = {
    val allStats = stats.scope("all")
    val perProductStats = stats.scope("perProduct", query.product.toStr ng)

    StatsUt l.track emsStats(allStats) {
      StatsUt l.track emsStats(perProductStats) {
        for {
          // fetch s ce s gnals
          s ceS gnals <- StatsUt l.trackBlockStats(fetchS cesStats) {
            fetchS ces(query)
          }
          realGraphSeeds <- StatsUt l.track emMapStats(fetchRealGraphSeedsStats) {
            fetchSeeds(query)
          }
          // get  n  al cand dates from s m lar y eng nes
          // hydrate l ne em nfo and f lter out non act ve ads
           n  alCand dates <- StatsUt l.trackBlockStats(fetchCand datesStats) {
            fetchCand dates(query, s ceS gnals, realGraphSeeds)
          }

          // blend cand dates
          blendedCand dates <- StatsUt l.track emsStats( nterleaveStats) {
             nterleave( n  alCand dates)
          }

          rankedCand dates <- StatsUt l.track emsStats(rankStats) {
            rank(
              blendedCand dates,
              query.params(AdsParams.EnableScoreBoost),
              query.params(AdsParams.AdsCand dateGenerat onScoreBoostFactor),
              rankStats)
          }
        } y eld {
          rankedCand dates.take(query.maxNumResults)
        }
      }
    }

  }

  def fetchS ces(
    query: AdsCand dateGeneratorQuery
  ): Future[Set[S ce nfo]] = {
    val fetc rQuery =
      Fetc rQuery(query.user d, query.product, query.userState, query.params)
    ussS ceS gnalFetc r.get(fetc rQuery).map(_.getOrElse(Seq.empty).toSet)
  }

  pr vate def fetchCand dates(
    query: AdsCand dateGeneratorQuery,
    s ceS gnals: Set[S ce nfo],
    realGraphSeeds: Map[User d, Double]
  ): Future[Seq[Seq[ n  alAdsCand date]]] = {
    scr beLogger.scr be n  alAdsCand dates(
      query,
      adsCand dateS ceRouter
        .fetchCand dates(query.user d, s ceS gnals, realGraphSeeds, query.params),
      query.params(AdsParams.EnableScr be)
    )

  }

  pr vate def fetchSeeds(
    query: AdsCand dateGeneratorQuery
  ): Future[Map[User d, Double]] = {
     f (query.params(Consu rsBasedUserAdGraphParams.EnableS ceParam)) {
      realGraph nS ceGraphFetc r
        .get(Fetc rQuery(query.user d, query.product, query.userState, query.params))
        .map(_.map(_.seedW hScores).getOrElse(Map.empty))
    } else Future.value(Map.empty[User d, Double])
  }

  pr vate def  nterleave(
    cand dates: Seq[Seq[ n  alAdsCand date]]
  ): Future[Seq[BlendedAdsCand date]] = {
    adsBlender
      .blend(cand dates)
  }

  pr vate def rank(
    cand dates: Seq[BlendedAdsCand date],
    enableScoreBoost: Boolean,
    scoreBoostFactor: Double,
    statsRece ver: StatsRece ver,
  ): Future[Seq[RankedAdsCand date]] = {

    val cand dateS ze = cand dates.s ze
    val rankedCand dates = cand dates.z pW h ndex.map {
      case (cand date,  ndex) =>
        val score = 0.5 + 0.5 * ((cand dateS ze -  ndex).toDouble / cand dateS ze)
        val boostedScore =  f (enableScoreBoost) {
          statsRece ver.stat("boostedScore").add((100.0 * score * scoreBoostFactor).toFloat)
          score * scoreBoostFactor
        } else {
          statsRece ver.stat("score").add((100.0 * score).toFloat)
          score
        }
        cand date.toRankedAdsCand date(boostedScore)
    }
    Future.value(rankedCand dates)
  }
}
