package com.tw ter.recos.user_v deo_graph.relatedT etHandlers

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graphjet.b part e.ap .B part eGraph
 mport com.tw ter.recos.user_v deo_graph.thr ftscala._
 mport com.tw ter.recos.ut l.Stats._
 mport com.tw ter.servo.request._
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport scala.concurrent.durat on.HOURS
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.recos.user_v deo_graph.store.UserRecentFollo rsStore
 mport com.tw ter.recos.user_v deo_graph.ut l.FetchRHST etsUt l
 mport com.tw ter.recos.user_v deo_graph.ut l.F lterUt l
 mport com.tw ter.recos.user_v deo_graph.ut l.GetRelatedT etCand datesUt l

/**
 *  mple ntat on of t  Thr ft-def ned serv ce  nterface for producerBasedRelatedT ets.
 *
 */
class ProducerBasedRelatedT etsHandler(
  b part eGraph: B part eGraph,
  userRecentFollo rsStore: ReadableStore[UserRecentFollo rsStore.Query, Seq[User d]],
  statsRece ver: StatsRece ver)
    extends RequestHandler[ProducerBasedRelatedT etRequest, RelatedT etResponse] {
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )

  overr de def apply(request: ProducerBasedRelatedT etRequest): Future[RelatedT etResponse] = {
    trackFutureBlockStats(stats) {
      val maxResults = request.maxResults.getOrElse(200)
      val maxNumFollo rs = request.maxNumFollo rs.getOrElse(500)
      val m nScore = request.m nScore.getOrElse(0.0)
      val maxT etAge = request.maxT etAge nH s.getOrElse(48)
      val m nResultDegree = request.m nResultDegree.getOrElse(50)
      val m nCooccurrence = request.m nCooccurrence.getOrElse(4)
      val excludeT et ds = request.excludeT et ds.getOrElse(Seq.empty).toSet

      val follo rsFut = fetchFollo rs(request.producer d, So (maxNumFollo rs))
      follo rsFut.map { follo rs =>
        val rhsT et ds = FetchRHST etsUt l.fetchRHST ets(
          follo rs,
          b part eGraph
        )

        val scorePreFactor = 1000.0 / follo rs.s ze
        val relatedT etCand dates = GetRelatedT etCand datesUt l.getRelatedT etCand dates(
          rhsT et ds,
          m nCooccurrence,
          m nResultDegree,
          scorePreFactor,
          b part eGraph)

        val relatedT ets = relatedT etCand dates
          .f lter { relatedT et =>
            F lterUt l.t etAgeF lter(
              relatedT et.t et d,
              Durat on(maxT etAge, HOURS)) && (relatedT et.score > m nScore) && (!excludeT et ds
              .conta ns(relatedT et.t et d))
          }.take(maxResults)
        stats.stat("response_s ze").add(relatedT ets.s ze)
        RelatedT etResponse(t ets = relatedT ets)
      }
    }
  }

  pr vate def fetchFollo rs(
    producer d: Long,
    maxNumFollo r: Opt on[ nt],
  ): Future[Seq[Long]] = {
    val query =
      UserRecentFollo rsStore.Query(producer d, maxNumFollo r, None)

    val follo rsFut = userRecentFollo rsStore.get(query)
    follo rsFut.map { follo rsOpt =>
      val follo rs = follo rsOpt.getOrElse(Seq.empty)
      val follo r ds = follo rs.d st nct.f lter { user d =>
        val userDegree = b part eGraph.getLeftNodeDegree(user d)
        // constra n to more act ve users that have >1 engage nt to opt m ze latency, and <100 engage nts to avo d spam  behav or
        userDegree > 1 && userDegree < 500
      }
      stats.stat("follo r_s ze_after_f lter").add(follo r ds.s ze)
      follo r ds
    }
  }
}
