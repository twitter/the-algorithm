package com.tw ter.recos njector.f lters

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos njector.cl ents.T etyp e
 mport com.tw ter.ut l.Future

class T etF lter(
  t etyp e: T etyp e
)(
   mpl c  statsRece ver: StatsRece ver) {
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val requests = stats.counter("requests")
  pr vate val f ltered = stats.counter("f ltered")

  /**
   * Query T etyp e to see  f   can fetch a t et object successfully. T etyP e appl es a safety
   * f lter and w ll not return t  t et object  f t  f lter does not pass.
   */
  def f lterForT etyp eSafetyLevel(t et d: Long): Future[Boolean] = {
    requests. ncr()
    t etyp e
      .getT et(t et d)
      .map {
        case So (_) =>
          true
        case _ =>
          f ltered. ncr()
          false
      }
  }
}
