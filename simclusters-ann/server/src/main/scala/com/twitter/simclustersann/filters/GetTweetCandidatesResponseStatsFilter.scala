package com.tw ter.s mclustersann.f lters

 mport com.tw ter.f nagle.Serv ce
 mport com.tw ter.f nagle.S mpleF lter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.scrooge.Request
 mport com.tw ter.scrooge.Response
 mport com.tw ter.s mclustersann.thr ftscala.S mClustersANNServ ce
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class GetT etCand datesResponseStatsF lter @ nject() (
  statsRece ver: StatsRece ver)
    extends S mpleF lter[Request[S mClustersANNServ ce.GetT etCand dates.Args], Response[
      S mClustersANNServ ce.GetT etCand dates.SuccessType
    ]] {

  pr vate[t ] val stats = statsRece ver.scope(" thod_response_stats").scope("getT etCand dates")
  pr vate[t ] val cand dateScoreStats = stats.stat("cand date_score_x1000")
  pr vate[t ] val emptyResponseCounter = stats.counter("empty")
  pr vate[t ] val nonEmptyResponseCounter = stats.counter("non_empty")
  overr de def apply(
    request: Request[S mClustersANNServ ce.GetT etCand dates.Args],
    serv ce: Serv ce[Request[S mClustersANNServ ce.GetT etCand dates.Args], Response[
      S mClustersANNServ ce.GetT etCand dates.SuccessType
    ]]
  ): Future[Response[S mClustersANNServ ce.GetT etCand dates.SuccessType]] = {
    val response = serv ce(request)

    response.onSuccess { successResponse =>
       f (successResponse.value.s ze == 0)
        emptyResponseCounter. ncr()
      else
        nonEmptyResponseCounter. ncr()
      successResponse.value.foreach { cand date =>
        cand dateScoreStats.add(cand date.score.toFloat * 1000)
      }
    }
    response
  }
}
