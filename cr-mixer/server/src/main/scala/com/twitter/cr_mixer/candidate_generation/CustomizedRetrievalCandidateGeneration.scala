package com.tw ter.cr_m xer.cand date_generat on

 mport com.tw ter.cr_m xer.cand date_generat on.Custom zedRetr evalCand dateGenerat on.Query
 mport com.tw ter.cr_m xer.model.Cand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.model.T etW hCand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.cr_m xer.param.Custom zedRetr evalBasedCand dateGenerat onParams._
 mport com.tw ter.cr_m xer.param.Custom zedRetr evalBasedTwh nParams._
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.cr_m xer.s m lar y_eng ne.D ffus onBasedS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.LookupEng neQuery
 mport com.tw ter.cr_m xer.s m lar y_eng ne.LookupS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Twh nCollabF lterS m lar yEng ne
 mport com.tw ter.cr_m xer.ut l. nterleaveUt l
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateS ce
 mport com.tw ter.fr gate.common.base.Stats
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton
 mport scala.collect on.mutable.ArrayBuffer

/**
 * A cand date generator that fetc s s m lar t ets from mult ple custom zed retr eval based cand date s ces
 *
 * D fferent from [[T etBasedCand dateGenerat on]], t  store returns cand dates from d fferent
 * s m lar y eng nes w hout blend ng.  n ot r words, t  class shall not be thought of as a
 * Un f ed S m lar y Eng ne.    s a CG that calls mult ple s ngular S m lar y Eng nes.
 */
@S ngleton
case class Custom zedRetr evalCand dateGenerat on @ nject() (
  @Na d(ModuleNa s.Twh nCollabF lterS m lar yEng ne)
  twh nCollabF lterS m lar yEng ne: LookupS m lar yEng ne[
    Twh nCollabF lterS m lar yEng ne.Query,
    T etW hScore
  ],
  @Na d(ModuleNa s.D ffus onBasedS m lar yEng ne)
  d ffus onBasedS m lar yEng ne: LookupS m lar yEng ne[
    D ffus onBasedS m lar yEng ne.Query,
    T etW hScore
  ],
  statsRece ver: StatsRece ver)
    extends Cand dateS ce[
      Query,
      Seq[T etW hCand dateGenerat on nfo]
    ] {

  overr de def na : Str ng = t .getClass.getS mpleNa 

  pr vate val stats = statsRece ver.scope(na )
  pr vate val fetchCand datesStat = stats.scope("fetchCand dates")

  /**
   * For each S m lar y Eng ne Model, return a l st of t et cand dates
   */
  overr de def get(
    query: Query
  ): Future[Opt on[Seq[Seq[T etW hCand dateGenerat on nfo]]]] = {
    query. nternal d match {
      case  nternal d.User d(_) =>
        Stats.trackOpt on(fetchCand datesStat) {
          val twh nCollabF lterForFollowCand datesFut =  f (query.enableTwh nCollabF lter) {
            twh nCollabF lterS m lar yEng ne.getCand dates(query.twh nCollabF lterFollowQuery)
          } else Future.None

          val twh nCollabF lterForEngage ntCand datesFut =
             f (query.enableTwh nCollabF lter) {
              twh nCollabF lterS m lar yEng ne.getCand dates(
                query.twh nCollabF lterEngage ntQuery)
            } else Future.None

          val twh nMult ClusterForFollowCand datesFut =  f (query.enableTwh nMult Cluster) {
            twh nCollabF lterS m lar yEng ne.getCand dates(query.twh nMult ClusterFollowQuery)
          } else Future.None

          val twh nMult ClusterForEngage ntCand datesFut =
             f (query.enableTwh nMult Cluster) {
              twh nCollabF lterS m lar yEng ne.getCand dates(
                query.twh nMult ClusterEngage ntQuery)
            } else Future.None

          val d ffus onBasedS m lar yEng neCand datesFut =  f (query.enableRet etBasedD ffus on) {
            d ffus onBasedS m lar yEng ne.getCand dates(query.d ffus onBasedS m lar yEng neQuery)
          } else Future.None

          Future
            .jo n(
              twh nCollabF lterForFollowCand datesFut,
              twh nCollabF lterForEngage ntCand datesFut,
              twh nMult ClusterForFollowCand datesFut,
              twh nMult ClusterForEngage ntCand datesFut,
              d ffus onBasedS m lar yEng neCand datesFut
            ).map {
              case (
                    twh nCollabF lterForFollowCand dates,
                    twh nCollabF lterForEngage ntCand dates,
                    twh nMult ClusterForFollowCand dates,
                    twh nMult ClusterForEngage ntCand dates,
                    d ffus onBasedS m lar yEng neCand dates) =>
                val maxCand dateNumPerS ceKey = 200
                val twh nCollabF lterForFollowW hCG nfo =
                  getTwh nCollabCand datesW hCG nfo(
                    twh nCollabF lterForFollowCand dates,
                    maxCand dateNumPerS ceKey,
                    query.twh nCollabF lterFollowQuery,
                  )
                val twh nCollabF lterForEngage ntW hCG nfo =
                  getTwh nCollabCand datesW hCG nfo(
                    twh nCollabF lterForEngage ntCand dates,
                    maxCand dateNumPerS ceKey,
                    query.twh nCollabF lterEngage ntQuery,
                  )
                val twh nMult ClusterForFollowW hCG nfo =
                  getTwh nCollabCand datesW hCG nfo(
                    twh nMult ClusterForFollowCand dates,
                    maxCand dateNumPerS ceKey,
                    query.twh nMult ClusterFollowQuery,
                  )
                val twh nMult ClusterForEngage ntW hCG nfo =
                  getTwh nCollabCand datesW hCG nfo(
                    twh nMult ClusterForEngage ntCand dates,
                    maxCand dateNumPerS ceKey,
                    query.twh nMult ClusterEngage ntQuery,
                  )
                val ret etBasedD ffus onW hCG nfo =
                  getD ffus onBasedCand datesW hCG nfo(
                    d ffus onBasedS m lar yEng neCand dates,
                    maxCand dateNumPerS ceKey,
                    query.d ffus onBasedS m lar yEng neQuery,
                  )

                val twh nCollabCand dateS cesToBe nterleaved =
                  ArrayBuffer[Seq[T etW hCand dateGenerat on nfo]](
                    twh nCollabF lterForFollowW hCG nfo,
                    twh nCollabF lterForEngage ntW hCG nfo,
                  )

                val twh nMult ClusterCand dateS cesToBe nterleaved =
                  ArrayBuffer[Seq[T etW hCand dateGenerat on nfo]](
                    twh nMult ClusterForFollowW hCG nfo,
                    twh nMult ClusterForEngage ntW hCG nfo,
                  )

                val  nterleavedTwh nCollabCand dates =
                   nterleaveUt l. nterleave(twh nCollabCand dateS cesToBe nterleaved)

                val  nterleavedTwh nMult ClusterCand dates =
                   nterleaveUt l. nterleave(twh nMult ClusterCand dateS cesToBe nterleaved)

                val twh nCollabF lterResults =
                   f ( nterleavedTwh nCollabCand dates.nonEmpty) {
                    So ( nterleavedTwh nCollabCand dates.take(maxCand dateNumPerS ceKey))
                  } else None

                val twh nMult ClusterResults =
                   f ( nterleavedTwh nMult ClusterCand dates.nonEmpty) {
                    So ( nterleavedTwh nMult ClusterCand dates.take(maxCand dateNumPerS ceKey))
                  } else None

                val d ffus onResults =
                   f (ret etBasedD ffus onW hCG nfo.nonEmpty) {
                    So (ret etBasedD ffus onW hCG nfo.take(maxCand dateNumPerS ceKey))
                  } else None

                So (
                  Seq(
                    twh nCollabF lterResults,
                    twh nMult ClusterResults,
                    d ffus onResults
                  ).flatten)
            }
        }
      case _ =>
        throw new  llegalArgu ntExcept on("s ce d_ s_not_user d_cnt")
    }
  }

  /** Returns a l st of t ets that are generated less than `maxT etAgeH s` h s ago */
  pr vate def t etAgeF lter(
    cand dates: Seq[T etW hScore],
    maxT etAgeH s: Durat on
  ): Seq[T etW hScore] = {
    // T et  Ds are approx mately chronolog cal (see http://go/snowflake),
    // so   are bu ld ng t  earl est t et  d once
    // T  per-cand date log c  re t n be cand date.t et d > earl estPerm tedT et d, wh ch  s far c aper.
    val earl estT et d = Snowflake d.f rst dFor(T  .now - maxT etAgeH s)
    cand dates.f lter { cand date => cand date.t et d >= earl estT et d }
  }

  /**
   * AgeF lters t etCand dates w h stats
   * Only age f lter log c  s effect ve  re (through t etAgeF lter). T  funct on acts mostly for  tr c logg ng.
   */
  pr vate def ageF lterW hStats(
    offl ne nterested nCand dates: Seq[T etW hScore],
    maxT etAgeH s: Durat on,
    scopedStatsRece ver: StatsRece ver
  ): Seq[T etW hScore] = {
    scopedStatsRece ver.stat("s ze").add(offl ne nterested nCand dates.s ze)
    val cand dates = offl ne nterested nCand dates.map { cand date =>
      T etW hScore(cand date.t et d, cand date.score)
    }
    val f lteredCand dates = t etAgeF lter(cand dates, maxT etAgeH s)
    scopedStatsRece ver.stat(f"f ltered_s ze").add(f lteredCand dates.s ze)
     f (f lteredCand dates. sEmpty) scopedStatsRece ver.counter(f"empty"). ncr()

    f lteredCand dates
  }

  pr vate def getTwh nCollabCand datesW hCG nfo(
    t etCand dates: Opt on[Seq[T etW hScore]],
    maxCand dateNumPerS ceKey:  nt,
    twh nCollabF lterQuery: LookupEng neQuery[
      Twh nCollabF lterS m lar yEng ne.Query
    ],
  ): Seq[T etW hCand dateGenerat on nfo] = {
    val twh nT ets = t etCand dates match {
      case So (t etsW hScores) =>
        t etsW hScores.map { t etW hScore =>
          T etW hCand dateGenerat on nfo(
            t etW hScore.t et d,
            Cand dateGenerat on nfo(
              None,
              Twh nCollabF lterS m lar yEng ne
                .toS m lar yEng ne nfo(twh nCollabF lterQuery, t etW hScore.score),
              Seq.empty
            )
          )
        }
      case _ => Seq.empty
    }
    twh nT ets.take(maxCand dateNumPerS ceKey)
  }

  pr vate def getD ffus onBasedCand datesW hCG nfo(
    t etCand dates: Opt on[Seq[T etW hScore]],
    maxCand dateNumPerS ceKey:  nt,
    d ffus onBasedS m lar yEng neQuery: LookupEng neQuery[
      D ffus onBasedS m lar yEng ne.Query
    ],
  ): Seq[T etW hCand dateGenerat on nfo] = {
    val d ffus onT ets = t etCand dates match {
      case So (t etsW hScores) =>
        t etsW hScores.map { t etW hScore =>
          T etW hCand dateGenerat on nfo(
            t etW hScore.t et d,
            Cand dateGenerat on nfo(
              None,
              D ffus onBasedS m lar yEng ne
                .toS m lar yEng ne nfo(d ffus onBasedS m lar yEng neQuery, t etW hScore.score),
              Seq.empty
            )
          )
        }
      case _ => Seq.empty
    }
    d ffus onT ets.take(maxCand dateNumPerS ceKey)
  }
}

object Custom zedRetr evalCand dateGenerat on {

  case class Query(
     nternal d:  nternal d,
    maxCand dateNumPerS ceKey:  nt,
    maxT etAgeH s: Durat on,
    // twh nCollabF lter
    enableTwh nCollabF lter: Boolean,
    twh nCollabF lterFollowQuery: LookupEng neQuery[
      Twh nCollabF lterS m lar yEng ne.Query
    ],
    twh nCollabF lterEngage ntQuery: LookupEng neQuery[
      Twh nCollabF lterS m lar yEng ne.Query
    ],
    // twh nMult Cluster
    enableTwh nMult Cluster: Boolean,
    twh nMult ClusterFollowQuery: LookupEng neQuery[
      Twh nCollabF lterS m lar yEng ne.Query
    ],
    twh nMult ClusterEngage ntQuery: LookupEng neQuery[
      Twh nCollabF lterS m lar yEng ne.Query
    ],
    enableRet etBasedD ffus on: Boolean,
    d ffus onBasedS m lar yEng neQuery: LookupEng neQuery[
      D ffus onBasedS m lar yEng ne.Query
    ],
  )

  def fromParams(
     nternal d:  nternal d,
    params: conf gap .Params
  ): Query = {
    val twh nCollabF lterFollowQuery =
      Twh nCollabF lterS m lar yEng ne.fromParams(
         nternal d,
        params(Custom zedRetr evalBasedTwh nCollabF lterFollowS ce),
        params)

    val twh nCollabF lterEngage ntQuery =
      Twh nCollabF lterS m lar yEng ne.fromParams(
         nternal d,
        params(Custom zedRetr evalBasedTwh nCollabF lterEngage ntS ce),
        params)

    val twh nMult ClusterFollowQuery =
      Twh nCollabF lterS m lar yEng ne.fromParams(
         nternal d,
        params(Custom zedRetr evalBasedTwh nMult ClusterFollowS ce),
        params)

    val twh nMult ClusterEngage ntQuery =
      Twh nCollabF lterS m lar yEng ne.fromParams(
         nternal d,
        params(Custom zedRetr evalBasedTwh nMult ClusterEngage ntS ce),
        params)

    val d ffus onBasedS m lar yEng neQuery =
      D ffus onBasedS m lar yEng ne.fromParams(
         nternal d,
        params(Custom zedRetr evalBasedRet etD ffus onS ce),
        params)

    Query(
       nternal d =  nternal d,
      maxCand dateNumPerS ceKey = params(GlobalParams.MaxCand dateNumPerS ceKeyParam),
      maxT etAgeH s = params(GlobalParams.MaxT etAgeH sParam),
      // twh nCollabF lter
      enableTwh nCollabF lter = params(EnableTwh nCollabF lterClusterParam),
      twh nCollabF lterFollowQuery = twh nCollabF lterFollowQuery,
      twh nCollabF lterEngage ntQuery = twh nCollabF lterEngage ntQuery,
      enableTwh nMult Cluster = params(EnableTwh nMult ClusterParam),
      twh nMult ClusterFollowQuery = twh nMult ClusterFollowQuery,
      twh nMult ClusterEngage ntQuery = twh nMult ClusterEngage ntQuery,
      enableRet etBasedD ffus on = params(EnableRet etBasedD ffus onParam),
      d ffus onBasedS m lar yEng neQuery = d ffus onBasedS m lar yEng neQuery
    )
  }
}
