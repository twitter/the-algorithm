package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.model.Cand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.S ce nfo
 mport com.tw ter.cr_m xer.model.T etW hCand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.cr_m xer.param.RelatedT etT etBasedParams
 mport com.tw ter.cr_m xer.param.RelatedV deoT etT etBasedParams
 mport com.tw ter.cr_m xer.param.S mClustersANNParams
 mport com.tw ter.cr_m xer.param.T etBasedCand dateGenerat onParams
 mport com.tw ter.cr_m xer.param.T etBasedTwH NParams
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.cr_m xer.thr ftscala.S ceType
 mport com.tw ter.cr_m xer.ut l. nterleaveUt l
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport javax. nject.Na d
 mport javax. nject.S ngleton
 mport scala.collect on.mutable.ArrayBuffer

/**
 * T  store fetc s s m lar t ets from mult ple t et based cand date s ces
 * and comb nes t m us ng d fferent  thods obta ned from query params
 */
@S ngleton
case class T etBasedUn f edS m lar yEng ne(
  @Na d(ModuleNa s.T etBasedUserT etGraphS m lar yEng ne)
  t etBasedUserT etGraphS m lar yEng ne: StandardS m lar yEng ne[
    T etBasedUserT etGraphS m lar yEng ne.Query,
    T etW hScore
  ],
  @Na d(ModuleNa s.T etBasedUserV deoGraphS m lar yEng ne)
  t etBasedUserV deoGraphS m lar yEng ne: StandardS m lar yEng ne[
    T etBasedUserV deoGraphS m lar yEng ne.Query,
    T etW hScore
  ],
  s mClustersANNS m lar yEng ne: StandardS m lar yEng ne[
    S mClustersANNS m lar yEng ne.Query,
    T etW hScore
  ],
  @Na d(ModuleNa s.T etBasedQ gS m lar yEng ne)
  t etBasedQ gS m larT etsS m lar yEng ne: StandardS m lar yEng ne[
    T etBasedQ gS m lar yEng ne.Query,
    T etW hScore
  ],
  @Na d(ModuleNa s.T etBasedTwH NANNS m lar yEng ne)
  t etBasedTwH NANNS m lar yEng ne: HnswANNS m lar yEng ne,
  statsRece ver: StatsRece ver)
    extends ReadableStore[
      T etBasedUn f edS m lar yEng ne.Query,
      Seq[T etW hCand dateGenerat on nfo]
    ] {

   mport T etBasedUn f edS m lar yEng ne._
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val fetchCand datesStat = stats.scope("fetchCand dates")

  overr de def get(
    query: Query
  ): Future[Opt on[Seq[T etW hCand dateGenerat on nfo]]] = {

    query.s ce nfo. nternal d match {
      case _:  nternal d.T et d =>
        StatsUt l.trackOpt on emsStats(fetchCand datesStat) {
          val twh nQuery =
            HnswANNEng neQuery(
              s ce d = query.s ce nfo. nternal d,
              model d = query.twh nModel d,
              params = query.params)
          val utgCand datesFut =
             f (query.enableUtg)
              t etBasedUserT etGraphS m lar yEng ne.getCand dates(query.utgQuery)
            else Future.None

          val uvgCand datesFut =
             f (query.enableUvg)
              t etBasedUserV deoGraphS m lar yEng ne.getCand dates(query.uvgQuery)
            else Future.None

          val sannCand datesFut =  f (query.enableS mClustersANN) {
            s mClustersANNS m lar yEng ne.getCand dates(query.s mClustersANNQuery)
          } else Future.None

          val sann1Cand datesFut =
             f (query.enableS mClustersANN1) {
              s mClustersANNS m lar yEng ne.getCand dates(query.s mClustersANN1Query)
            } else Future.None

          val sann2Cand datesFut =
             f (query.enableS mClustersANN2) {
              s mClustersANNS m lar yEng ne.getCand dates(query.s mClustersANN2Query)
            } else Future.None

          val sann3Cand datesFut =
             f (query.enableS mClustersANN3) {
              s mClustersANNS m lar yEng ne.getCand dates(query.s mClustersANN3Query)
            } else Future.None

          val sann5Cand datesFut =
             f (query.enableS mClustersANN5) {
              s mClustersANNS m lar yEng ne.getCand dates(query.s mClustersANN5Query)
            } else Future.None

          val sann4Cand datesFut =
             f (query.enableS mClustersANN4) {
              s mClustersANNS m lar yEng ne.getCand dates(query.s mClustersANN4Query)
            } else Future.None

          val exper  ntalSANNCand datesFut =
             f (query.enableExper  ntalS mClustersANN) {
              s mClustersANNS m lar yEng ne.getCand dates(query.exper  ntalS mClustersANNQuery)
            } else Future.None

          val q gCand datesFut =
             f (query.enableQ g)
              t etBasedQ gS m larT etsS m lar yEng ne.getCand dates(query.q gQuery)
            else Future.None

          val twH NCand dateFut =  f (query.enableTwH N) {
            t etBasedTwH NANNS m lar yEng ne.getCand dates(twh nQuery)
          } else Future.None

          Future
            .jo n(
              utgCand datesFut,
              sannCand datesFut,
              sann1Cand datesFut,
              sann2Cand datesFut,
              sann3Cand datesFut,
              sann5Cand datesFut,
              sann4Cand datesFut,
              exper  ntalSANNCand datesFut,
              q gCand datesFut,
              twH NCand dateFut,
              uvgCand datesFut
            ).map {
              case (
                    userT etGraphCand dates,
                    s mClustersANNCand dates,
                    s mClustersANN1Cand dates,
                    s mClustersANN2Cand dates,
                    s mClustersANN3Cand dates,
                    s mClustersANN5Cand dates,
                    s mClustersANN4Cand dates,
                    exper  ntalSANNCand dates,
                    q gS m larT etsCand dates,
                    twh nCand dates,
                    userV deoGraphCand dates) =>
                val f lteredUTGT ets =
                  userT etGraphF lter(userT etGraphCand dates.toSeq.flatten)
                val f lteredUVGT ets =
                  userV deoGraphF lter(userV deoGraphCand dates.toSeq.flatten)
                val f lteredSANNT ets = s mClustersCand dateM nScoreF lter(
                  s mClustersANNCand dates.toSeq.flatten,
                  query.s mClustersM nScore,
                  query.s mClustersANNQuery.storeQuery.s mClustersANNConf g d)

                val f lteredSANN1T ets = s mClustersCand dateM nScoreF lter(
                  s mClustersANN1Cand dates.toSeq.flatten,
                  query.s mClustersM nScore,
                  query.s mClustersANN1Query.storeQuery.s mClustersANNConf g d)

                val f lteredSANN2T ets = s mClustersCand dateM nScoreF lter(
                  s mClustersANN2Cand dates.toSeq.flatten,
                  query.s mClustersM nScore,
                  query.s mClustersANN2Query.storeQuery.s mClustersANNConf g d)

                val f lteredSANN3T ets = s mClustersCand dateM nScoreF lter(
                  s mClustersANN3Cand dates.toSeq.flatten,
                  query.s mClustersM nScore,
                  query.s mClustersANN3Query.storeQuery.s mClustersANNConf g d)

                val f lteredSANN4T ets = s mClustersCand dateM nScoreF lter(
                  s mClustersANN4Cand dates.toSeq.flatten,
                  query.s mClustersM nScore,
                  query.s mClustersANN4Query.storeQuery.s mClustersANNConf g d)

                val f lteredSANN5T ets = s mClustersCand dateM nScoreF lter(
                  s mClustersANN5Cand dates.toSeq.flatten,
                  query.s mClustersM nScore,
                  query.s mClustersANN5Query.storeQuery.s mClustersANNConf g d)

                val f lteredExper  ntalSANNT ets = s mClustersCand dateM nScoreF lter(
                  exper  ntalSANNCand dates.toSeq.flatten,
                  query.s mClustersV deoBasedM nScore,
                  query.exper  ntalS mClustersANNQuery.storeQuery.s mClustersANNConf g d)

                val f lteredQ gT ets = q gS m larT etsF lter(
                  q gS m larT etsCand dates.toSeq.flatten,
                  query.q gMaxT etAgeH s,
                  query.q gMaxNumS m larT ets
                )

                val f lteredTwH NT ets = twh nF lter(
                  twh nCand dates.toSeq.flatten.sortBy(-_.score),
                  query.twh nMaxT etAgeH s,
                  t etBasedTwH NANNS m lar yEng ne.getScopedStats
                )
                val utgT etsW hCG nfo = f lteredUTGT ets.map { t etW hScore =>
                  val s m lar yEng ne nfo = T etBasedUserT etGraphS m lar yEng ne
                    .toS m lar yEng ne nfo(t etW hScore.score)
                  T etW hCand dateGenerat on nfo(
                    t etW hScore.t et d,
                    Cand dateGenerat on nfo(
                      So (query.s ce nfo),
                      s m lar yEng ne nfo,
                      Seq(s m lar yEng ne nfo)
                    ))
                }

                val uvgT etsW hCG nfo = f lteredUVGT ets.map { t etW hScore =>
                  val s m lar yEng ne nfo = T etBasedUserV deoGraphS m lar yEng ne
                    .toS m lar yEng ne nfo(t etW hScore.score)
                  T etW hCand dateGenerat on nfo(
                    t etW hScore.t et d,
                    Cand dateGenerat on nfo(
                      So (query.s ce nfo),
                      s m lar yEng ne nfo,
                      Seq(s m lar yEng ne nfo)
                    ))
                }
                val sannT etsW hCG nfo = f lteredSANNT ets.map { t etW hScore =>
                  val s m lar yEng ne nfo = S mClustersANNS m lar yEng ne
                    .toS m lar yEng ne nfo(query.s mClustersANNQuery, t etW hScore.score)
                  T etW hCand dateGenerat on nfo(
                    t etW hScore.t et d,
                    Cand dateGenerat on nfo(
                      So (query.s ce nfo),
                      s m lar yEng ne nfo,
                      Seq(s m lar yEng ne nfo)
                    ))
                }
                val sann1T etsW hCG nfo = f lteredSANN1T ets.map { t etW hScore =>
                  val s m lar yEng ne nfo = S mClustersANNS m lar yEng ne
                    .toS m lar yEng ne nfo(query.s mClustersANN1Query, t etW hScore.score)
                  T etW hCand dateGenerat on nfo(
                    t etW hScore.t et d,
                    Cand dateGenerat on nfo(
                      So (query.s ce nfo),
                      s m lar yEng ne nfo,
                      Seq(s m lar yEng ne nfo)
                    ))
                }
                val sann2T etsW hCG nfo = f lteredSANN2T ets.map { t etW hScore =>
                  val s m lar yEng ne nfo = S mClustersANNS m lar yEng ne
                    .toS m lar yEng ne nfo(query.s mClustersANN2Query, t etW hScore.score)
                  T etW hCand dateGenerat on nfo(
                    t etW hScore.t et d,
                    Cand dateGenerat on nfo(
                      So (query.s ce nfo),
                      s m lar yEng ne nfo,
                      Seq(s m lar yEng ne nfo)
                    ))
                }
                val sann3T etsW hCG nfo = f lteredSANN3T ets.map { t etW hScore =>
                  val s m lar yEng ne nfo = S mClustersANNS m lar yEng ne
                    .toS m lar yEng ne nfo(query.s mClustersANN3Query, t etW hScore.score)
                  T etW hCand dateGenerat on nfo(
                    t etW hScore.t et d,
                    Cand dateGenerat on nfo(
                      So (query.s ce nfo),
                      s m lar yEng ne nfo,
                      Seq(s m lar yEng ne nfo)
                    ))
                }
                val sann4T etsW hCG nfo = f lteredSANN4T ets.map { t etW hScore =>
                  val s m lar yEng ne nfo = S mClustersANNS m lar yEng ne
                    .toS m lar yEng ne nfo(query.s mClustersANN4Query, t etW hScore.score)
                  T etW hCand dateGenerat on nfo(
                    t etW hScore.t et d,
                    Cand dateGenerat on nfo(
                      So (query.s ce nfo),
                      s m lar yEng ne nfo,
                      Seq(s m lar yEng ne nfo)
                    ))
                }
                val sann5T etsW hCG nfo = f lteredSANN5T ets.map { t etW hScore =>
                  val s m lar yEng ne nfo = S mClustersANNS m lar yEng ne
                    .toS m lar yEng ne nfo(query.s mClustersANN5Query, t etW hScore.score)
                  T etW hCand dateGenerat on nfo(
                    t etW hScore.t et d,
                    Cand dateGenerat on nfo(
                      So (query.s ce nfo),
                      s m lar yEng ne nfo,
                      Seq(s m lar yEng ne nfo)
                    ))
                }

                val exper  ntalSANNT etsW hCG nfo = f lteredExper  ntalSANNT ets.map {
                  t etW hScore =>
                    val s m lar yEng ne nfo = S mClustersANNS m lar yEng ne
                      .toS m lar yEng ne nfo(
                        query.exper  ntalS mClustersANNQuery,
                        t etW hScore.score)
                    T etW hCand dateGenerat on nfo(
                      t etW hScore.t et d,
                      Cand dateGenerat on nfo(
                        So (query.s ce nfo),
                        s m lar yEng ne nfo,
                        Seq(s m lar yEng ne nfo)
                      ))
                }
                val q gT etsW hCG nfo = f lteredQ gT ets.map { t etW hScore =>
                  val s m lar yEng ne nfo = T etBasedQ gS m lar yEng ne
                    .toS m lar yEng ne nfo(t etW hScore.score)
                  T etW hCand dateGenerat on nfo(
                    t etW hScore.t et d,
                    Cand dateGenerat on nfo(
                      So (query.s ce nfo),
                      s m lar yEng ne nfo,
                      Seq(s m lar yEng ne nfo)
                    ))
                }

                val twH NT etsW hCG nfo = f lteredTwH NT ets.map { t etW hScore =>
                  val s m lar yEng ne nfo = t etBasedTwH NANNS m lar yEng ne
                    .toS m lar yEng ne nfo(twh nQuery, t etW hScore.score)
                  T etW hCand dateGenerat on nfo(
                    t etW hScore.t et d,
                    Cand dateGenerat on nfo(
                      So (query.s ce nfo),
                      s m lar yEng ne nfo,
                      Seq(s m lar yEng ne nfo)
                    ))
                }

                val cand dateS cesToBe nterleaved =
                  ArrayBuffer[Seq[T etW hCand dateGenerat on nfo]](
                    sannT etsW hCG nfo,
                    exper  ntalSANNT etsW hCG nfo,
                    sann1T etsW hCG nfo,
                    sann2T etsW hCG nfo,
                    sann3T etsW hCG nfo,
                    sann5T etsW hCG nfo,
                    sann4T etsW hCG nfo,
                    q gT etsW hCG nfo,
                    uvgT etsW hCG nfo,
                    utgT etsW hCG nfo,
                    twH NT etsW hCG nfo
                  )

                val  nterleavedCand dates =
                   nterleaveUt l. nterleave(cand dateS cesToBe nterleaved)

                val un f edCand datesW hUn f edCG nfo =
                   nterleavedCand dates.map { cand date =>
                    /***
                     * w n a cand date was made by  nterleave/keepG venOrder,
                     * t n   apply getT etBasedUn f edCG nfo() to overr de w h t  un f ed CG nfo
                     *
                     *  'll not have ALL SEs that generated t  t et
                     *  n contr but ngSE l st for  nterleave.   only have t  chosen SE ava lable.
                     */
                    T etW hCand dateGenerat on nfo(
                      t et d = cand date.t et d,
                      cand dateGenerat on nfo = getT etBasedUn f edCG nfo(
                        cand date.cand dateGenerat on nfo.s ce nfoOpt,
                        cand date.getS m lar yScore,
                        cand date.cand dateGenerat on nfo.contr but ngS m lar yEng nes
                      ) // getS m lar yScore co s from e  r un f edScore or s ngle score
                    )
                  }
                stats
                  .stat("un f ed_cand date_s ze").add(un f edCand datesW hUn f edCG nfo.s ze)

                val truncatedCand dates =
                  un f edCand datesW hUn f edCG nfo.take(query.maxCand dateNumPerS ceKey)
                stats.stat("truncatedCand dates_s ze").add(truncatedCand dates.s ze)

                So (truncatedCand dates)
            }
        }

      case _ =>
        stats.counter("s ce d_ s_not_t et d_cnt"). ncr()
        Future.None
    }
  }

  pr vate def s mClustersCand dateM nScoreF lter(
    s mClustersAnnCand dates: Seq[T etW hScore],
    s mClustersM nScore: Double,
    s mClustersANNConf g d: Str ng
  ): Seq[T etW hScore] = {
    val f lteredCand dates = s mClustersAnnCand dates
      .f lter { cand date =>
        cand date.score > s mClustersM nScore
      }

    stats.stat(s mClustersANNConf g d, "s mClustersAnnCand dates_s ze").add(f lteredCand dates.s ze)
    stats.counter(s mClustersANNConf g d, "s mClustersAnnRequests"). ncr()
     f (f lteredCand dates. sEmpty)
      stats.counter(s mClustersANNConf g d, "emptyF lteredS mClustersAnnCand dates"). ncr()

    f lteredCand dates.map { cand date =>
      T etW hScore(cand date.t et d, cand date.score)
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

  pr vate def twh nF lter(
    twh nCand dates: Seq[T etW hScore],
    twh nMaxT etAgeH s: Durat on,
    s mEng neStats: StatsRece ver
  ): Seq[T etW hScore] = {
    s mEng neStats.stat("twh nCand dates_s ze").add(twh nCand dates.s ze)
    val cand dates = twh nCand dates.map { cand date =>
      T etW hScore(cand date.t et d, cand date.score)
    }

    val f lteredCand dates = t etAgeF lter(cand dates, twh nMaxT etAgeH s)
    s mEng neStats.stat("f lteredTwh nCand dates_s ze").add(f lteredCand dates.s ze)
     f (f lteredCand dates. sEmpty) s mEng neStats.counter("emptyF lteredTwh nCand dates"). ncr()

    f lteredCand dates
  }

  /** A no-op f lter as UTG f lter ng already happens on UTG serv ce s de */
  pr vate def userT etGraphF lter(
    userT etGraphCand dates: Seq[T etW hScore]
  ): Seq[T etW hScore] = {
    val f lteredCand dates = userT etGraphCand dates

    stats.stat("userT etGraphCand dates_s ze").add(userT etGraphCand dates.s ze)
     f (f lteredCand dates. sEmpty) stats.counter("emptyF lteredUserT etGraphCand dates"). ncr()

    f lteredCand dates.map { cand date =>
      T etW hScore(cand date.t et d, cand date.score)
    }
  }

  /** A no-op f lter as UVG f lter ng already happens on UVG serv ce s de */
  pr vate def userV deoGraphF lter(
    userV deoGraphCand dates: Seq[T etW hScore]
  ): Seq[T etW hScore] = {
    val f lteredCand dates = userV deoGraphCand dates

    stats.stat("userV deoGraphCand dates_s ze").add(userV deoGraphCand dates.s ze)
     f (f lteredCand dates. sEmpty) stats.counter("emptyF lteredUserV deoGraphCand dates"). ncr()

    f lteredCand dates.map { cand date =>
      T etW hScore(cand date.t et d, cand date.score)
    }
  }
  pr vate def q gS m larT etsF lter(
    q gS m larT etsCand dates: Seq[T etW hScore],
    q gMaxT etAgeH s: Durat on,
    q gMaxNumS m larT ets:  nt
  ): Seq[T etW hScore] = {
    val ageF lteredCand dates = t etAgeF lter(q gS m larT etsCand dates, q gMaxT etAgeH s)
    stats.stat("ageF lteredQ gS m larT etsCand dates_s ze").add(ageF lteredCand dates.s ze)

    val f lteredCand dates = ageF lteredCand dates.take(q gMaxNumS m larT ets)
     f (f lteredCand dates. sEmpty) stats.counter("emptyF lteredQ gS m larT etsCand dates"). ncr()

    f lteredCand dates
  }

  /***
   * Every cand date w ll have t  CG  nfo w h T etBasedUn f edS m lar yEng ne
   * as t y are generated by a compos e of S m lar y Eng nes.
   * Add  onally,   store t  contr but ng SEs (eg., SANN, UTG).
   */
  pr vate def getT etBasedUn f edCG nfo(
    s ce nfoOpt: Opt on[S ce nfo],
    un f edScore: Double,
    contr but ngS m lar yEng nes: Seq[S m lar yEng ne nfo]
  ): Cand dateGenerat on nfo = {
    Cand dateGenerat on nfo(
      s ce nfoOpt,
      S m lar yEng ne nfo(
        s m lar yEng neType = S m lar yEng neType.T etBasedUn f edS m lar yEng ne,
        model d = None, //   do not ass gn model d for a un f ed s m lar y eng ne
        score = So (un f edScore)
      ),
      contr but ngS m lar yEng nes
    )
  }
}

object T etBasedUn f edS m lar yEng ne {

  case class Query(
    s ce nfo: S ce nfo,
    maxCand dateNumPerS ceKey:  nt,
    enableS mClustersANN: Boolean,
    s mClustersANNQuery: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    enableExper  ntalS mClustersANN: Boolean,
    exper  ntalS mClustersANNQuery: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    enableS mClustersANN1: Boolean,
    s mClustersANN1Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    enableS mClustersANN2: Boolean,
    s mClustersANN2Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    enableS mClustersANN3: Boolean,
    s mClustersANN3Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    enableS mClustersANN5: Boolean,
    s mClustersANN5Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    enableS mClustersANN4: Boolean,
    s mClustersANN4Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    s mClustersM nScore: Double,
    s mClustersV deoBasedM nScore: Double,
    twh nModel d: Str ng,
    enableTwH N: Boolean,
    twh nMaxT etAgeH s: Durat on,
    q gMaxT etAgeH s: Durat on,
    q gMaxNumS m larT ets:  nt,
    enableUtg: Boolean,
    utgQuery: Eng neQuery[T etBasedUserT etGraphS m lar yEng ne.Query],
    enableUvg: Boolean,
    uvgQuery: Eng neQuery[T etBasedUserV deoGraphS m lar yEng ne.Query],
    enableQ g: Boolean,
    q gQuery: Eng neQuery[T etBasedQ gS m lar yEng ne.Query],
    params: conf gap .Params)

  def fromParams(
    s ce nfo: S ce nfo,
    params: conf gap .Params,
  ): Eng neQuery[Query] = {
    // S mClusters
    val enableS mClustersANN =
      params(T etBasedCand dateGenerat onParams.EnableS mClustersANNParam)

    val s mClustersModelVers on =
      ModelVers ons.Enum.enumToS mClustersModelVers onMap(params(GlobalParams.ModelVers onParam))
    val s mClustersM nScore = params(T etBasedCand dateGenerat onParams.S mClustersM nScoreParam)
    val s mClustersV deoBasedM nScore = params(
      T etBasedCand dateGenerat onParams.S mClustersV deoBasedM nScoreParam)
    val s mClustersANNConf g d = params(S mClustersANNParams.S mClustersANNConf g d)
    // S mClusters - Exper  ntal SANN S m lar y Eng ne (V deo based SE)
    val enableExper  ntalS mClustersANN =
      params(T etBasedCand dateGenerat onParams.EnableExper  ntalS mClustersANNParam)

    val exper  ntalS mClustersANNConf g d = params(
      S mClustersANNParams.Exper  ntalS mClustersANNConf g d)
    // S mClusters - SANN cluster 1 S m lar y Eng ne
    val enableS mClustersANN1 =
      params(T etBasedCand dateGenerat onParams.EnableS mClustersANN1Param)

    val s mClustersANN1Conf g d = params(S mClustersANNParams.S mClustersANN1Conf g d)
    // S mClusters - SANN cluster 2 S m lar y Eng ne
    val enableS mClustersANN2 =
      params(T etBasedCand dateGenerat onParams.EnableS mClustersANN2Param)
    val s mClustersANN2Conf g d = params(S mClustersANNParams.S mClustersANN2Conf g d)
    // S mClusters - SANN cluster 3 S m lar y Eng ne
    val enableS mClustersANN3 =
      params(T etBasedCand dateGenerat onParams.EnableS mClustersANN3Param)
    val s mClustersANN3Conf g d = params(S mClustersANNParams.S mClustersANN3Conf g d)
    // S mClusters - SANN cluster 5 S m lar y Eng ne
    val enableS mClustersANN5 =
      params(T etBasedCand dateGenerat onParams.EnableS mClustersANN5Param)
    val s mClustersANN5Conf g d = params(S mClustersANNParams.S mClustersANN5Conf g d)
    // S mClusters - SANN cluster 4 S m lar y Eng ne
    val enableS mClustersANN4 =
      params(T etBasedCand dateGenerat onParams.EnableS mClustersANN4Param)
    val s mClustersANN4Conf g d = params(S mClustersANNParams.S mClustersANN4Conf g d)
    // S mClusters ANN Quer es for d fferent SANN clusters
    val s mClustersANNQuery = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANNConf g d,
      params
    )
    val exper  ntalS mClustersANNQuery = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      exper  ntalS mClustersANNConf g d,
      params
    )
    val s mClustersANN1Query = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN1Conf g d,
      params
    )
    val s mClustersANN2Query = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN2Conf g d,
      params
    )
    val s mClustersANN3Query = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN3Conf g d,
      params
    )
    val s mClustersANN5Query = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN5Conf g d,
      params
    )
    val s mClustersANN4Query = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN4Conf g d,
      params
    )
    // T etBasedCand dateGenerat on
    val maxCand dateNumPerS ceKey = params(GlobalParams.MaxCand dateNumPerS ceKeyParam)
    // TwH N
    val twh nModel d = params(T etBasedTwH NParams.Model dParam)
    val enableTwH N =
      params(T etBasedCand dateGenerat onParams.EnableTwH NParam)

    val twh nMaxT etAgeH s = params(GlobalParams.MaxT etAgeH sParam)

    // Q G
    val enableQ g =
      params(T etBasedCand dateGenerat onParams.EnableQ gS m larT etsParam)
    val q gMaxT etAgeH s = params(GlobalParams.MaxT etAgeH sParam)
    val q gMaxNumS m larT ets = params(
      T etBasedCand dateGenerat onParams.Q gMaxNumS m larT etsParam)

    // UTG
    val enableUtg =
      params(T etBasedCand dateGenerat onParams.EnableUTGParam)
    // UVG
    val enableUvg =
      params(T etBasedCand dateGenerat onParams.EnableUVGParam)
    Eng neQuery(
      Query(
        s ce nfo = s ce nfo,
        maxCand dateNumPerS ceKey = maxCand dateNumPerS ceKey,
        enableS mClustersANN = enableS mClustersANN,
        s mClustersANNQuery = s mClustersANNQuery,
        enableExper  ntalS mClustersANN = enableExper  ntalS mClustersANN,
        exper  ntalS mClustersANNQuery = exper  ntalS mClustersANNQuery,
        enableS mClustersANN1 = enableS mClustersANN1,
        s mClustersANN1Query = s mClustersANN1Query,
        enableS mClustersANN2 = enableS mClustersANN2,
        s mClustersANN2Query = s mClustersANN2Query,
        enableS mClustersANN3 = enableS mClustersANN3,
        s mClustersANN3Query = s mClustersANN3Query,
        enableS mClustersANN5 = enableS mClustersANN5,
        s mClustersANN5Query = s mClustersANN5Query,
        enableS mClustersANN4 = enableS mClustersANN4,
        s mClustersANN4Query = s mClustersANN4Query,
        s mClustersM nScore = s mClustersM nScore,
        s mClustersV deoBasedM nScore = s mClustersV deoBasedM nScore,
        twh nModel d = twh nModel d,
        enableTwH N = enableTwH N,
        twh nMaxT etAgeH s = twh nMaxT etAgeH s,
        q gMaxT etAgeH s = q gMaxT etAgeH s,
        q gMaxNumS m larT ets = q gMaxNumS m larT ets,
        enableUtg = enableUtg,
        utgQuery = T etBasedUserT etGraphS m lar yEng ne
          .fromParams(s ce nfo. nternal d, params),
        enableQ g = enableQ g,
        q gQuery = T etBasedQ gS m lar yEng ne.fromParams(s ce nfo. nternal d, params),
        enableUvg = enableUvg,
        uvgQuery =
          T etBasedUserV deoGraphS m lar yEng ne.fromParams(s ce nfo. nternal d, params),
        params = params
      ),
      params
    )
  }

  def fromParamsForRelatedT et(
     nternal d:  nternal d,
    params: conf gap .Params,
  ): Eng neQuery[Query] = {
    // S mClusters
    val enableS mClustersANN = params(RelatedT etT etBasedParams.EnableS mClustersANNParam)
    val s mClustersModelVers on =
      ModelVers ons.Enum.enumToS mClustersModelVers onMap(params(GlobalParams.ModelVers onParam))
    val s mClustersM nScore = params(RelatedT etT etBasedParams.S mClustersM nScoreParam)
    val s mClustersANNConf g d = params(S mClustersANNParams.S mClustersANNConf g d)
    val enableExper  ntalS mClustersANN =
      params(RelatedT etT etBasedParams.EnableExper  ntalS mClustersANNParam)
    val exper  ntalS mClustersANNConf g d = params(
      S mClustersANNParams.Exper  ntalS mClustersANNConf g d)
    // S mClusters - SANN cluster 1 S m lar y Eng ne
    val enableS mClustersANN1 = params(RelatedT etT etBasedParams.EnableS mClustersANN1Param)
    val s mClustersANN1Conf g d = params(S mClustersANNParams.S mClustersANN1Conf g d)
    // S mClusters - SANN cluster 2 S m lar y Eng ne
    val enableS mClustersANN2 = params(RelatedT etT etBasedParams.EnableS mClustersANN2Param)
    val s mClustersANN2Conf g d = params(S mClustersANNParams.S mClustersANN2Conf g d)
    // S mClusters - SANN cluster 3 S m lar y Eng ne
    val enableS mClustersANN3 = params(RelatedT etT etBasedParams.EnableS mClustersANN3Param)
    val s mClustersANN3Conf g d = params(S mClustersANNParams.S mClustersANN3Conf g d)
    // S mClusters - SANN cluster 5 S m lar y Eng ne
    val enableS mClustersANN5 = params(RelatedT etT etBasedParams.EnableS mClustersANN5Param)
    val s mClustersANN5Conf g d = params(S mClustersANNParams.S mClustersANN5Conf g d)
    // S mClusters - SANN cluster 4 S m lar y Eng ne
    val enableS mClustersANN4 = params(RelatedT etT etBasedParams.EnableS mClustersANN4Param)
    val s mClustersANN4Conf g d = params(S mClustersANNParams.S mClustersANN4Conf g d)
    // S mClusters ANN Quer es for d fferent SANN clusters
    val s mClustersANNQuery = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANNConf g d,
      params
    )
    val exper  ntalS mClustersANNQuery = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      exper  ntalS mClustersANNConf g d,
      params
    )
    val s mClustersANN1Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN1Conf g d,
      params
    )
    val s mClustersANN2Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN2Conf g d,
      params
    )
    val s mClustersANN3Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN3Conf g d,
      params
    )
    val s mClustersANN5Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN5Conf g d,
      params
    )
    val s mClustersANN4Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN4Conf g d,
      params
    )
    // T etBasedCand dateGenerat on
    val maxCand dateNumPerS ceKey = params(GlobalParams.MaxCand dateNumPerS ceKeyParam)
    // TwH N
    val twh nModel d = params(T etBasedTwH NParams.Model dParam)
    val enableTwH N = params(RelatedT etT etBasedParams.EnableTwH NParam)
    val twh nMaxT etAgeH s = params(GlobalParams.MaxT etAgeH sParam)
    // Q G
    val enableQ g = params(RelatedT etT etBasedParams.EnableQ gS m larT etsParam)
    val q gMaxT etAgeH s = params(GlobalParams.MaxT etAgeH sParam)
    val q gMaxNumS m larT ets = params(
      T etBasedCand dateGenerat onParams.Q gMaxNumS m larT etsParam)
    // UTG
    val enableUtg = params(RelatedT etT etBasedParams.EnableUTGParam)
    // UVG
    val enableUvg = params(RelatedT etT etBasedParams.EnableUVGParam)
    // S ceType.RequestT et d  s a placeholder.
    val s ce nfo = S ce nfo(S ceType.RequestT et d,  nternal d, None)

    Eng neQuery(
      Query(
        s ce nfo = s ce nfo,
        maxCand dateNumPerS ceKey = maxCand dateNumPerS ceKey,
        enableS mClustersANN = enableS mClustersANN,
        s mClustersM nScore = s mClustersM nScore,
        s mClustersV deoBasedM nScore = s mClustersM nScore,
        s mClustersANNQuery = s mClustersANNQuery,
        enableExper  ntalS mClustersANN = enableExper  ntalS mClustersANN,
        exper  ntalS mClustersANNQuery = exper  ntalS mClustersANNQuery,
        enableS mClustersANN1 = enableS mClustersANN1,
        s mClustersANN1Query = s mClustersANN1Query,
        enableS mClustersANN2 = enableS mClustersANN2,
        s mClustersANN2Query = s mClustersANN2Query,
        enableS mClustersANN3 = enableS mClustersANN3,
        s mClustersANN3Query = s mClustersANN3Query,
        enableS mClustersANN5 = enableS mClustersANN5,
        s mClustersANN5Query = s mClustersANN5Query,
        enableS mClustersANN4 = enableS mClustersANN4,
        s mClustersANN4Query = s mClustersANN4Query,
        twh nModel d = twh nModel d,
        enableTwH N = enableTwH N,
        twh nMaxT etAgeH s = twh nMaxT etAgeH s,
        q gMaxT etAgeH s = q gMaxT etAgeH s,
        q gMaxNumS m larT ets = q gMaxNumS m larT ets,
        enableUtg = enableUtg,
        utgQuery = T etBasedUserT etGraphS m lar yEng ne
          .fromParams(s ce nfo. nternal d, params),
        enableQ g = enableQ g,
        q gQuery = T etBasedQ gS m lar yEng ne.fromParams(s ce nfo. nternal d, params),
        enableUvg = enableUvg,
        uvgQuery =
          T etBasedUserV deoGraphS m lar yEng ne.fromParams(s ce nfo. nternal d, params),
        params = params,
      ),
      params
    )
  }
  def fromParamsForRelatedV deoT et(
     nternal d:  nternal d,
    params: conf gap .Params,
  ): Eng neQuery[Query] = {
    // S mClusters
    val enableS mClustersANN = params(RelatedV deoT etT etBasedParams.EnableS mClustersANNParam)
    val s mClustersModelVers on =
      ModelVers ons.Enum.enumToS mClustersModelVers onMap(params(GlobalParams.ModelVers onParam))
    val s mClustersM nScore = params(RelatedV deoT etT etBasedParams.S mClustersM nScoreParam)
    val s mClustersANNConf g d = params(S mClustersANNParams.S mClustersANNConf g d)
    val enableExper  ntalS mClustersANN = params(
      RelatedV deoT etT etBasedParams.EnableExper  ntalS mClustersANNParam)
    val exper  ntalS mClustersANNConf g d = params(
      S mClustersANNParams.Exper  ntalS mClustersANNConf g d)
    // S mClusters - SANN cluster 1 S m lar y Eng ne
    val enableS mClustersANN1 = params(RelatedV deoT etT etBasedParams.EnableS mClustersANN1Param)
    val s mClustersANN1Conf g d = params(S mClustersANNParams.S mClustersANN1Conf g d)
    // S mClusters - SANN cluster 2 S m lar y Eng ne
    val enableS mClustersANN2 = params(RelatedV deoT etT etBasedParams.EnableS mClustersANN2Param)
    val s mClustersANN2Conf g d = params(S mClustersANNParams.S mClustersANN2Conf g d)
    // S mClusters - SANN cluster 3 S m lar y Eng ne
    val enableS mClustersANN3 = params(RelatedV deoT etT etBasedParams.EnableS mClustersANN3Param)
    val s mClustersANN3Conf g d = params(S mClustersANNParams.S mClustersANN3Conf g d)
    // S mClusters - SANN cluster 5 S m lar y Eng ne
    val enableS mClustersANN5 = params(RelatedV deoT etT etBasedParams.EnableS mClustersANN5Param)
    val s mClustersANN5Conf g d = params(S mClustersANNParams.S mClustersANN5Conf g d)

    // S mClusters - SANN cluster 4 S m lar y Eng ne
    val enableS mClustersANN4 = params(RelatedV deoT etT etBasedParams.EnableS mClustersANN4Param)
    val s mClustersANN4Conf g d = params(S mClustersANNParams.S mClustersANN4Conf g d)
    // S mClusters ANN Quer es for d fferent SANN clusters
    val s mClustersANNQuery = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANNConf g d,
      params
    )
    val exper  ntalS mClustersANNQuery = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      exper  ntalS mClustersANNConf g d,
      params
    )
    val s mClustersANN1Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN1Conf g d,
      params
    )
    val s mClustersANN2Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN2Conf g d,
      params
    )
    val s mClustersANN3Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN3Conf g d,
      params
    )
    val s mClustersANN5Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN5Conf g d,
      params
    )

    val s mClustersANN4Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.LogFavLongestL2Embedd ngT et,
      s mClustersModelVers on,
      s mClustersANN4Conf g d,
      params
    )
    // T etBasedCand dateGenerat on
    val maxCand dateNumPerS ceKey = params(GlobalParams.MaxCand dateNumPerS ceKeyParam)
    // TwH N
    val twh nModel d = params(T etBasedTwH NParams.Model dParam)
    val enableTwH N = params(RelatedV deoT etT etBasedParams.EnableTwH NParam)
    val twh nMaxT etAgeH s = params(GlobalParams.MaxT etAgeH sParam)
    // Q G
    val enableQ g = params(RelatedV deoT etT etBasedParams.EnableQ gS m larT etsParam)
    val q gMaxT etAgeH s = params(GlobalParams.MaxT etAgeH sParam)
    val q gMaxNumS m larT ets = params(
      T etBasedCand dateGenerat onParams.Q gMaxNumS m larT etsParam)
    // UTG
    val enableUtg = params(RelatedV deoT etT etBasedParams.EnableUTGParam)

    // S ceType.RequestT et d  s a placeholder.
    val s ce nfo = S ce nfo(S ceType.RequestT et d,  nternal d, None)

    val enableUvg = params(RelatedV deoT etT etBasedParams.EnableUVGParam)
    Eng neQuery(
      Query(
        s ce nfo = s ce nfo,
        maxCand dateNumPerS ceKey = maxCand dateNumPerS ceKey,
        enableS mClustersANN = enableS mClustersANN,
        s mClustersM nScore = s mClustersM nScore,
        s mClustersV deoBasedM nScore = s mClustersM nScore,
        s mClustersANNQuery = s mClustersANNQuery,
        enableExper  ntalS mClustersANN = enableExper  ntalS mClustersANN,
        exper  ntalS mClustersANNQuery = exper  ntalS mClustersANNQuery,
        enableS mClustersANN1 = enableS mClustersANN1,
        s mClustersANN1Query = s mClustersANN1Query,
        enableS mClustersANN2 = enableS mClustersANN2,
        s mClustersANN2Query = s mClustersANN2Query,
        enableS mClustersANN3 = enableS mClustersANN3,
        s mClustersANN3Query = s mClustersANN3Query,
        enableS mClustersANN5 = enableS mClustersANN5,
        s mClustersANN5Query = s mClustersANN5Query,
        enableS mClustersANN4 = enableS mClustersANN4,
        s mClustersANN4Query = s mClustersANN4Query,
        twh nModel d = twh nModel d,
        enableTwH N = enableTwH N,
        twh nMaxT etAgeH s = twh nMaxT etAgeH s,
        q gMaxT etAgeH s = q gMaxT etAgeH s,
        q gMaxNumS m larT ets = q gMaxNumS m larT ets,
        enableUtg = enableUtg,
        utgQuery = T etBasedUserT etGraphS m lar yEng ne
          .fromParams(s ce nfo. nternal d, params),
        enableUvg = enableUvg,
        uvgQuery =
          T etBasedUserV deoGraphS m lar yEng ne.fromParams(s ce nfo. nternal d, params),
        enableQ g = enableQ g,
        q gQuery = T etBasedQ gS m lar yEng ne.fromParams(s ce nfo. nternal d, params),
        params = params
      ),
      params
    )
  }
}
