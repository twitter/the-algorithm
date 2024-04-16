package com.tw ter.fr gate.pushserv ce.rank

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType

/**
 *  T  Ranker re-ranks MR cand dates, boost ng  nput CRTs.
 *  Relat ve rank ng bet en  nput CRTs and rest of t  cand dates doesn't change
 *
 *  Ex: T: T et cand date, F:  nput CRT cand datess
 *
 *  T3, F2, T1, T2, F1 => F2, F1, T3, T1, T2
 */
case class CRTBoostRanker(statsRece ver: StatsRece ver) {

  pr vate val recsToBoostStat = statsRece ver.stat("recs_to_boost")
  pr vate val ot rRecsStat = statsRece ver.stat("ot r_recs")

  pr vate def boostCrtToTop(
     nputCand dates: Seq[Cand dateDeta ls[PushCand date]],
    crtToBoost: CommonRecom ndat onType
  ): Seq[Cand dateDeta ls[PushCand date]] = {
    val (upRankedCand dates, ot rCand dates) =
       nputCand dates.part  on(_.cand date.commonRecType == crtToBoost)
    recsToBoostStat.add(upRankedCand dates.s ze)
    ot rRecsStat.add(ot rCand dates.s ze)
    upRankedCand dates ++ ot rCand dates
  }

  f nal def boostCrtsToTop(
     nputCand dates: Seq[Cand dateDeta ls[PushCand date]],
    crtsToBoost: Seq[CommonRecom ndat onType]
  ): Seq[Cand dateDeta ls[PushCand date]] = {
    crtsToBoost. adOpt on match {
      case So (crt) =>
        val upRankedCand dates = boostCrtToTop( nputCand dates, crt)
        boostCrtsToTop(upRankedCand dates, crtsToBoost.ta l)
      case None =>  nputCand dates
    }
  }

  f nal def boostCrtsToTopStableOrder(
     nputCand dates: Seq[Cand dateDeta ls[PushCand date]],
    crtsToBoost: Seq[CommonRecom ndat onType]
  ): Seq[Cand dateDeta ls[PushCand date]] = {
    val crtsToBoostSet = crtsToBoost.toSet
    val (upRankedCand dates, ot rCand dates) =  nputCand dates.part  on(cand dateDeta l =>
      crtsToBoostSet.conta ns(cand dateDeta l.cand date.commonRecType))

    upRankedCand dates ++ ot rCand dates
  }
}
