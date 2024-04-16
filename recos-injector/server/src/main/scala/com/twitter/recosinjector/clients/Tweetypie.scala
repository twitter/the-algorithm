package com.tw ter.recos njector.cl ents

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.t etyp e.T etyP e.{T etyP eExcept on, T etyP eResult}
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.ut l.Future

class T etyp e(
  t etyP eStore: ReadableStore[Long, T etyP eResult]
)(
   mpl c  statsRece ver: StatsRece ver) {
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val fa lureStats = stats.scope("getT etFa lure")

  def getT et(t et d: Long): Future[Opt on[T et]] = {
    t etyP eStore
      .get(t et d)
      .map { _.map(_.t et) }
      .rescue {
        case e: T etyP eExcept on =>
          // Usually results from try ng to query a protected or unsafe t et
          fa lureStats.scope("T etyP eExcept on").counter(e.result.t etState.toStr ng). ncr()
          Future.None
        case e =>
          fa lureStats.counter(e.getClass.getS mpleNa ). ncr()
          Future.None
      }
  }
}
