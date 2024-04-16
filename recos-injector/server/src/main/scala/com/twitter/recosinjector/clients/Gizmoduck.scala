package com.tw ter.recos njector.cl ents

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

class G zmoduck(
  userStore: ReadableStore[Long, User]
)(
   mpl c  statsRece ver: StatsRece ver) {
  pr vate val log = Logger()
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )

  def getUser(user d: Long): Future[Opt on[User]] = {
    userStore
      .get(user d)
      .rescue {
        case e =>
          stats.scope("getUserFa lure").counter(e.getClass.getS mpleNa ). ncr()
          log.error(s"Fa led w h  ssage ${e.toStr ng}")
          Future.None
      }
  }
}
