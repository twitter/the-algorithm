package com.tw ter.recos.user_v deo_graph.relatedT etHandlers

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graphjet.b part e.ap .B part eGraph
 mport com.tw ter.recos.features.t et.thr ftscala.GraphFeaturesForQuery
 mport com.tw ter.recos.user_v deo_graph.thr ftscala._
 mport com.tw ter.recos.user_v deo_graph.ut l.F lterUt l
 mport com.tw ter.recos.user_v deo_graph.ut l.FetchRHST etsUt l
 mport com.tw ter.recos.user_v deo_graph.ut l.GetRelatedT etCand datesUt l
 mport com.tw ter.recos.user_v deo_graph.ut l.GetAll nternalT et dsUt l
 mport com.tw ter.recos.user_v deo_graph.ut l.SampleLHSUsersUt l
 mport com.tw ter.recos.ut l.Stats._
 mport com.tw ter.servo.request._
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport scala.concurrent.durat on.HOURS

/**
 *  mple ntat on of t  Thr ft-def ned serv ce  nterface for t etBasedRelatedT ets.
 *
 */
class T etBasedRelatedT etsHandler(b part eGraph: B part eGraph, statsRece ver: StatsRece ver)
    extends RequestHandler[T etBasedRelatedT etRequest, RelatedT etResponse] {
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )

  overr de def apply(request: T etBasedRelatedT etRequest): Future[RelatedT etResponse] = {
    trackFutureBlockStats(stats) {
      val  nternalQueryT et ds =
        GetAll nternalT et dsUt l.getAll nternalT et ds(request.t et d, b part eGraph)

      val response =  nternalQueryT et ds match {
        case  ad +: N l => getRelatedT ets(request,  ad)
        case _ => RelatedT etResponse()
      }
      Future.value(response)
    }
  }

  pr vate def getRelatedT ets(
    request: T etBasedRelatedT etRequest,
    maskedT et d: Long
  ): RelatedT etResponse = {

    val maxNumSamplesPerNe ghbor = request.maxNumSamplesPerNe ghbor.getOrElse(100)
    val maxResults = request.maxResults.getOrElse(200)
    val m nScore = request.m nScore.getOrElse(0.5)
    val maxT etAge = request.maxT etAge nH s.getOrElse(48)
    val m nResultDegree = request.m nResultDegree.getOrElse(50)
    val m nQueryDegree = request.m nQueryDegree.getOrElse(10)
    val m nCooccurrence = request.m nCooccurrence.getOrElse(3)
    val excludeT et ds = request.excludeT et ds.getOrElse(Seq.empty).toSet

    val queryT etDegree = b part eGraph.getR ghtNodeDegree(maskedT et d)
    stats.stat("queryT etDegree").add(queryT etDegree)

     f (queryT etDegree < m nQueryDegree) {
      stats.counter("queryT etDegreeLessThanM nQueryDegree"). ncr()
      RelatedT etResponse()
    } else {

      val sampledLHSuser ds =
        SampleLHSUsersUt l.sampleLHSUsers(maskedT et d, maxNumSamplesPerNe ghbor, b part eGraph)

      val rHSt et ds = FetchRHST etsUt l.fetchRHST ets(
        sampledLHSuser ds,
        b part eGraph,
      )

      val scorePreFactor =
        queryT etDegree / math.log(queryT etDegree) / sampledLHSuser ds.d st nct.s ze
      val relatedT etCand dates = GetRelatedT etCand datesUt l.getRelatedT etCand dates(
        rHSt et ds,
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
      RelatedT etResponse(
        t ets = relatedT ets,
        queryT etGraphFeatures = So (GraphFeaturesForQuery(degree = So (queryT etDegree))))
    }
  }
}
