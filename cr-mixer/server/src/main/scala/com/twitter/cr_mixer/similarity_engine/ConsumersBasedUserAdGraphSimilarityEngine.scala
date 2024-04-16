package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.cr_m xer.param.Consu rsBasedUserAdGraphParams
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos.user_ad_graph.thr ftscala.Consu rsBasedRelatedAdRequest
 mport com.tw ter.recos.user_ad_graph.thr ftscala.RelatedAdResponse
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton

/**
 * T  store uses t  graph based  nput (a l st of user ds)
 * to query consu rsBasedUserAdGraph and get t  r top engaged ad t ets
 */
@S ngleton
case class Consu rsBasedUserAdGraphS m lar yEng ne(
  consu rsBasedUserAdGraphStore: ReadableStore[
    Consu rsBasedRelatedAdRequest,
    RelatedAdResponse
  ],
  statsRece ver: StatsRece ver)
    extends ReadableStore[
      Consu rsBasedUserAdGraphS m lar yEng ne.Query,
      Seq[T etW hScore]
    ] {

  overr de def get(
    query: Consu rsBasedUserAdGraphS m lar yEng ne.Query
  ): Future[Opt on[Seq[T etW hScore]]] = {
    val consu rsBasedRelatedAdRequest =
      Consu rsBasedRelatedAdRequest(
        query.seedW hScores.keySet.toSeq,
        maxResults = So (query.maxResults),
        m nCooccurrence = So (query.m nCooccurrence),
        m nScore = So (query.m nScore),
        maxT etAge nH s = So (query.maxT etAge nH s)
      )
    consu rsBasedUserAdGraphStore
      .get(consu rsBasedRelatedAdRequest)
      .map { relatedAdResponseOpt =>
        relatedAdResponseOpt.map { relatedAdResponse =>
          relatedAdResponse.adT ets.map { t et =>
            T etW hScore(t et.adT et d, t et.score)
          }
        }
      }
  }
}

object Consu rsBasedUserAdGraphS m lar yEng ne {

  case class Query(
    seedW hScores: Map[User d, Double],
    maxResults:  nt,
    m nCooccurrence:  nt,
    m nScore: Double,
    maxT etAge nH s:  nt)

  def toS m lar yEng ne nfo(
    score: Double
  ): S m lar yEng ne nfo = {
    S m lar yEng ne nfo(
      s m lar yEng neType = S m lar yEng neType.Consu rsBasedUserAdGraph,
      model d = None,
      score = So (score))
  }

  def fromParams(
    seedW hScores: Map[User d, Double],
    params: conf gap .Params,
  ): Eng neQuery[Query] = {

    Eng neQuery(
      Query(
        seedW hScores = seedW hScores,
        maxResults = params(GlobalParams.MaxCand dateNumPerS ceKeyParam),
        m nCooccurrence = params(Consu rsBasedUserAdGraphParams.M nCoOccurrenceParam),
        m nScore = params(Consu rsBasedUserAdGraphParams.M nScoreParam),
        maxT etAge nH s = params(GlobalParams.MaxT etAgeH sParam). nH s,
      ),
      params
    )
  }
}
