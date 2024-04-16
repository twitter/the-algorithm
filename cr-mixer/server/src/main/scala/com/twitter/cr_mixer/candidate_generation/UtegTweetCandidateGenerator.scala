package com.tw ter.cr_m xer.cand date_generat on

 mport com.tw ter.contentrecom nder.thr ftscala.T et nfo
 mport com.tw ter.cr_m xer.logg ng.UtegT etScr beLogger
 mport com.tw ter.cr_m xer.f lter.UtegF lterRunner
 mport com.tw ter.cr_m xer.model.Cand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.model.RankedCand date
 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.T etW hScoreAndSoc alProof
 mport com.tw ter.cr_m xer.model.UtegT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.s m lar y_eng ne.UserT etEnt yGraphS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.StandardS m lar yEng ne
 mport com.tw ter.cr_m xer.s ce_s gnal.RealGraph nS ceGraphFetc r
 mport com.tw ter.cr_m xer.s ce_s gnal.S ceFetc r.Fetc rQuery
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

@S ngleton
class UtegT etCand dateGenerator @ nject() (
  @Na d(ModuleNa s.UserT etEnt yGraphS m lar yEng ne) userT etEnt yGraphS m lar yEng ne: StandardS m lar yEng ne[
    UserT etEnt yGraphS m lar yEng ne.Query,
    T etW hScoreAndSoc alProof
  ],
  utegT etScr beLogger: UtegT etScr beLogger,
  t et nfoStore: ReadableStore[T et d, T et nfo],
  realGraph nS ceGraphFetc r: RealGraph nS ceGraphFetc r,
  utegF lterRunner: UtegF lterRunner,
  globalStats: StatsRece ver) {

  pr vate val stats: StatsRece ver = globalStats.scope(t .getClass.getCanon calNa )
  pr vate val fetchSeedsStats = stats.scope("fetchSeeds")
  pr vate val fetchCand datesStats = stats.scope("fetchCand dates")
  pr vate val utegF lterStats = stats.scope("utegF lter")
  pr vate val rankStats = stats.scope("rank")

  def get(
    query: UtegT etCand dateGeneratorQuery
  ): Future[Seq[T etW hScoreAndSoc alProof]] = {

    val allStats = stats.scope("all")
    val perProductStats = stats.scope("perProduct", query.product.toStr ng)
    StatsUt l.track emsStats(allStats) {
      StatsUt l.track emsStats(perProductStats) {

        /**
         * T  cand date   return  n t  end needs a soc al proof f eld, wh ch  sn't
         * supported by t  any ex st ng Cand date type, so   created T etW hScoreAndSoc alProof
         *  nstead.
         *
         * Ho ver, f lters and l ght ranker expect Cand date-typed param to work.  n order to m n m se t 
         * changes to t m,   are do ng convers ons from/to T etW hScoreAndSoc alProof to/from Cand date
         *  n t   thod.
         */
        for {
          realGraphSeeds <- StatsUt l.track emMapStats(fetchSeedsStats) {
            fetchSeeds(query)
          }
           n  alT ets <- StatsUt l.track emsStats(fetchCand datesStats) {
            fetchCand dates(query, realGraphSeeds)
          }
           n  alCand dates <- convertTo n  alCand dates( n  alT ets)
          f lteredCand dates <- StatsUt l.track emsStats(utegF lterStats) {
            utegF lter(query,  n  alCand dates)
          }
          rankedCand dates <- StatsUt l.track emsStats(rankStats) {
            rankCand dates(query, f lteredCand dates)
          }
        } y eld {
          val topT ets = rankedCand dates.take(query.maxNumResults)
          convertToT ets(topT ets,  n  alT ets.map(t et => t et.t et d -> t et).toMap)
        }
      }
    }
  }

  pr vate def utegF lter(
    query: UtegT etCand dateGeneratorQuery,
    cand dates: Seq[ n  alCand date]
  ): Future[Seq[ n  alCand date]] = {
    utegF lterRunner.runSequent alF lters(query, Seq(cand dates)).map(_.flatten)
  }

  pr vate def fetchSeeds(
    query: UtegT etCand dateGeneratorQuery
  ): Future[Map[User d, Double]] = {
    realGraph nS ceGraphFetc r
      .get(Fetc rQuery(query.user d, query.product, query.userState, query.params))
      .map(_.map(_.seedW hScores).getOrElse(Map.empty))
  }

  pr vate[cand date_generat on] def rankCand dates(
    query: UtegT etCand dateGeneratorQuery,
    f lteredCand dates: Seq[ n  alCand date],
  ): Future[Seq[RankedCand date]] = {
    val blendedCand dates = f lteredCand dates.map(cand date =>
      cand date.toBlendedCand date(Seq(cand date.cand dateGenerat on nfo)))

    Future(
      blendedCand dates.map { cand date =>
        val score = cand date.getS m lar yScore
        cand date.toRankedCand date(score)
      }
    )

  }

  def fetchCand dates(
    query: UtegT etCand dateGeneratorQuery,
    realGraphSeeds: Map[User d, Double],
  ): Future[Seq[T etW hScoreAndSoc alProof]] = {
    val eng neQuery = UserT etEnt yGraphS m lar yEng ne.fromParams(
      query.user d,
      realGraphSeeds,
      So (query. mpressedT etL st.toSeq),
      query.params
    )

    utegT etScr beLogger.scr be n  alCand dates(
      query,
      userT etEnt yGraphS m lar yEng ne.getCand dates(eng neQuery).map(_.toSeq.flatten)
    )
  }

  pr vate[cand date_generat on] def convertTo n  alCand dates(
    cand dates: Seq[T etW hScoreAndSoc alProof],
  ): Future[Seq[ n  alCand date]] = {
    val t et ds = cand dates.map(_.t et d).toSet
    Future.collect(t et nfoStore.mult Get(t et ds)).map { t et nfos =>
      /** *
       *  f t et nfo does not ex st,   w ll f lter out t  t et cand date.
       */
      cand dates.collect {
        case cand date  f t et nfos.getOrElse(cand date.t et d, None). sDef ned =>
          val t et nfo = t et nfos(cand date.t et d)
            .getOrElse(throw new  llegalStateExcept on("C ck prev ous l ne's cond  on"))

           n  alCand date(
            t et d = cand date.t et d,
            t et nfo = t et nfo,
            Cand dateGenerat on nfo(
              None,
              S m lar yEng ne nfo(
                s m lar yEng neType = S m lar yEng neType.Uteg,
                model d = None,
                score = So (cand date.score)),
              Seq.empty
            )
          )
      }
    }
  }

  pr vate[cand date_generat on] def convertToT ets(
    cand dates: Seq[RankedCand date],
    t etMap: Map[T et d, T etW hScoreAndSoc alProof]
  ): Seq[T etW hScoreAndSoc alProof] = {
    cand dates.map { cand date =>
      t etMap
        .get(cand date.t et d).map { t et =>
          T etW hScoreAndSoc alProof(
            t et.t et d,
            cand date.pred ct onScore,
            t et.soc alProofByType
          )
        // T  except on should never be thrown
        }.getOrElse(throw new Except on("Cannot f nd ranked cand date  n or g nal UTEG t ets"))
    }
  }
}
