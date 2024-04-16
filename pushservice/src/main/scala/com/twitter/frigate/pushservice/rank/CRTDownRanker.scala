package com.tw ter.fr gate.pushserv ce.rank

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType

/**
 *  T  Ranker re-ranks MR cand dates, down ranks  nput CRTs.
 *  Relat ve rank ng bet en  nput CRTs and rest of t  cand dates doesn't change
 *
 *  Ex: T: T et cand date, F:  nput CRT cand dates
 *
 *  T3, F2, T1, T2, F1 => T3, T1, T2, F2, F1
 */
case class CRTDownRanker(statsRece ver: StatsRece ver) {

  pr vate val recsToDownRankStat = statsRece ver.stat("recs_to_down_rank")
  pr vate val ot rRecsStat = statsRece ver.stat("ot r_recs")
  pr vate val downRankerRequests = statsRece ver.counter("down_ranker_requests")

  pr vate def downRank(
     nputCand dates: Seq[Cand dateDeta ls[PushCand date]],
    crtToDownRank: CommonRecom ndat onType
  ): Seq[Cand dateDeta ls[PushCand date]] = {
    downRankerRequests. ncr()
    val (downRankedCand dates, ot rCand dates) =
       nputCand dates.part  on(_.cand date.commonRecType == crtToDownRank)
    recsToDownRankStat.add(downRankedCand dates.s ze)
    ot rRecsStat.add(ot rCand dates.s ze)
    ot rCand dates ++ downRankedCand dates
  }

  f nal def downRank(
     nputCand dates: Seq[Cand dateDeta ls[PushCand date]],
    crtsToDownRank: Seq[CommonRecom ndat onType]
  ): Seq[Cand dateDeta ls[PushCand date]] = {
    crtsToDownRank. adOpt on match {
      case So (crt) =>
        val downRankedCand dates = downRank( nputCand dates, crt)
        downRank(downRankedCand dates, crtsToDownRank.ta l)
      case None =>  nputCand dates
    }
  }
}
