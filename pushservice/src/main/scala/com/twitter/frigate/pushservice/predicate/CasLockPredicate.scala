package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.CasLock
 mport com.tw ter.fr gate.common.ut l.CasSuccess
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future

object CasLockPred cate {
  def apply(
    casLock: CasLock,
    exp ryDurat on: Durat on
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val stats = statsRece ver.scope("pred cate_addcaslock_for_cand date")
    Pred cate
      .fromAsync { cand date: PushCand date =>
         f (cand date.target.pushContext.ex sts(_.darkWr e.ex sts(_ == true))) {
          Future.True
        } else  f (cand date.commonRecType == CommonRecom ndat onType.Mag cFanoutSportsEvent) {
          Future.True
        } else {
          cand date.target. tory flatMap { h =>
            val now = cand date.createdAt
            val exp ry = now + exp ryDurat on
            val oldT  stamp = h.lastNot f cat onT   map {
              _. nSeconds
            } getOrElse 0
            casLock.cas(cand date.target.target d, oldT  stamp, now. nSeconds, exp ry) map {
              casResult =>
                stats.counter(s"cas_$casResult"). ncr()
                casResult == CasSuccess
            }
          }
        }
      }
      .w hStats(stats)
      .w hNa ("add_cas_lock")
  }
}
