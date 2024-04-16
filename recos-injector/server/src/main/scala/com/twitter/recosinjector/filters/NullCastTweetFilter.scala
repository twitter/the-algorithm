package com.tw ter.recos njector.f lters

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos njector.cl ents.T etyp e
 mport com.tw ter.ut l.Future

/**
 * F lters t ets that are null cast,  .e. t et  s not del vered to a user's follo rs,
 * not shown  n t  user's t  l ne, and does not appear  n search results.
 * T y are ma nly ads t ets.
 */
class NullCastT etF lter(
  t etyp e: T etyp e
)(
   mpl c  statsRece ver: StatsRece ver) {
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val requests = stats.counter("requests")
  pr vate val f ltered = stats.counter("f ltered")

  // Return Future(True) to keep t  T et.
  def f lter(t et d: Long): Future[Boolean] = {
    requests. ncr()
    t etyp e
      .getT et(t et d)
      .map { t etOpt =>
        //  f t  null cast b   s So (true), drop t  t et.
        val  sNullCastT et = t etOpt.flatMap(_.coreData).ex sts(_.nullcast)
         f ( sNullCastT et) {
          f ltered. ncr()
        }
        ! sNullCastT et
      }
  }
}
