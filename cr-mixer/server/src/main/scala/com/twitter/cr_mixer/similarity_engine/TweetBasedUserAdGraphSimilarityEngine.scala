package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.cr_m xer.param.T etBasedUserAdGraphParams
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.recos.user_ad_graph.thr ftscala.Consu rsBasedRelatedAdRequest
 mport com.tw ter.recos.user_ad_graph.thr ftscala.RelatedAdResponse
 mport com.tw ter.recos.user_ad_graph.thr ftscala.UserAdGraph
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.tw stly.thr ftscala.T etRecentEngagedUsers
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton

/**
 * T  store looks for s m lar t ets from UserAdGraph for a S ce T et d
 * For a query t et,User Ad Graph (UAG)
 * lets us f nd out wh ch ot r t ets share a lot of t  sa  engagers w h t  query t et
 */
@S ngleton
case class T etBasedUserAdGraphS m lar yEng ne(
  userAdGraphServ ce: UserAdGraph. thodPerEndpo nt,
  t etEngagedUsersStore: ReadableStore[T et d, T etRecentEngagedUsers],
  statsRece ver: StatsRece ver)
    extends ReadableStore[
      T etBasedUserAdGraphS m lar yEng ne.Query,
      Seq[T etW hScore]
    ] {

   mport T etBasedUserAdGraphS m lar yEng ne._

  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val fetchCoverageExpans onCand datesStat = stats.scope("fetchCoverageExpans onCand dates")
  overr de def get(
    query: T etBasedUserAdGraphS m lar yEng ne.Query
  ): Future[Opt on[Seq[T etW hScore]]] = {
    query.s ce d match {
      case  nternal d.T et d(t et d) => getCand dates(t et d, query)
      case _ =>
        Future.value(None)
    }
  }

  //   f rst fetch t et's recent engaged users as consu SeedSet from MH store,
  // t n query consu rsBasedUTG us ng t  consu rSeedSet
  pr vate def getCand dates(
    t et d: T et d,
    query: T etBasedUserAdGraphS m lar yEng ne.Query
  ): Future[Opt on[Seq[T etW hScore]]] = {
    StatsUt l
      .trackOpt on emsStats(fetchCoverageExpans onCand datesStat) {
        t etEngagedUsersStore
          .get(t et d).flatMap {
            _.map { t etRecentEngagedUsers =>
              val consu rSeedSet =
                t etRecentEngagedUsers.recentEngagedUsers
                  .map { _.user d }.take(query.maxConsu rSeedsNum)
              val consu rsBasedRelatedAdRequest =
                Consu rsBasedRelatedAdRequest(
                  consu rSeedSet = consu rSeedSet,
                  maxResults = So (query.maxResults),
                  m nCooccurrence = So (query.m nCooccurrence),
                  excludeT et ds = So (Seq(t et d)),
                  m nScore = So (query.consu rsBasedM nScore),
                  maxT etAge nH s = So (query.maxT etAge nH s)
                )
              toT etW hScore(userAdGraphServ ce
                .consu rsBasedRelatedAds(consu rsBasedRelatedAdRequest).map { So (_) })
            }.getOrElse(Future.value(None))
          }
      }
  }

}

object T etBasedUserAdGraphS m lar yEng ne {

  def toS m lar yEng ne nfo(score: Double): S m lar yEng ne nfo = {
    S m lar yEng ne nfo(
      s m lar yEng neType = S m lar yEng neType.T etBasedUserAdGraph,
      model d = None,
      score = So (score))
  }
  pr vate def toT etW hScore(
    relatedAdResponseFut: Future[Opt on[RelatedAdResponse]]
  ): Future[Opt on[Seq[T etW hScore]]] = {
    relatedAdResponseFut.map { relatedAdResponseOpt =>
      relatedAdResponseOpt.map { relatedAdResponse =>
        val cand dates =
          relatedAdResponse.adT ets.map(t et => T etW hScore(t et.adT et d, t et.score))

        cand dates
      }
    }
  }

  case class Query(
    s ce d:  nternal d,
    maxResults:  nt,
    m nCooccurrence:  nt,
    consu rsBasedM nScore: Double,
    maxT etAge nH s:  nt,
    maxConsu rSeedsNum:  nt,
  )

  def fromParams(
    s ce d:  nternal d,
    params: conf gap .Params,
  ): Eng neQuery[Query] = {
    Eng neQuery(
      Query(
        s ce d = s ce d,
        maxResults = params(GlobalParams.MaxCand dateNumPerS ceKeyParam),
        m nCooccurrence = params(T etBasedUserAdGraphParams.M nCoOccurrenceParam),
        consu rsBasedM nScore = params(T etBasedUserAdGraphParams.Consu rsBasedM nScoreParam),
        maxT etAge nH s = params(GlobalParams.MaxT etAgeH sParam). nH s,
        maxConsu rSeedsNum = params(T etBasedUserAdGraphParams.MaxConsu rSeedsNumParam),
      ),
      params
    )
  }

}
