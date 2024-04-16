package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.T etW hScore
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Stats
 mport com.tw ter.product_m xer.core.thr ftscala.Cl entContext
 mport com.tw ter.q g_ranker.thr ftscala.Product
 mport com.tw ter.q g_ranker.thr ftscala.ProductContext
 mport com.tw ter.q g_ranker.thr ftscala.Q gRanker
 mport com.tw ter.q g_ranker.thr ftscala.Q gRankerProductResponse
 mport com.tw ter.q g_ranker.thr ftscala.Q gRankerRequest
 mport com.tw ter.q g_ranker.thr ftscala.Q gRankerResponse
 mport com.tw ter.q g_ranker.thr ftscala.Tw stlyS m larT etsProductContext
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton

/**
 * T  store looks for s m lar t ets from Query nteract onGraph (Q G) for a s ce t et  d.
 * For a g ven query t et, Q G returns us t  s m lar t ets that have an overlap of engage nts
 * (w h t  query t et) on d fferent search quer es
 */
@S ngleton
case class T etBasedQ gS m lar yEng ne(
  q gRanker: Q gRanker. thodPerEndpo nt,
  statsRece ver: StatsRece ver)
    extends ReadableStore[
      T etBasedQ gS m lar yEng ne.Query,
      Seq[T etW hScore]
    ] {

  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val fetchCand datesStat = stats.scope("fetchCand dates")

  overr de def get(
    query: T etBasedQ gS m lar yEng ne.Query
  ): Future[Opt on[Seq[T etW hScore]]] = {
    query.s ce d match {
      case  nternal d.T et d(t et d) =>
        val q gS m larT etsRequest = getQ gS m larT etsRequest(t et d)

        Stats.trackOpt on(fetchCand datesStat) {
          q gRanker
            .getS m larCand dates(q gS m larT etsRequest)
            .map { q gS m larT etsResponse =>
              getCand datesFromQ gResponse(q gS m larT etsResponse)
            }
        }
      case _ =>
        Future.value(None)
    }
  }

  pr vate def getQ gS m larT etsRequest(
    t et d: Long
  ): Q gRankerRequest = {
    // Note: Q gRanker needs a non-empty user d to be passed to return results.
    //   are pass ng  n a dum  user d unt l   f x t  on Q gRanker s de
    val cl entContext = Cl entContext(user d = So (0L))
    val productContext = ProductContext.Tw stlyS m larT etsProductContext(
      Tw stlyS m larT etsProductContext(t et d = t et d))

    Q gRankerRequest(
      cl entContext = cl entContext,
      product = Product.Tw stlyS m larT ets,
      productContext = So (productContext),
    )
  }

  pr vate def getCand datesFromQ gResponse(
    q gS m larT etsResponse: Q gRankerResponse
  ): Opt on[Seq[T etW hScore]] = {
    q gS m larT etsResponse.productResponse match {
      case Q gRankerProductResponse
            .Tw stlyS m larT etCand datesResponse(response) =>
        val t etsW hScore = response.s m larT ets
          .map { s m larT etResult =>
            T etW hScore(
              s m larT etResult.t etResult.t et d,
              s m larT etResult.t etResult.score.getOrElse(0L))
          }
        So (t etsW hScore)

      case _ => None
    }
  }
}

object T etBasedQ gS m lar yEng ne {

  def toS m lar yEng ne nfo(score: Double): S m lar yEng ne nfo = {
    S m lar yEng ne nfo(
      s m lar yEng neType = S m lar yEng neType.Q g,
      model d = None,
      score = So (score))
  }

  case class Query(s ce d:  nternal d)

  def fromParams(
    s ce d:  nternal d,
    params: conf gap .Params,
  ): Eng neQuery[Query] = {
    Eng neQuery(
      Query(s ce d = s ce d),
      params
    )
  }

}
