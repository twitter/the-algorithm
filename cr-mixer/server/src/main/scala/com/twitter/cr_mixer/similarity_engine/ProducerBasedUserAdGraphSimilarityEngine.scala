package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.cr_m xer.param.ProducerBasedUserAdGraphParams
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos.user_ad_graph.thr ftscala.ProducerBasedRelatedAdRequest
 mport com.tw ter.recos.user_ad_graph.thr ftscala.UserAdGraph
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.t  l nes.conf gap 

/**
 * T  store looks for s m lar t ets from UserAdGraph for a S ce Producer d
 * For a query producer d,User T et Graph (UAG),
 * lets us f nd out wh ch ad t ets t  query producer's follo rs co-engaged
 */
@S ngleton
case class ProducerBasedUserAdGraphS m lar yEng ne(
  userAdGraphServ ce: UserAdGraph. thodPerEndpo nt,
  statsRece ver: StatsRece ver)
    extends ReadableStore[ProducerBasedUserAdGraphS m lar yEng ne.Query, Seq[
      T etW hScore
    ]] {

  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val fetchCand datesStat = stats.scope("fetchCand dates")

  overr de def get(
    query: ProducerBasedUserAdGraphS m lar yEng ne.Query
  ): Future[Opt on[Seq[T etW hScore]]] = {
    query.s ce d match {
      case  nternal d.User d(producer d) =>
        StatsUt l.trackOpt on emsStats(fetchCand datesStat) {
          val relatedAdRequest =
            ProducerBasedRelatedAdRequest(
              producer d,
              maxResults = So (query.maxResults),
              m nCooccurrence = So (query.m nCooccurrence),
              m nScore = So (query.m nScore),
              maxNumFollo rs = So (query.maxNumFollo rs),
              maxT etAge nH s = So (query.maxT etAge nH s),
            )

          userAdGraphServ ce.producerBasedRelatedAds(relatedAdRequest).map { relatedAdResponse =>
            val cand dates =
              relatedAdResponse.adT ets.map(t et => T etW hScore(t et.adT et d, t et.score))
            So (cand dates)
          }
        }
      case _ =>
        Future.value(None)
    }
  }
}

object ProducerBasedUserAdGraphS m lar yEng ne {

  def toS m lar yEng ne nfo(score: Double): S m lar yEng ne nfo = {
    S m lar yEng ne nfo(
      s m lar yEng neType = S m lar yEng neType.ProducerBasedUserAdGraph,
      model d = None,
      score = So (score))
  }

  case class Query(
    s ce d:  nternal d,
    maxResults:  nt,
    m nCooccurrence:  nt, // requ re at least {m nCooccurrence} lhs user engaged w h returned t et
    m nScore: Double,
    maxNumFollo rs:  nt, // max number of lhs users
    maxT etAge nH s:  nt)

  def fromParams(
    s ce d:  nternal d,
    params: conf gap .Params,
  ): Eng neQuery[Query] = {
    Eng neQuery(
      Query(
        s ce d = s ce d,
        maxResults = params(GlobalParams.MaxCand dateNumPerS ceKeyParam),
        m nCooccurrence = params(ProducerBasedUserAdGraphParams.M nCoOccurrenceParam),
        maxNumFollo rs = params(ProducerBasedUserAdGraphParams.MaxNumFollo rsParam),
        maxT etAge nH s = params(GlobalParams.MaxT etAgeH sParam). nH s,
        m nScore = params(ProducerBasedUserAdGraphParams.M nScoreParam)
      ),
      params
    )
  }
}
