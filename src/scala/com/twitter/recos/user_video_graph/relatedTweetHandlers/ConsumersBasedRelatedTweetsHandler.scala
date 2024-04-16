package com.tw ter.recos.user_t et_graph.relatedT etHandlers

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graphjet.b part e.ap .B part eGraph
 mport com.tw ter.recos.user_v deo_graph.thr ftscala._
 mport com.tw ter.recos.user_v deo_graph.ut l.FetchRHST etsUt l
 mport com.tw ter.recos.user_v deo_graph.ut l.F lterUt l
 mport com.tw ter.recos.user_v deo_graph.ut l.GetRelatedT etCand datesUt l
 mport com.tw ter.recos.ut l.Stats._
 mport com.tw ter.servo.request._
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport scala.concurrent.durat on.HOURS

/**
 *  mple ntat on of t  Thr ft-def ned serv ce  nterface for consu rsT etBasedRelatedT ets.
 * g ven a l st of consu r user ds, f nd t  t ets t y co-engaged w h ( 're treat ng  nput user ds as consu rs t refore "consu rsT etBasedRelatedT ets" )
 * example use case: g ven a l st of user's contacts  n t  r address book, f nd t ets those contacts engaged w h
 */
class Consu rsBasedRelatedT etsHandler(
  b part eGraph: B part eGraph,
  statsRece ver: StatsRece ver)
    extends RequestHandler[Consu rsBasedRelatedT etRequest, RelatedT etResponse] {
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )

  overr de def apply(request: Consu rsBasedRelatedT etRequest): Future[RelatedT etResponse] = {
    trackFutureBlockStats(stats) {

      val maxResults = request.maxResults.getOrElse(200)
      val m nScore = request.m nScore.getOrElse(0.0)
      val maxT etAge = request.maxT etAge nH s.getOrElse(48)
      val m nResultDegree = request.m nResultDegree.getOrElse(50)
      val m nCooccurrence = request.m nCooccurrence.getOrElse(3)
      val excludeT et ds = request.excludeT et ds.getOrElse(Seq.empty).toSet

      val consu rSeedSet = request.consu rSeedSet.d st nct.f lter { user d =>
        val userDegree = b part eGraph.getLeftNodeDegree(user d)
        // constra n to users that have <100 engage nts to avo d spam  behav or
        userDegree < 100
      }

      val rhsT et ds = FetchRHST etsUt l.fetchRHST ets(
        consu rSeedSet,
        b part eGraph
      )

      val scorePreFactor = 1000.0 / consu rSeedSet.s ze
      val relatedT etCand dates = GetRelatedT etCand datesUt l.getRelatedT etCand dates(
        rhsT et ds,
        m nCooccurrence,
        m nResultDegree,
        scorePreFactor,
        b part eGraph)

      val relatedT ets = relatedT etCand dates
        .f lter(relatedT et =>
          F lterUt l.t etAgeF lter(
            relatedT et.t et d,
            Durat on(maxT etAge, HOURS)) && (relatedT et.score > m nScore) && (!excludeT et ds
            .conta ns(relatedT et.t et d))).take(maxResults)

      stats.stat("response_s ze").add(relatedT ets.s ze)
      Future.value(RelatedT etResponse(t ets = relatedT ets))
    }
  }
}
