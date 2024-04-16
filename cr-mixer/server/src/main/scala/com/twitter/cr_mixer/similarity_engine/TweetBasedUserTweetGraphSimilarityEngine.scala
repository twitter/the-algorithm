package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.cr_m xer.param.T etBasedUserT etGraphParams
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.recos.user_t et_graph.thr ftscala.RelatedT etResponse
 mport com.tw ter.recos.user_t et_graph.thr ftscala.T etBasedRelatedT etRequest
 mport com.tw ter.recos.user_t et_graph.thr ftscala.Consu rsBasedRelatedT etRequest
 mport com.tw ter.recos.user_t et_graph.thr ftscala.UserT etGraph
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.tw stly.thr ftscala.T etRecentEngagedUsers
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport scala.concurrent.durat on.HOURS

/**
 * T  store looks for s m lar t ets from UserT etGraph for a S ce T et d
 * For a query t et,User T et Graph (UTG),
 * lets us f nd out wh ch ot r t ets share a lot of t  sa  engagers w h t  query t et
 * one-pager: go/UTG
 */
@S ngleton
case class T etBasedUserT etGraphS m lar yEng ne(
  userT etGraphServ ce: UserT etGraph. thodPerEndpo nt,
  t etEngagedUsersStore: ReadableStore[T et d, T etRecentEngagedUsers],
  statsRece ver: StatsRece ver)
    extends ReadableStore[
      T etBasedUserT etGraphS m lar yEng ne.Query,
      Seq[T etW hScore]
    ] {

   mport T etBasedUserT etGraphS m lar yEng ne._

  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val fetchCand datesStat = stats.scope("fetchCand dates")
  pr vate val fetchCoverageExpans onCand datesStat = stats.scope("fetchCoverageExpans onCand dates")

  overr de def get(
    query: T etBasedUserT etGraphS m lar yEng ne.Query
  ): Future[Opt on[Seq[T etW hScore]]] = {
    query.s ce d match {
      case  nternal d.T et d(t et d)  f query.enableCoverageExpans onAllT et =>
        getCoverageExpans onCand dates(t et d, query)

      case  nternal d.T et d(t et d)  f query.enableCoverageExpans onOldT et => // For Ho 
         f ( sOldT et(t et d)) getCoverageExpans onCand dates(t et d, query)
        else getCand dates(t et d, query)

      case  nternal d.T et d(t et d) => getCand dates(t et d, query)
      case _ =>
        Future.value(None)
    }
  }

  // T   s t  ma n cand date s ce
  pr vate def getCand dates(
    t et d: T et d,
    query: T etBasedUserT etGraphS m lar yEng ne.Query
  ): Future[Opt on[Seq[T etW hScore]]] = {
    StatsUt l.trackOpt on emsStats(fetchCand datesStat) {
      val t etBasedRelatedT etRequest = {
        T etBasedRelatedT etRequest(
          t et d,
          maxResults = So (query.maxResults),
          m nCooccurrence = So (query.m nCooccurrence),
          excludeT et ds = So (Seq(t et d)),
          m nScore = So (query.t etBasedM nScore),
          maxT etAge nH s = So (query.maxT etAge nH s)
        )
      }
      toT etW hScore(
        userT etGraphServ ce.t etBasedRelatedT ets(t etBasedRelatedT etRequest).map {
          So (_)
        })
    }
  }

  // funct on for DDGs, for coverage expans on algo,   f rst fetch t et's recent engaged users as consu SeedSet from MH store,
  // and query consu rsBasedUTG us ng t  consu SeedSet
  pr vate def getCoverageExpans onCand dates(
    t et d: T et d,
    query: T etBasedUserT etGraphS m lar yEng ne.Query
  ): Future[Opt on[Seq[T etW hScore]]] = {
    StatsUt l
      .trackOpt on emsStats(fetchCoverageExpans onCand datesStat) {
        t etEngagedUsersStore
          .get(t et d).flatMap {
            _.map { t etRecentEngagedUsers =>
              val consu rSeedSet =
                t etRecentEngagedUsers.recentEngagedUsers
                  .map { _.user d }.take(query.maxConsu rSeedsNum)
              val consu rsBasedRelatedT etRequest =
                Consu rsBasedRelatedT etRequest(
                  consu rSeedSet = consu rSeedSet,
                  maxResults = So (query.maxResults),
                  m nCooccurrence = So (query.m nCooccurrence),
                  excludeT et ds = So (Seq(t et d)),
                  m nScore = So (query.consu rsBasedM nScore),
                  maxT etAge nH s = So (query.maxT etAge nH s)
                )

              toT etW hScore(userT etGraphServ ce
                .consu rsBasedRelatedT ets(consu rsBasedRelatedT etRequest).map { So (_) })
            }.getOrElse(Future.value(None))
          }
      }
  }

}

object T etBasedUserT etGraphS m lar yEng ne {

  def toS m lar yEng ne nfo(score: Double): S m lar yEng ne nfo = {
    S m lar yEng ne nfo(
      s m lar yEng neType = S m lar yEng neType.T etBasedUserT etGraph,
      model d = None,
      score = So (score))
  }

  pr vate val oldT etCap: Durat on = Durat on(48, HOURS)

  pr vate def toT etW hScore(
    relatedT etResponseFut: Future[Opt on[RelatedT etResponse]]
  ): Future[Opt on[Seq[T etW hScore]]] = {
    relatedT etResponseFut.map { relatedT etResponseOpt =>
      relatedT etResponseOpt.map { relatedT etResponse =>
        val cand dates =
          relatedT etResponse.t ets.map(t et => T etW hScore(t et.t et d, t et.score))
        cand dates
      }
    }
  }

  pr vate def  sOldT et(t et d: T et d): Boolean = {
    Snowflake d
      .t  From dOpt(t et d).forall { t etT   => t etT   < T  .now - oldT etCap }
    //  f t re's no snowflake t  stamp,   have no  dea w n t  t et happened.
  }

  case class Query(
    s ce d:  nternal d,
    maxResults:  nt,
    m nCooccurrence:  nt,
    t etBasedM nScore: Double,
    consu rsBasedM nScore: Double,
    maxT etAge nH s:  nt,
    maxConsu rSeedsNum:  nt,
    enableCoverageExpans onOldT et: Boolean,
    enableCoverageExpans onAllT et: Boolean,
  )

  def fromParams(
    s ce d:  nternal d,
    params: conf gap .Params,
  ): Eng neQuery[Query] = {
    Eng neQuery(
      Query(
        s ce d = s ce d,
        maxResults = params(GlobalParams.MaxCand dateNumPerS ceKeyParam),
        m nCooccurrence = params(T etBasedUserT etGraphParams.M nCoOccurrenceParam),
        t etBasedM nScore = params(T etBasedUserT etGraphParams.T etBasedM nScoreParam),
        consu rsBasedM nScore = params(T etBasedUserT etGraphParams.Consu rsBasedM nScoreParam),
        maxT etAge nH s = params(GlobalParams.MaxT etAgeH sParam). nH s,
        maxConsu rSeedsNum = params(T etBasedUserT etGraphParams.MaxConsu rSeedsNumParam),
        enableCoverageExpans onOldT et =
          params(T etBasedUserT etGraphParams.EnableCoverageExpans onOldT etParam),
        enableCoverageExpans onAllT et =
          params(T etBasedUserT etGraphParams.EnableCoverageExpans onAllT etParam),
      ),
      params
    )
  }

}
