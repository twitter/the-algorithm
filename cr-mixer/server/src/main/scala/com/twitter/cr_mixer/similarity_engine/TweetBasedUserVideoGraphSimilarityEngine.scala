package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.cr_m xer.param.T etBasedUserV deoGraphParams
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.recos.user_v deo_graph.thr ftscala.RelatedT etResponse
 mport com.tw ter.recos.user_v deo_graph.thr ftscala.Consu rsBasedRelatedT etRequest
 mport com.tw ter.recos.user_v deo_graph.thr ftscala.T etBasedRelatedT etRequest
 mport com.tw ter.recos.user_v deo_graph.thr ftscala.UserV deoGraph
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.tw stly.thr ftscala.T etRecentEngagedUsers
 mport com.tw ter.ut l.Durat on
 mport javax. nject.S ngleton
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport scala.concurrent.durat on.HOURS

/**
 * T  store looks for s m lar t ets from UserV deoGraph for a S ce T et d
 * For a query t et,User V deo Graph (UVG),
 * lets us f nd out wh ch ot r v deo t ets share a lot of t  sa  engagers w h t  query t et
 */
@S ngleton
case class T etBasedUserV deoGraphS m lar yEng ne(
  userV deoGraphServ ce: UserV deoGraph. thodPerEndpo nt,
  t etEngagedUsersStore: ReadableStore[T et d, T etRecentEngagedUsers],
  statsRece ver: StatsRece ver)
    extends ReadableStore[
      T etBasedUserV deoGraphS m lar yEng ne.Query,
      Seq[T etW hScore]
    ] {

   mport T etBasedUserV deoGraphS m lar yEng ne._

  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val fetchCand datesStat = stats.scope("fetchCand dates")
  pr vate val fetchCoverageExpans onCand datesStat = stats.scope("fetchCoverageExpans onCand dates")

  overr de def get(
    query: T etBasedUserV deoGraphS m lar yEng ne.Query
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

  pr vate def getCand dates(
    t et d: T et d,
    query: T etBasedUserV deoGraphS m lar yEng ne.Query
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
        userV deoGraphServ ce.t etBasedRelatedT ets(t etBasedRelatedT etRequest).map {
          So (_)
        })
    }
  }

  pr vate def getCoverageExpans onCand dates(
    t et d: T et d,
    query: T etBasedUserV deoGraphS m lar yEng ne.Query
  ): Future[Opt on[Seq[T etW hScore]]] = {
    StatsUt l
      .trackOpt on emsStats(fetchCoverageExpans onCand datesStat) {
        t etEngagedUsersStore
          .get(t et d).flatMap {
            _.map { t etRecentEngagedUsers =>
              val consu rSeedSet =
                t etRecentEngagedUsers.recentEngagedUsers
                  .map {
                    _.user d
                  }.take(query.maxConsu rSeedsNum)
              val consu rsBasedRelatedT etRequest =
                Consu rsBasedRelatedT etRequest(
                  consu rSeedSet = consu rSeedSet,
                  maxResults = So (query.maxResults),
                  m nCooccurrence = So (query.m nCooccurrence),
                  excludeT et ds = So (Seq(t et d)),
                  m nScore = So (query.consu rsBasedM nScore),
                  maxT etAge nH s = So (query.maxT etAge nH s)
                )

              toT etW hScore(userV deoGraphServ ce
                .consu rsBasedRelatedT ets(consu rsBasedRelatedT etRequest).map {
                  So (_)
                })
            }.getOrElse(Future.value(None))
          }
      }
  }

}

object T etBasedUserV deoGraphS m lar yEng ne {

  pr vate val oldT etCap: Durat on = Durat on(24, HOURS)

  def toS m lar yEng ne nfo(score: Double): S m lar yEng ne nfo = {
    S m lar yEng ne nfo(
      s m lar yEng neType = S m lar yEng neType.T etBasedUserV deoGraph,
      model d = None,
      score = So (score))
  }

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
    enableCoverageExpans onAllT et: Boolean)

  def fromParams(
    s ce d:  nternal d,
    params: conf gap .Params,
  ): Eng neQuery[Query] = {
    Eng neQuery(
      Query(
        s ce d = s ce d,
        maxResults = params(GlobalParams.MaxCand dateNumPerS ceKeyParam),
        m nCooccurrence = params(T etBasedUserV deoGraphParams.M nCoOccurrenceParam),
        t etBasedM nScore = params(T etBasedUserV deoGraphParams.T etBasedM nScoreParam),
        consu rsBasedM nScore = params(T etBasedUserV deoGraphParams.Consu rsBasedM nScoreParam),
        maxT etAge nH s = params(GlobalParams.MaxT etAgeH sParam). nH s,
        maxConsu rSeedsNum = params(T etBasedUserV deoGraphParams.MaxConsu rSeedsNumParam),
        enableCoverageExpans onOldT et =
          params(T etBasedUserV deoGraphParams.EnableCoverageExpans onOldT etParam),
        enableCoverageExpans onAllT et =
          params(T etBasedUserV deoGraphParams.EnableCoverageExpans onAllT etParam)
      ),
      params
    )
  }

}
