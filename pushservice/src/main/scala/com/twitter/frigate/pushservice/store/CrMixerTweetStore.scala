package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter.cr_m xer.thr ftscala.CrM xer
 mport com.tw ter.cr_m xer.thr ftscala.CrM xerT etRequest
 mport com.tw ter.cr_m xer.thr ftscala.CrM xerT etResponse
 mport com.tw ter.cr_m xer.thr ftscala.FrsT etRequest
 mport com.tw ter.cr_m xer.thr ftscala.FrsT etResponse
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Future

/**
 * Store to get content recs from content recom nder.
 */
case class CrM xerT etStore(
  crM xer: CrM xer. thodPerEndpo nt
)(
   mpl c  statsRece ver: StatsRece ver = NullStatsRece ver) {

  pr vate val requestsCounter = statsRece ver.counter("requests")
  pr vate val successCounter = statsRece ver.counter("success")
  pr vate val fa luresCounter = statsRece ver.counter("fa lures")
  pr vate val nonEmptyCounter = statsRece ver.counter("non_empty")
  pr vate val emptyCounter = statsRece ver.counter("empty")
  pr vate val fa luresScope = statsRece ver.scope("fa lures")
  pr vate val latencyStat = statsRece ver.stat("latency")

  pr vate def updateStats[T](f: => Future[Opt on[T]]): Future[Opt on[T]] = {
    requestsCounter. ncr()
    Stat
      .t  Future(latencyStat)(f)
      .onSuccess { r =>
         f (r. sDef ned) nonEmptyCounter. ncr() else emptyCounter. ncr()
        successCounter. ncr()
      }
      .onFa lure { e =>
        {
          fa luresCounter. ncr()
          fa luresScope.counter(e.getClass.getNa ). ncr()
        }
      }
  }

  def getT etRecom ndat ons(
    request: CrM xerT etRequest
  ): Future[Opt on[CrM xerT etResponse]] = {
    updateStats(crM xer.getT etRecom ndat ons(request).map { response =>
      So (response)
    })
  }

  def getFRST etCand dates(request: FrsT etRequest): Future[Opt on[FrsT etResponse]] = {
    updateStats(crM xer.getFrsBasedT etRecom ndat ons(request).map { response =>
      So (response)
    })
  }
}
