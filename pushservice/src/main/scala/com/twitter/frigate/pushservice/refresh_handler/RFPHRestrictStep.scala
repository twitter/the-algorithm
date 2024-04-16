package com.tw ter.fr gate.pushserv ce.refresh_handler

 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateDeta ls
 mport com.tw ter.fr gate.common.base.TargetUser
 mport com.tw ter.fr gate.common.cand date.TargetABDec der
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.target.TargetScor ngDeta ls

class RFPHRestr ctStep()( mpl c  stats: StatsRece ver) {

  pr vate val statsRece ver: StatsRece ver = stats.scope("RefreshForPushHandler")
  pr vate val restr ctStepStats: StatsRece ver = statsRece ver.scope("restr ct")
  pr vate val restr ctStepNumCand datesDroppedStat: Stat =
    restr ctStepStats.stat("cand dates_dropped")

  /**
   * L m  t  number of cand dates that enter t  Take step
   */
  def restr ct(
    target: TargetUser w h TargetABDec der w h TargetScor ngDeta ls,
    cand dates: Seq[Cand dateDeta ls[PushCand date]]
  ): (Seq[Cand dateDeta ls[PushCand date]], Seq[Cand dateDeta ls[PushCand date]]) = {
     f (target.params(PushFeatureSw chParams.EnableRestr ctStep)) {
      val restr ctS zeParam = PushFeatureSw chParams.Restr ctStepS ze
      val (newCand dates, f lteredCand dates) = cand dates.spl At(target.params(restr ctS zeParam))
      val numDropped = cand dates.length - newCand dates.length
      restr ctStepNumCand datesDroppedStat.add(numDropped)
      (newCand dates, f lteredCand dates)
    } else (cand dates, Seq.empty)
  }
}
