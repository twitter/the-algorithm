package com.tw ter.cr_m xer.cand date_generat on

 mport com.tw ter.cr_m xer.blender.Sw chBlender
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.f lter.PostRankF lterRunner
 mport com.tw ter.cr_m xer.f lter.PreRankF lterRunner
 mport com.tw ter.cr_m xer.logg ng.CrM xerScr beLogger
 mport com.tw ter.cr_m xer.model.BlendedCand date
 mport com.tw ter.cr_m xer.model.CrCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.GraphS ce nfo
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.model.RankedCand date
 mport com.tw ter.cr_m xer.model.S ce nfo
 mport com.tw ter.cr_m xer.param.RankerParams
 mport com.tw ter.cr_m xer.param.RecentNegat veS gnalParams
 mport com.tw ter.cr_m xer.ranker.Sw chRanker
 mport com.tw ter.cr_m xer.s ce_s gnal.S ce nfoRouter
 mport com.tw ter.cr_m xer.s ce_s gnal.UssStore.EnabledNegat veS ceTypes
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.JavaT  r
 mport com.tw ter.ut l.T  r

 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * For now   performs t  ma n steps as follows:
 * 1. S ce s gnal (v a USS, FRS) fetch
 * 2. Cand date generat on
 * 3. F lter ng
 * 4.  nterleave blender
 * 5. Ranker
 * 6. Post-ranker f lter
 * 7. Truncat on
 */
@S ngleton
class CrCand dateGenerator @ nject() (
  s ce nfoRouter: S ce nfoRouter,
  cand dateS ceRouter: Cand dateS cesRouter,
  sw chBlender: Sw chBlender,
  preRankF lterRunner: PreRankF lterRunner,
  postRankF lterRunner: PostRankF lterRunner,
  sw chRanker: Sw chRanker,
  crM xerScr beLogger: CrM xerScr beLogger,
  t  outConf g: T  outConf g,
  globalStats: StatsRece ver) {
  pr vate val t  r: T  r = new JavaT  r(true)

  pr vate val stats: StatsRece ver = globalStats.scope(t .getClass.getCanon calNa )

  pr vate val fetchS cesStats = stats.scope("fetchS ces")
  pr vate val fetchPos  veS cesStats = stats.scope("fetchPos  veS ces")
  pr vate val fetchNegat veS cesStats = stats.scope("fetchNegat veS ces")
  pr vate val fetchCand datesStats = stats.scope("fetchCand dates")
  pr vate val fetchCand datesAfterF lterStats = stats.scope("fetchCand datesAfterF lter")
  pr vate val preRankF lterStats = stats.scope("preRankF lter")
  pr vate val  nterleaveStats = stats.scope(" nterleave")
  pr vate val rankStats = stats.scope("rank")
  pr vate val postRankF lterStats = stats.scope("postRankF lter")
  pr vate val blueVer f edT etStats = stats.scope("blueVer f edT etStats")
  pr vate val blueVer f edT etStatsPerS m lar yEng ne =
    stats.scope("blueVer f edT etStatsPerS m lar yEng ne")

  def get(query: CrCand dateGeneratorQuery): Future[Seq[RankedCand date]] = {
    val allStats = stats.scope("all")
    val perProductStats = stats.scope("perProduct", query.product.toStr ng)
    val perProductBlueVer f edStats =
      blueVer f edT etStats.scope("perProduct", query.product.toStr ng)

    StatsUt l.track emsStats(allStats) {
      trackResultStats(perProductStats) {
        StatsUt l.track emsStats(perProductStats) {
          val result = for {
            (s ceS gnals, s ceGraphsMap) <- StatsUt l.trackBlockStats(fetchS cesStats) {
              fetchS ces(query)
            }
             n  alCand dates <- StatsUt l.trackBlockStats(fetchCand datesAfterF lterStats) {
              // f nd t  pos  ve and negat ve s gnals
              val (pos  veS gnals, negat veS gnals) = s ceS gnals.part  on { s gnal =>
                !EnabledNegat veS ceTypes.conta ns(s gnal.s ceType)
              }
              fetchPos  veS cesStats.stat("s ze").add(pos  veS gnals.s ze)
              fetchNegat veS cesStats.stat("s ze").add(negat veS gnals.s ze)

              // f nd t  pos  ve s gnals to keep, remov ng block and muted users
              val f lteredS ce nfo =
                 f (negat veS gnals.nonEmpty && query.params(
                    RecentNegat veS gnalParams.EnableS ceParam)) {
                  f lterS ce nfo(pos  veS gnals, negat veS gnals)
                } else {
                  pos  veS gnals
                }

              // fetch cand dates from t  pos  ve s gnals
              StatsUt l.trackBlockStats(fetchCand datesStats) {
                fetchCand dates(query, f lteredS ce nfo, s ceGraphsMap)
              }
            }
            f lteredCand dates <- StatsUt l.trackBlockStats(preRankF lterStats) {
              preRankF lter(query,  n  alCand dates)
            }
             nterleavedCand dates <- StatsUt l.track emsStats( nterleaveStats) {
               nterleave(query, f lteredCand dates)
            }
            rankedCand dates <- StatsUt l.track emsStats(rankStats) {
              val cand datesToRank =
                 nterleavedCand dates.take(query.params(RankerParams.MaxCand datesToRank))
              rank(query, cand datesToRank)
            }
            postRankF lterCand dates <- StatsUt l.track emsStats(postRankF lterStats) {
              postRankF lter(query, rankedCand dates)
            }
          } y eld {
            trackTopKStats(
              800,
              postRankF lterCand dates,
               sQueryK = false,
              perProductBlueVer f edStats)
            trackTopKStats(
              400,
              postRankF lterCand dates,
               sQueryK = false,
              perProductBlueVer f edStats)
            trackTopKStats(
              query.maxNumResults,
              postRankF lterCand dates,
               sQueryK = true,
              perProductBlueVer f edStats)

            val (blueVer f edT ets, rema n ngT ets) =
              postRankF lterCand dates.part  on(
                _.t et nfo.hasBlueVer f edAnnotat on.conta ns(true))
            val topKBlueVer f ed = blueVer f edT ets.take(query.maxNumResults)
            val topKRema n ng = rema n ngT ets.take(query.maxNumResults - topKBlueVer f ed.s ze)

            trackBlueVer f edT etStats(topKBlueVer f ed, perProductBlueVer f edStats)

             f (topKBlueVer f ed.nonEmpty && query.params(RankerParams.EnableBlueVer f edTopK)) {
              topKBlueVer f ed ++ topKRema n ng
            } else {
              postRankF lterCand dates
            }
          }
          result.ra seW h n(t  outConf g.serv ceT  out)(t  r)
        }
      }
    }
  }

  pr vate def fetchS ces(
    query: CrCand dateGeneratorQuery
  ): Future[(Set[S ce nfo], Map[Str ng, Opt on[GraphS ce nfo]])] = {
    crM xerScr beLogger.scr beS gnalS ces(
      query,
      s ce nfoRouter
        .get(query.user d, query.product, query.userState, query.params))
  }

  pr vate def f lterS ce nfo(
    pos  veS gnals: Set[S ce nfo],
    negat veS gnals: Set[S ce nfo]
  ): Set[S ce nfo] = {
    val f lterUsers: Set[Long] = negat veS gnals.flatMap {
      case S ce nfo(_,  nternal d.User d(user d), _) => So (user d)
      case _ => None
    }

    pos  veS gnals.f lter {
      case S ce nfo(_,  nternal d.User d(user d), _) => !f lterUsers.conta ns(user d)
      case _ => true
    }
  }

  def fetchCand dates(
    query: CrCand dateGeneratorQuery,
    s ceS gnals: Set[S ce nfo],
    s ceGraphs: Map[Str ng, Opt on[GraphS ce nfo]]
  ): Future[Seq[Seq[ n  alCand date]]] = {
    val  n  alCand dates = cand dateS ceRouter
      .fetchCand dates(
        query.user d,
        s ceS gnals,
        s ceGraphs,
        query.params
      )

     n  alCand dates.map(_.flatten.map { cand date =>
       f (cand date.t et nfo.hasBlueVer f edAnnotat on.conta ns(true)) {
        blueVer f edT etStatsPerS m lar yEng ne
          .scope(query.product.toStr ng).scope(
            cand date.cand dateGenerat on nfo.contr but ngS m lar yEng nes. ad.s m lar yEng neType.toStr ng).counter(
            cand date.t et nfo.author d.toStr ng). ncr()
      }
    })

    crM xerScr beLogger.scr be n  alCand dates(
      query,
       n  alCand dates
    )
  }

  pr vate def preRankF lter(
    query: CrCand dateGeneratorQuery,
    cand dates: Seq[Seq[ n  alCand date]]
  ): Future[Seq[Seq[ n  alCand date]]] = {
    crM xerScr beLogger.scr bePreRankF lterCand dates(
      query,
      preRankF lterRunner
        .runSequent alF lters(query, cand dates))
  }

  pr vate def postRankF lter(
    query: CrCand dateGeneratorQuery,
    cand dates: Seq[RankedCand date]
  ): Future[Seq[RankedCand date]] = {
    postRankF lterRunner.run(query, cand dates)
  }

  pr vate def  nterleave(
    query: CrCand dateGeneratorQuery,
    cand dates: Seq[Seq[ n  alCand date]]
  ): Future[Seq[BlendedCand date]] = {
    crM xerScr beLogger.scr be nterleaveCand dates(
      query,
      sw chBlender
        .blend(query.params, query.userState, cand dates))
  }

  pr vate def rank(
    query: CrCand dateGeneratorQuery,
    cand dates: Seq[BlendedCand date],
  ): Future[Seq[RankedCand date]] = {
    crM xerScr beLogger.scr beRankedCand dates(
      query,
      sw chRanker.rank(query, cand dates)
    )
  }

  pr vate def trackResultStats(
    stats: StatsRece ver
  )(
    fn: => Future[Seq[RankedCand date]]
  ): Future[Seq[RankedCand date]] = {
    fn.onSuccess { cand dates =>
      trackReasonChosenS ceTypeStats(cand dates, stats)
      trackReasonChosenS m lar yEng neStats(cand dates, stats)
      trackPotent alReasonsS ceTypeStats(cand dates, stats)
      trackPotent alReasonsS m lar yEng neStats(cand dates, stats)
    }
  }

  pr vate def trackReasonChosenS ceTypeStats(
    cand dates: Seq[RankedCand date],
    stats: StatsRece ver
  ): Un  = {
    cand dates
      .groupBy(_.reasonChosen.s ce nfoOpt.map(_.s ceType))
      .foreach {
        case (s ceTypeOpt, rankedCands) =>
          val s ceType = s ceTypeOpt.map(_.toStr ng).getOrElse("Requester d") // default
          stats.stat("reasonChosen", "s ceType", s ceType, "s ze").add(rankedCands.s ze)
      }
  }

  pr vate def trackReasonChosenS m lar yEng neStats(
    cand dates: Seq[RankedCand date],
    stats: StatsRece ver
  ): Un  = {
    cand dates
      .groupBy(_.reasonChosen.s m lar yEng ne nfo.s m lar yEng neType)
      .foreach {
        case (se nfoType, rankedCands) =>
          stats
            .stat("reasonChosen", "s m lar yEng ne", se nfoType.toStr ng, "s ze").add(
              rankedCands.s ze)
      }
  }

  pr vate def trackPotent alReasonsS ceTypeStats(
    cand dates: Seq[RankedCand date],
    stats: StatsRece ver
  ): Un  = {
    cand dates
      .flatMap(_.potent alReasons.map(_.s ce nfoOpt.map(_.s ceType)))
      .groupBy(s ce => s ce)
      .foreach {
        case (s ce nfoOpt, seq) =>
          val s ceType = s ce nfoOpt.map(_.toStr ng).getOrElse("Requester d") // default
          stats.stat("potent alReasons", "s ceType", s ceType, "s ze").add(seq.s ze)
      }
  }

  pr vate def trackPotent alReasonsS m lar yEng neStats(
    cand dates: Seq[RankedCand date],
    stats: StatsRece ver
  ): Un  = {
    cand dates
      .flatMap(_.potent alReasons.map(_.s m lar yEng ne nfo.s m lar yEng neType))
      .groupBy(se => se)
      .foreach {
        case (seType, seq) =>
          stats.stat("potent alReasons", "s m lar yEng ne", seType.toStr ng, "s ze").add(seq.s ze)
      }
  }

  pr vate def trackBlueVer f edT etStats(
    cand dates: Seq[RankedCand date],
    statsRece ver: StatsRece ver
  ): Un  = {
    cand dates.foreach { cand date =>
       f (cand date.t et nfo.hasBlueVer f edAnnotat on.conta ns(true)) {
        statsRece ver.counter(cand date.t et nfo.author d.toStr ng). ncr()
        statsRece ver
          .scope(cand date.t et nfo.author d.toStr ng).counter(cand date.t et d.toStr ng). ncr()
      }
    }
  }

  pr vate def trackTopKStats(
    k:  nt,
    t etCand dates: Seq[RankedCand date],
     sQueryK: Boolean,
    statsRece ver: StatsRece ver
  ): Un  = {
    val (topK, beyondK) = t etCand dates.spl At(k)

    val blueVer f ed ds = t etCand dates.collect {
      case cand date  f cand date.t et nfo.hasBlueVer f edAnnotat on.conta ns(true) =>
        cand date.t et nfo.author d
    }.toSet

    blueVer f ed ds.foreach { blueVer f ed d =>
      val numT etsTopK = topK.count(_.t et nfo.author d == blueVer f ed d)
      val numT etsBeyondK = beyondK.count(_.t et nfo.author d == blueVer f ed d)

       f ( sQueryK) {
        statsRece ver.scope(blueVer f ed d.toStr ng).stat(s"topK").add(numT etsTopK)
        statsRece ver
          .scope(blueVer f ed d.toStr ng).stat(s"beyondK").add(numT etsBeyondK)
      } else {
        statsRece ver.scope(blueVer f ed d.toStr ng).stat(s"top$k").add(numT etsTopK)
        statsRece ver
          .scope(blueVer f ed d.toStr ng).stat(s"beyond$k").add(numT etsBeyondK)
      }
    }
  }
}
