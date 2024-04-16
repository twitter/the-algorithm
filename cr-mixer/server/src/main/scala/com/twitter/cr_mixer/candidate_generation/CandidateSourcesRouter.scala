package com.tw ter.cr_m xer.cand date_generat on

 mport com.tw ter.contentrecom nder.thr ftscala.T et nfo
 mport com.tw ter.cr_m xer.model.Cand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model.GraphS ce nfo
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.model.ModelConf g
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.S ce nfo
 mport com.tw ter.cr_m xer.model.Tr pT etW hScore
 mport com.tw ter.cr_m xer.model.T etW hCand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.cr_m xer.model.T etW hScoreAndSoc alProof
 mport com.tw ter.cr_m xer.param.Consu rBasedWalsParams
 mport com.tw ter.cr_m xer.param.Consu rEmbedd ngBasedCand dateGenerat onParams
 mport com.tw ter.cr_m xer.param.Consu rsBasedUserV deoGraphParams
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Consu rsBasedUserV deoGraphS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Consu rBasedWalsS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Consu rEmbedd ngBasedTr pS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Consu rEmbedd ngBasedTwH NS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Consu rEmbedd ngBasedTwoTo rS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Eng neQuery
 mport com.tw ter.cr_m xer.s m lar y_eng ne.F lterUt l
 mport com.tw ter.cr_m xer.s m lar y_eng ne.HnswANNEng neQuery
 mport com.tw ter.cr_m xer.s m lar y_eng ne.HnswANNS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.ProducerBasedUn f edS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.StandardS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Tr pEng neQuery
 mport com.tw ter.cr_m xer.s m lar y_eng ne.T etBasedUn f edS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.UserT etEnt yGraphS m lar yEng ne
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.cr_m xer.thr ftscala.S ceType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

/**
 * Route t  S ce nfo to t  assoc ated Cand date Eng nes.
 */
@S ngleton
case class Cand dateS cesRouter @ nject() (
  custom zedRetr evalCand dateGenerat on: Custom zedRetr evalCand dateGenerat on,
  s mClusters nterested nCand dateGenerat on: S mClusters nterested nCand dateGenerat on,
  @Na d(ModuleNa s.T etBasedUn f edS m lar yEng ne)
  t etBasedUn f edS m lar yEng ne: StandardS m lar yEng ne[
    T etBasedUn f edS m lar yEng ne.Query,
    T etW hCand dateGenerat on nfo
  ],
  @Na d(ModuleNa s.ProducerBasedUn f edS m lar yEng ne)
  producerBasedUn f edS m lar yEng ne: StandardS m lar yEng ne[
    ProducerBasedUn f edS m lar yEng ne.Query,
    T etW hCand dateGenerat on nfo
  ],
  @Na d(ModuleNa s.Consu rEmbedd ngBasedTr pS m lar yEng ne)
  consu rEmbedd ngBasedTr pS m lar yEng ne: StandardS m lar yEng ne[
    Tr pEng neQuery,
    Tr pT etW hScore
  ],
  @Na d(ModuleNa s.Consu rEmbedd ngBasedTwH NANNS m lar yEng ne)
  consu rBasedTwH NANNS m lar yEng ne: HnswANNS m lar yEng ne,
  @Na d(ModuleNa s.Consu rEmbedd ngBasedTwoTo rANNS m lar yEng ne)
  consu rBasedTwoTo rS m lar yEng ne: HnswANNS m lar yEng ne,
  @Na d(ModuleNa s.Consu rsBasedUserV deoGraphS m lar yEng ne)
  consu rsBasedUserV deoGraphS m lar yEng ne: StandardS m lar yEng ne[
    Consu rsBasedUserV deoGraphS m lar yEng ne.Query,
    T etW hScore
  ],
  @Na d(ModuleNa s.UserT etEnt yGraphS m lar yEng ne) userT etEnt yGraphS m lar yEng ne: StandardS m lar yEng ne[
    UserT etEnt yGraphS m lar yEng ne.Query,
    T etW hScoreAndSoc alProof
  ],
  @Na d(ModuleNa s.Consu rBasedWalsS m lar yEng ne)
  consu rBasedWalsS m lar yEng ne: StandardS m lar yEng ne[
    Consu rBasedWalsS m lar yEng ne.Query,
    T etW hScore
  ],
  t et nfoStore: ReadableStore[T et d, T et nfo],
  globalStats: StatsRece ver,
) {

   mport Cand dateS cesRouter._
  val stats: StatsRece ver = globalStats.scope(t .getClass.getS mpleNa )

  def fetchCand dates(
    requestUser d: User d,
    s ceS gnals: Set[S ce nfo],
    s ceGraphs: Map[Str ng, Opt on[GraphS ce nfo]],
    params: conf gap .Params,
  ): Future[Seq[Seq[ n  alCand date]]] = {

    val t etBasedCand datesFuture = getCand dates(
      getT etBasedS ce nfo(s ceS gnals),
      params,
      T etBasedUn f edS m lar yEng ne.fromParams,
      t etBasedUn f edS m lar yEng ne.getCand dates)

    val producerBasedCand datesFuture =
      getCand dates(
        getProducerBasedS ce nfo(s ceS gnals),
        params,
        ProducerBasedUn f edS m lar yEng ne.fromParams(_, _),
        producerBasedUn f edS m lar yEng ne.getCand dates
      )

    val s mClusters nterested nBasedCand datesFuture =
      getCand datesPerS m lar yEng neModel(
        requestUser d,
        params,
        S mClusters nterested nCand dateGenerat on.fromParams,
        s mClusters nterested nCand dateGenerat on.get)

    val consu rEmbedd ngBasedLogFavBasedTr pCand datesFuture =
       f (params(
          Consu rEmbedd ngBasedCand dateGenerat onParams.EnableLogFavBasedS mClustersTr pParam)) {
        getS mClustersTr pCand dates(
          params,
          Consu rEmbedd ngBasedTr pS m lar yEng ne.fromParams(
            ModelConf g.Consu rLogFavBased nterested nEmbedd ng,
             nternal d.User d(requestUser d),
            params
          ),
          consu rEmbedd ngBasedTr pS m lar yEng ne
        ).map {
          Seq(_)
        }
      } else
        Future.N l

    val consu rsBasedUvgRealGraph nCand datesFuture =
       f (params(Consu rsBasedUserV deoGraphParams.EnableS ceParam)) {
        val realGraph nGraphS ce nfoOpt =
          getGraphS ce nfoByS ceType(S ceType.RealGraph n.na , s ceGraphs)

        getGraphBasedCand dates(
          params,
          Consu rsBasedUserV deoGraphS m lar yEng ne
            .fromParamsForRealGraph n(
              realGraph nGraphS ce nfoOpt
                .map { graphS ce nfo => graphS ce nfo.seedW hScores }.getOrElse(Map.empty),
              params),
          consu rsBasedUserV deoGraphS m lar yEng ne,
          Consu rsBasedUserV deoGraphS m lar yEng ne.toS m lar yEng ne nfo,
          realGraph nGraphS ce nfoOpt
        ).map {
          Seq(_)
        }
      } else Future.N l

    val consu rEmbedd ngBasedFollowBasedTr pCand datesFuture =
       f (params(
          Consu rEmbedd ngBasedCand dateGenerat onParams.EnableFollowBasedS mClustersTr pParam)) {
        getS mClustersTr pCand dates(
          params,
          Consu rEmbedd ngBasedTr pS m lar yEng ne.fromParams(
            ModelConf g.Consu rFollowBased nterested nEmbedd ng,
             nternal d.User d(requestUser d),
            params
          ),
          consu rEmbedd ngBasedTr pS m lar yEng ne
        ).map {
          Seq(_)
        }
      } else
        Future.N l

    val consu rBasedWalsCand datesFuture =
       f (params(
          Consu rBasedWalsParams.EnableS ceParam
        )) {
        getConsu rBasedWalsCand dates(s ceS gnals, params)
      }.map { Seq(_) }
      else Future.N l

    val consu rEmbedd ngBasedTwH NCand datesFuture =
       f (params(Consu rEmbedd ngBasedCand dateGenerat onParams.EnableTwH NParam)) {
        getHnswCand dates(
          params,
          Consu rEmbedd ngBasedTwH NS m lar yEng ne.fromParams(
             nternal d.User d(requestUser d),
            params),
          consu rBasedTwH NANNS m lar yEng ne
        ).map { Seq(_) }
      } else Future.N l

    val consu rEmbedd ngBasedTwoTo rCand datesFuture =
       f (params(Consu rEmbedd ngBasedCand dateGenerat onParams.EnableTwoTo rParam)) {
        getHnswCand dates(
          params,
          Consu rEmbedd ngBasedTwoTo rS m lar yEng ne.fromParams(
             nternal d.User d(requestUser d),
            params),
          consu rBasedTwoTo rS m lar yEng ne
        ).map {
          Seq(_)
        }
      } else Future.N l

    val custom zedRetr evalBasedCand datesFuture =
      getCand datesPerS m lar yEng neModel(
        requestUser d,
        params,
        Custom zedRetr evalCand dateGenerat on.fromParams,
        custom zedRetr evalCand dateGenerat on.get)

    Future
      .collect(
        Seq(
          t etBasedCand datesFuture,
          producerBasedCand datesFuture,
          s mClusters nterested nBasedCand datesFuture,
          consu rBasedWalsCand datesFuture,
          consu rEmbedd ngBasedLogFavBasedTr pCand datesFuture,
          consu rEmbedd ngBasedFollowBasedTr pCand datesFuture,
          consu rEmbedd ngBasedTwH NCand datesFuture,
          consu rEmbedd ngBasedTwoTo rCand datesFuture,
          consu rsBasedUvgRealGraph nCand datesFuture,
          custom zedRetr evalBasedCand datesFuture
        )).map { cand datesL st =>
        // remove empty  nnerSeq
        val result = cand datesL st.flatten.f lter(_.nonEmpty)
        stats.stat("numOfSequences").add(result.s ze)
        stats.stat("flattenCand datesW hDup").add(result.flatten.s ze)

        result
      }
  }

  pr vate def getGraphBasedCand dates[QueryType](
    params: conf gap .Params,
    query: Eng neQuery[QueryType],
    eng ne: StandardS m lar yEng ne[QueryType, T etW hScore],
    toS m lar yEng ne nfo: Double => S m lar yEng ne nfo,
    graphS ce nfoOpt: Opt on[GraphS ce nfo] = None
  ): Future[Seq[ n  alCand date]] = {
    val cand datesOptFut = eng ne.getCand dates(query)
    val t etsW hCand dateGenerat on nfoOptFut = cand datesOptFut.map {
      _.map { t etsW hScores =>
        val sortedCand dates = t etsW hScores.sortBy(-_.score)
        eng ne.getScopedStats.stat("sortedCand dates_s ze").add(sortedCand dates.s ze)
        val t etsW hCand dateGenerat on nfo = sortedCand dates.map { t etW hScore =>
          {
            val s m lar yEng ne nfo = toS m lar yEng ne nfo(t etW hScore.score)
            val s ce nfo = graphS ce nfoOpt.map { graphS ce nfo =>
              // T   nternal d  s a placeholder value.   do not plan to store t  full seedUser d set.
              S ce nfo(
                s ceType = graphS ce nfo.s ceType,
                 nternal d =  nternal d.User d(0L),
                s ceEventT   = None
              )
            }
            T etW hCand dateGenerat on nfo(
              t etW hScore.t et d,
              Cand dateGenerat on nfo(
                s ce nfo,
                s m lar yEng ne nfo,
                Seq.empty // Atom c S m lar y Eng ne.  nce   has no contr but ng SEs
              )
            )
          }
        }
        val maxCand dateNum = params(GlobalParams.MaxCand dateNumPerS ceKeyParam)
        t etsW hCand dateGenerat on nfo.take(maxCand dateNum)
      }
    }
    for {
      t etsW hCand dateGenerat on nfoOpt <- t etsW hCand dateGenerat on nfoOptFut
       n  alCand dates <- convertTo n  alCand dates(
        t etsW hCand dateGenerat on nfoOpt.toSeq.flatten)
    } y eld  n  alCand dates
  }

  pr vate def getCand dates[QueryType](
    s ceS gnals: Set[S ce nfo],
    params: conf gap .Params,
    fromParams: (S ce nfo, conf gap .Params) => QueryType,
    getFunc: QueryType => Future[Opt on[Seq[T etW hCand dateGenerat on nfo]]]
  ): Future[Seq[Seq[ n  alCand date]]] = {
    val quer es = s ceS gnals.map { s ce nfo =>
      fromParams(s ce nfo, params)
    }.toSeq

    Future
      .collect {
        quer es.map { query =>
          for {
            cand dates <- getFunc(query)
            pref lterCand dates <- convertTo n  alCand dates(cand dates.toSeq.flatten)
          } y eld {
            pref lterCand dates
          }
        }
      }
  }

  pr vate def getConsu rBasedWalsCand dates(
    s ceS gnals: Set[S ce nfo],
    params: conf gap .Params
  ): Future[Seq[ n  alCand date]] = {
    // Fetch s ce s gnals and f lter t m based on age.
    val s gnals = F lterUt l.t etS ceAgeF lter(
      getConsu rBasedWalsS ce nfo(s ceS gnals).toSeq,
      params(Consu rBasedWalsParams.MaxT etS gnalAgeH sParam))

    val cand datesOptFut = consu rBasedWalsS m lar yEng ne.getCand dates(
      Consu rBasedWalsS m lar yEng ne.fromParams(s gnals, params)
    )
    val t etsW hCand dateGenerat on nfoOptFut = cand datesOptFut.map {
      _.map { t etsW hScores =>
        val sortedCand dates = t etsW hScores.sortBy(-_.score)
        val f lteredCand dates =
          F lterUt l.t etAgeF lter(sortedCand dates, params(GlobalParams.MaxT etAgeH sParam))
        consu rBasedWalsS m lar yEng ne.getScopedStats
          .stat("f lteredCand dates_s ze").add(f lteredCand dates.s ze)

        val t etsW hCand dateGenerat on nfo = f lteredCand dates.map { t etW hScore =>
          {
            val s m lar yEng ne nfo =
              Consu rBasedWalsS m lar yEng ne.toS m lar yEng ne nfo(t etW hScore.score)
            T etW hCand dateGenerat on nfo(
              t etW hScore.t et d,
              Cand dateGenerat on nfo(
                None,
                s m lar yEng ne nfo,
                Seq.empty // Atom c S m lar y Eng ne.  nce   has no contr but ng SEs
              )
            )
          }
        }
        val maxCand dateNum = params(GlobalParams.MaxCand dateNumPerS ceKeyParam)
        t etsW hCand dateGenerat on nfo.take(maxCand dateNum)
      }
    }
    for {
      t etsW hCand dateGenerat on nfoOpt <- t etsW hCand dateGenerat on nfoOptFut
       n  alCand dates <- convertTo n  alCand dates(
        t etsW hCand dateGenerat on nfoOpt.toSeq.flatten)
    } y eld  n  alCand dates
  }

  pr vate def getS mClustersTr pCand dates(
    params: conf gap .Params,
    query: Tr pEng neQuery,
    eng ne: StandardS m lar yEng ne[
      Tr pEng neQuery,
      Tr pT etW hScore
    ],
  ): Future[Seq[ n  alCand date]] = {
    val t etsW hCand datesGenerat on nfoOptFut =
      eng ne.getCand dates(Eng neQuery(query, params)).map {
        _.map {
          _.map { t etW hScore =>
            // def ne f lters
            T etW hCand dateGenerat on nfo(
              t etW hScore.t et d,
              Cand dateGenerat on nfo(
                None,
                S m lar yEng ne nfo(
                  S m lar yEng neType.ExploreTr pOffl neS mClustersT ets,
                  None,
                  So (t etW hScore.score)),
                Seq.empty
              )
            )
          }
        }
      }
    for {
      t etsW hCand dateGenerat on nfoOpt <- t etsW hCand datesGenerat on nfoOptFut
       n  alCand dates <- convertTo n  alCand dates(
        t etsW hCand dateGenerat on nfoOpt.toSeq.flatten)
    } y eld  n  alCand dates
  }

  pr vate def getHnswCand dates(
    params: conf gap .Params,
    query: HnswANNEng neQuery,
    eng ne: HnswANNS m lar yEng ne,
  ): Future[Seq[ n  alCand date]] = {
    val cand datesOptFut = eng ne.getCand dates(query)
    val t etsW hCand dateGenerat on nfoOptFut = cand datesOptFut.map {
      _.map { t etsW hScores =>
        val sortedCand dates = t etsW hScores.sortBy(-_.score)
        val f lteredCand dates =
          F lterUt l.t etAgeF lter(sortedCand dates, params(GlobalParams.MaxT etAgeH sParam))
        eng ne.getScopedStats.stat("f lteredCand dates_s ze").add(f lteredCand dates.s ze)
        val t etsW hCand dateGenerat on nfo = f lteredCand dates.map { t etW hScore =>
          {
            val s m lar yEng ne nfo =
              eng ne.toS m lar yEng ne nfo(query, t etW hScore.score)
            T etW hCand dateGenerat on nfo(
              t etW hScore.t et d,
              Cand dateGenerat on nfo(
                None,
                s m lar yEng ne nfo,
                Seq.empty // Atom c S m lar y Eng ne.  nce   has no contr but ng SEs
              )
            )
          }
        }
        val maxCand dateNum = params(GlobalParams.MaxCand dateNumPerS ceKeyParam)
        t etsW hCand dateGenerat on nfo.take(maxCand dateNum)
      }
    }
    for {
      t etsW hCand dateGenerat on nfoOpt <- t etsW hCand dateGenerat on nfoOptFut
       n  alCand dates <- convertTo n  alCand dates(
        t etsW hCand dateGenerat on nfoOpt.toSeq.flatten)
    } y eld  n  alCand dates
  }

  /**
   * Returns cand dates from each s m lar y eng ne separately.
   * For 1 requestUser d,   w ll fetch results from each s m lar y eng ne e_ ,
   * and returns Seq[Seq[T etCand date]].
   */
  pr vate def getCand datesPerS m lar yEng neModel[QueryType](
    requestUser d: User d,
    params: conf gap .Params,
    fromParams: ( nternal d, conf gap .Params) => QueryType,
    getFunc: QueryType => Future[
      Opt on[Seq[Seq[T etW hCand dateGenerat on nfo]]]
    ]
  ): Future[Seq[Seq[ n  alCand date]]] = {
    val query = fromParams( nternal d.User d(requestUser d), params)
    getFunc(query).flatMap { cand datesPerS m lar yEng neModelOpt =>
      val cand datesPerS m lar yEng neModel = cand datesPerS m lar yEng neModelOpt.toSeq.flatten
      Future.collect {
        cand datesPerS m lar yEng neModel.map(convertTo n  alCand dates)
      }
    }
  }

  pr vate[cand date_generat on] def convertTo n  alCand dates(
    cand dates: Seq[T etW hCand dateGenerat on nfo],
  ): Future[Seq[ n  alCand date]] = {
    val t et ds = cand dates.map(_.t et d).toSet
    Future.collect(t et nfoStore.mult Get(t et ds)).map { t et nfos =>
      /***
       *  f t et nfo does not ex st,   w ll f lter out t  t et cand date.
       */
      cand dates.collect {
        case cand date  f t et nfos.getOrElse(cand date.t et d, None). sDef ned =>
          val t et nfo = t et nfos(cand date.t et d)
            .getOrElse(throw new  llegalStateExcept on("C ck prev ous l ne's cond  on"))

           n  alCand date(
            t et d = cand date.t et d,
            t et nfo = t et nfo,
            cand date.cand dateGenerat on nfo
          )
      }
    }
  }
}

object Cand dateS cesRouter {
  def getGraphS ce nfoByS ceType(
    s ceTypeStr: Str ng,
    s ceGraphs: Map[Str ng, Opt on[GraphS ce nfo]]
  ): Opt on[GraphS ce nfo] = {
    s ceGraphs.getOrElse(s ceTypeStr, None)
  }

  def getT etBasedS ce nfo(
    s ceS gnals: Set[S ce nfo]
  ): Set[S ce nfo] = {
    s ceS gnals.collect {
      case s ce nfo
           f Allo dS ceTypesForT etBasedUn f edSE.conta ns(s ce nfo.s ceType.value) =>
        s ce nfo
    }
  }

  def getProducerBasedS ce nfo(
    s ceS gnals: Set[S ce nfo]
  ): Set[S ce nfo] = {
    s ceS gnals.collect {
      case s ce nfo
           f Allo dS ceTypesForProducerBasedUn f edSE.conta ns(s ce nfo.s ceType.value) =>
        s ce nfo
    }
  }

  def getConsu rBasedWalsS ce nfo(
    s ceS gnals: Set[S ce nfo]
  ): Set[S ce nfo] = {
    s ceS gnals.collect {
      case s ce nfo
           f Allo dS ceTypesForConsu rBasedWalsSE.conta ns(s ce nfo.s ceType.value) =>
        s ce nfo
    }
  }

  /***
   * S gnal funnel ng should not ex st  n CG or even  n any S m lar yEng ne.
   * T y w ll be  n Router, or eventually,  n CrCand dateGenerator.
   */
  val Allo dS ceTypesForConsu rBasedWalsSE = Set(
    S ceType.T etFavor e.value,
    S ceType.Ret et.value,
    S ceType.T etDontL ke.value, //currently no-op
    S ceType.T etReport.value, //currently no-op
    S ceType.AccountMute.value, //currently no-op
    S ceType.AccountBlock.value //currently no-op
  )
  val Allo dS ceTypesForT etBasedUn f edSE = Set(
    S ceType.T etFavor e.value,
    S ceType.Ret et.value,
    S ceType.Or g nalT et.value,
    S ceType.Reply.value,
    S ceType.T etShare.value,
    S ceType.Not f cat onCl ck.value,
    S ceType.GoodT etCl ck.value,
    S ceType.V deoT etQual yV ew.value,
    S ceType.V deoT etPlayback50.value,
    S ceType.T etAggregat on.value,
  )
  val Allo dS ceTypesForProducerBasedUn f edSE = Set(
    S ceType.UserFollow.value,
    S ceType.UserRepeatedProf leV s .value,
    S ceType.RealGraphOon.value,
    S ceType.FollowRecom ndat on.value,
    S ceType.UserTraff cAttr but onProf leV s .value,
    S ceType.GoodProf leCl ck.value,
    S ceType.ProducerAggregat on.value,
  )
}
