package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.model.Cand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.S ce nfo
 mport com.tw ter.cr_m xer.model.T etW hCand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.cr_m xer.param.ProducerBasedCand dateGenerat onParams
 mport com.tw ter.cr_m xer.param.Un f edSET etComb nat on thod
 mport com.tw ter.cr_m xer.param.RelatedT etProducerBasedParams
 mport com.tw ter.cr_m xer.param.S mClustersANNParams
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.cr_m xer.thr ftscala.S ceType
 mport com.tw ter.cr_m xer.ut l. nterleaveUt l
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport javax. nject.Na d
 mport javax. nject.S ngleton
 mport scala.collect on.mutable.ArrayBuffer

/**
 * T  store looks for s m lar t ets from UserT etGraph for a S ce Producer d
 * For a query producer d,User T et Graph (UTG),
 * lets us f nd out wh ch t ets t  query producer's follo rs co-engaged
 */
@S ngleton
case class ProducerBasedUn f edS m lar yEng ne(
  @Na d(ModuleNa s.ProducerBasedUserT etGraphS m lar yEng ne)
  producerBasedUserT etGraphS m lar yEng ne: StandardS m lar yEng ne[
    ProducerBasedUserT etGraphS m lar yEng ne.Query,
    T etW hScore
  ],
  s mClustersANNS m lar yEng ne: StandardS m lar yEng ne[
    S mClustersANNS m lar yEng ne.Query,
    T etW hScore
  ],
  statsRece ver: StatsRece ver)
    extends ReadableStore[ProducerBasedUn f edS m lar yEng ne.Query, Seq[
      T etW hCand dateGenerat on nfo
    ]] {

   mport ProducerBasedUn f edS m lar yEng ne._
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val fetchCand datesStat = stats.scope("fetchCand dates")

  overr de def get(
    query: Query
  ): Future[Opt on[Seq[T etW hCand dateGenerat on nfo]]] = {
    query.s ce nfo. nternal d match {
      case _:  nternal d.User d =>
        StatsUt l.trackOpt on emsStats(fetchCand datesStat) {
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

          val sann4Cand datesFut =
             f (query.enableS mClustersANN4) {
              s mClustersANNS m lar yEng ne.getCand dates(query.s mClustersANN4Query)
            } else Future.None

          val sann5Cand datesFut =
             f (query.enableS mClustersANN5) {
              s mClustersANNS m lar yEng ne.getCand dates(query.s mClustersANN5Query)
            } else Future.None

          val exper  ntalSANNCand datesFut =
             f (query.enableExper  ntalS mClustersANN) {
              s mClustersANNS m lar yEng ne.getCand dates(query.exper  ntalS mClustersANNQuery)
            } else Future.None

          val utgCand datesFut =  f (query.enableUtg) {
            producerBasedUserT etGraphS m lar yEng ne.getCand dates(query.utgQuery)
          } else Future.None

          Future
            .jo n(
              sannCand datesFut,
              sann1Cand datesFut,
              sann2Cand datesFut,
              sann3Cand datesFut,
              sann4Cand datesFut,
              sann5Cand datesFut,
              exper  ntalSANNCand datesFut,
              utgCand datesFut
            ).map {
              case (
                    s mClustersAnnCand dates,
                    s mClustersAnn1Cand dates,
                    s mClustersAnn2Cand dates,
                    s mClustersAnn3Cand dates,
                    s mClustersAnn4Cand dates,
                    s mClustersAnn5Cand dates,
                    exper  ntalSANNCand dates,
                    userT etGraphCand dates) =>
                val f lteredSANNT ets = s mClustersCand dateM nScoreF lter(
                  s mClustersAnnCand dates.toSeq.flatten,
                  query.s mClustersM nScore,
                  query.s mClustersANNQuery.storeQuery.s mClustersANNConf g d)

                val f lteredExper  ntalSANNT ets = s mClustersCand dateM nScoreF lter(
                  exper  ntalSANNCand dates.toSeq.flatten,
                  query.s mClustersM nScore,
                  query.exper  ntalS mClustersANNQuery.storeQuery.s mClustersANNConf g d)

                val f lteredSANN1T ets = s mClustersCand dateM nScoreF lter(
                  s mClustersAnn1Cand dates.toSeq.flatten,
                  query.s mClustersM nScore,
                  query.s mClustersANN1Query.storeQuery.s mClustersANNConf g d)

                val f lteredSANN2T ets = s mClustersCand dateM nScoreF lter(
                  s mClustersAnn2Cand dates.toSeq.flatten,
                  query.s mClustersM nScore,
                  query.s mClustersANN2Query.storeQuery.s mClustersANNConf g d)

                val f lteredSANN3T ets = s mClustersCand dateM nScoreF lter(
                  s mClustersAnn3Cand dates.toSeq.flatten,
                  query.s mClustersM nScore,
                  query.s mClustersANN3Query.storeQuery.s mClustersANNConf g d)

                val f lteredSANN4T ets = s mClustersCand dateM nScoreF lter(
                  s mClustersAnn4Cand dates.toSeq.flatten,
                  query.s mClustersM nScore,
                  query.s mClustersANN4Query.storeQuery.s mClustersANNConf g d)

                val f lteredSANN5T ets = s mClustersCand dateM nScoreF lter(
                  s mClustersAnn5Cand dates.toSeq.flatten,
                  query.s mClustersM nScore,
                  query.s mClustersANN5Query.storeQuery.s mClustersANNConf g d)

                val f lteredUTGT ets =
                  userT etGraphF lter(userT etGraphCand dates.toSeq.flatten)

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
                val utgT etsW hCG nfo = f lteredUTGT ets.map { t etW hScore =>
                  val s m lar yEng ne nfo =
                    ProducerBasedUserT etGraphS m lar yEng ne
                      .toS m lar yEng ne nfo(t etW hScore.score)
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
                    sann1T etsW hCG nfo,
                    sann2T etsW hCG nfo,
                    sann3T etsW hCG nfo,
                    sann4T etsW hCG nfo,
                    sann5T etsW hCG nfo,
                    exper  ntalSANNT etsW hCG nfo,
                  )

                 f (query.utgComb nat on thod == Un f edSET etComb nat on thod. nterleave) {
                  cand dateS cesToBe nterleaved += utgT etsW hCG nfo
                }

                val  nterleavedCand dates =
                   nterleaveUt l. nterleave(cand dateS cesToBe nterleaved)

                val cand dateS cesToBeOrdered =
                  ArrayBuffer[Seq[T etW hCand dateGenerat on nfo]]( nterleavedCand dates)

                 f (query.utgComb nat on thod == Un f edSET etComb nat on thod.Frontload)
                  cand dateS cesToBeOrdered.prepend(utgT etsW hCG nfo)

                val cand datesFromG venOrderComb nat on =
                  S m lar yS ceOrder ngUt l.keepG venOrder(cand dateS cesToBeOrdered)

                val un f edCand datesW hUn f edCG nfo = cand datesFromG venOrderComb nat on.map {
                  cand date =>
                    /***
                     * w n a cand date was made by  nterleave/keepG venOrder,
                     * t n   apply getProducerBasedUn f edCG nfo() to overr de w h t  un f ed CG nfo
                     *
                     *  n contr but ngSE l st for  nterleave.   only have t  chosen SE ava lable.
                     * T   s hard to add for  nterleave, and   plan to add   later after abstract on  mprove nt.
                     */
                    T etW hCand dateGenerat on nfo(
                      t et d = cand date.t et d,
                      cand dateGenerat on nfo = getProducerBasedUn f edCG nfo(
                        cand date.cand dateGenerat on nfo.s ce nfoOpt,
                        cand date.getS m lar yScore,
                        cand date.cand dateGenerat on nfo.contr but ngS m lar yEng nes
                      ) // getS m lar yScore co s from e  r un f edScore or s ngle score
                    )
                }
                stats.stat("un f ed_cand date_s ze").add(un f edCand datesW hUn f edCG nfo.s ze)
                val truncatedCand dates =
                  un f edCand datesW hUn f edCG nfo.take(query.maxCand dateNumPerS ceKey)
                stats.stat("truncatedCand dates_s ze").add(truncatedCand dates.s ze)

                So (truncatedCand dates)

            }
        }

      case _ =>
        stats.counter("s ce d_ s_not_user d_cnt"). ncr()
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

  /** A no-op f lter as UTG f lter already happened at UTG serv ce s de */
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

}
object ProducerBasedUn f edS m lar yEng ne {

  /***
   * Every cand date w ll have t  CG  nfo w h ProducerBasedUn f edS m lar yEng ne
   * as t y are generated by a compos e of S m lar y Eng nes.
   * Add  onally,   store t  contr but ng SEs (eg., SANN, UTG).
   */
  pr vate def getProducerBasedUn f edCG nfo(
    s ce nfoOpt: Opt on[S ce nfo],
    un f edScore: Double,
    contr but ngS m lar yEng nes: Seq[S m lar yEng ne nfo]
  ): Cand dateGenerat on nfo = {
    Cand dateGenerat on nfo(
      s ce nfoOpt,
      S m lar yEng ne nfo(
        s m lar yEng neType = S m lar yEng neType.ProducerBasedUn f edS m lar yEng ne,
        model d = None, //   do not ass gn model d for a un f ed s m lar y eng ne
        score = So (un f edScore)
      ),
      contr but ngS m lar yEng nes
    )
  }

  case class Query(
    s ce nfo: S ce nfo,
    maxCand dateNumPerS ceKey:  nt,
    maxT etAgeH s: Durat on,
    // S mClusters
    enableS mClustersANN: Boolean,
    s mClustersANNQuery: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    enableExper  ntalS mClustersANN: Boolean,
    exper  ntalS mClustersANNQuery: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    enableS mClustersANN1: Boolean,
    s mClustersANN1Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    enableS mClustersANN2: Boolean,
    s mClustersANN2Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    enableS mClustersANN4: Boolean,
    s mClustersANN4Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    enableS mClustersANN3: Boolean,
    s mClustersANN3Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    enableS mClustersANN5: Boolean,
    s mClustersANN5Query: Eng neQuery[S mClustersANNS m lar yEng ne.Query],
    s mClustersM nScore: Double,
    // UTG
    enableUtg: Boolean,
    utgComb nat on thod: Un f edSET etComb nat on thod.Value,
    utgQuery: Eng neQuery[ProducerBasedUserT etGraphS m lar yEng ne.Query])

  def fromParams(
    s ce nfo: S ce nfo,
    params: conf gap .Params,
  ): Eng neQuery[Query] = {
    val maxCand dateNumPerS ceKey = params(GlobalParams.MaxCand dateNumPerS ceKeyParam)
    val maxT etAgeH s = params(GlobalParams.MaxT etAgeH sParam)
    // S mClusters
    val enableS mClustersANN = params(
      ProducerBasedCand dateGenerat onParams.EnableS mClustersANNParam)
    val s mClustersModelVers on =
      ModelVers ons.Enum.enumToS mClustersModelVers onMap(params(GlobalParams.ModelVers onParam))
    val s mClustersANNConf g d = params(S mClustersANNParams.S mClustersANNConf g d)
    // S mClusters - Exper  ntal SANN S m lar y Eng ne
    val enableExper  ntalS mClustersANN = params(
      ProducerBasedCand dateGenerat onParams.EnableExper  ntalS mClustersANNParam)
    val exper  ntalS mClustersANNConf g d = params(
      S mClustersANNParams.Exper  ntalS mClustersANNConf g d)
    // S mClusters - SANN cluster 1 S m lar y Eng ne
    val enableS mClustersANN1 = params(
      ProducerBasedCand dateGenerat onParams.EnableS mClustersANN1Param)
    val s mClustersANN1Conf g d = params(S mClustersANNParams.S mClustersANN1Conf g d)
    // S mClusters - SANN cluster 2 S m lar y Eng ne
    val enableS mClustersANN2 = params(
      ProducerBasedCand dateGenerat onParams.EnableS mClustersANN2Param)
    val s mClustersANN2Conf g d = params(S mClustersANNParams.S mClustersANN2Conf g d)
    // S mClusters - SANN cluster 3 S m lar y Eng ne
    val enableS mClustersANN3 = params(
      ProducerBasedCand dateGenerat onParams.EnableS mClustersANN3Param)
    val s mClustersANN3Conf g d = params(S mClustersANNParams.S mClustersANN3Conf g d)
    // S mClusters - SANN cluster 5 S m lar y Eng ne
    val enableS mClustersANN5 = params(
      ProducerBasedCand dateGenerat onParams.EnableS mClustersANN5Param)
    val s mClustersANN5Conf g d = params(S mClustersANNParams.S mClustersANN5Conf g d)
    val enableS mClustersANN4 = params(
      ProducerBasedCand dateGenerat onParams.EnableS mClustersANN4Param)
    val s mClustersANN4Conf g d = params(S mClustersANNParams.S mClustersANN4Conf g d)

    val s mClustersM nScore = params(
      ProducerBasedCand dateGenerat onParams.S mClustersM nScoreParam)

    // S mClusters ANN Query
    val s mClustersANNQuery = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      s mClustersANNConf g d,
      params
    )
    val exper  ntalS mClustersANNQuery = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      exper  ntalS mClustersANNConf g d,
      params
    )
    val s mClustersANN1Query = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      s mClustersANN1Conf g d,
      params
    )
    val s mClustersANN2Query = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      s mClustersANN2Conf g d,
      params
    )
    val s mClustersANN3Query = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      s mClustersANN3Conf g d,
      params
    )
    val s mClustersANN5Query = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      s mClustersANN5Conf g d,
      params
    )
    val s mClustersANN4Query = S mClustersANNS m lar yEng ne.fromParams(
      s ce nfo. nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      s mClustersANN4Conf g d,
      params
    )
    // UTG
    val enableUtg = params(ProducerBasedCand dateGenerat onParams.EnableUTGParam)
    val utgComb nat on thod = params(
      ProducerBasedCand dateGenerat onParams.UtgComb nat on thodParam)

    Eng neQuery(
      Query(
        s ce nfo = s ce nfo,
        maxCand dateNumPerS ceKey = maxCand dateNumPerS ceKey,
        maxT etAgeH s = maxT etAgeH s,
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
        enableUtg = enableUtg,
        utgComb nat on thod = utgComb nat on thod,
        utgQuery = ProducerBasedUserT etGraphS m lar yEng ne
          .fromParams(s ce nfo. nternal d, params)
      ),
      params
    )
  }

  def fromParamsForRelatedT et(
     nternal d:  nternal d,
    params: conf gap .Params
  ): Eng neQuery[Query] = {
    val maxCand dateNumPerS ceKey = params(GlobalParams.MaxCand dateNumPerS ceKeyParam)
    val maxT etAgeH s = params(GlobalParams.MaxT etAgeH sParam)
    // S mClusters
    val enableS mClustersANN = params(RelatedT etProducerBasedParams.EnableS mClustersANNParam)
    val s mClustersModelVers on =
      ModelVers ons.Enum.enumToS mClustersModelVers onMap(params(GlobalParams.ModelVers onParam))
    val s mClustersANNConf g d = params(S mClustersANNParams.S mClustersANNConf g d)
    val s mClustersM nScore =
      params(RelatedT etProducerBasedParams.S mClustersM nScoreParam)
    // S mClusters - Exper  ntal SANN S m lar y Eng ne
    val enableExper  ntalS mClustersANN = params(
      RelatedT etProducerBasedParams.EnableExper  ntalS mClustersANNParam)
    val exper  ntalS mClustersANNConf g d = params(
      S mClustersANNParams.Exper  ntalS mClustersANNConf g d)
    // S mClusters - SANN cluster 1 S m lar y Eng ne
    val enableS mClustersANN1 = params(RelatedT etProducerBasedParams.EnableS mClustersANN1Param)
    val s mClustersANN1Conf g d = params(S mClustersANNParams.S mClustersANN1Conf g d)
    // S mClusters - SANN cluster 2 S m lar y Eng ne
    val enableS mClustersANN2 = params(RelatedT etProducerBasedParams.EnableS mClustersANN2Param)
    val s mClustersANN2Conf g d = params(S mClustersANNParams.S mClustersANN2Conf g d)
    // S mClusters - SANN cluster 3 S m lar y Eng ne
    val enableS mClustersANN3 = params(RelatedT etProducerBasedParams.EnableS mClustersANN3Param)
    val s mClustersANN3Conf g d = params(S mClustersANNParams.S mClustersANN3Conf g d)
    // S mClusters - SANN cluster 5 S m lar y Eng ne
    val enableS mClustersANN5 = params(RelatedT etProducerBasedParams.EnableS mClustersANN5Param)
    val s mClustersANN5Conf g d = params(S mClustersANNParams.S mClustersANN5Conf g d)

    val enableS mClustersANN4 = params(RelatedT etProducerBasedParams.EnableS mClustersANN4Param)
    val s mClustersANN4Conf g d = params(S mClustersANNParams.S mClustersANN4Conf g d)
    // Bu ld SANN Query
    val s mClustersANNQuery = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      s mClustersANNConf g d,
      params
    )
    val exper  ntalS mClustersANNQuery = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      exper  ntalS mClustersANNConf g d,
      params
    )
    val s mClustersANN1Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      s mClustersANN1Conf g d,
      params
    )
    val s mClustersANN2Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      s mClustersANN2Conf g d,
      params
    )
    val s mClustersANN3Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      s mClustersANN3Conf g d,
      params
    )
    val s mClustersANN5Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      s mClustersANN5Conf g d,
      params
    )
    val s mClustersANN4Query = S mClustersANNS m lar yEng ne.fromParams(
       nternal d,
      Embedd ngType.FavBasedProducer,
      s mClustersModelVers on,
      s mClustersANN4Conf g d,
      params
    )
    // UTG
    val enableUtg = params(RelatedT etProducerBasedParams.EnableUTGParam)
    val utgComb nat on thod = params(
      ProducerBasedCand dateGenerat onParams.UtgComb nat on thodParam)

    // S ceType.RequestUser d  s a placeholder.
    val s ce nfo = S ce nfo(S ceType.RequestUser d,  nternal d, None)

    Eng neQuery(
      Query(
        s ce nfo = s ce nfo,
        maxCand dateNumPerS ceKey = maxCand dateNumPerS ceKey,
        maxT etAgeH s = maxT etAgeH s,
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
        enableUtg = enableUtg,
        utgQuery = ProducerBasedUserT etGraphS m lar yEng ne.fromParams( nternal d, params),
        utgComb nat on thod = utgComb nat on thod
      ),
      params
    )
  }

}
