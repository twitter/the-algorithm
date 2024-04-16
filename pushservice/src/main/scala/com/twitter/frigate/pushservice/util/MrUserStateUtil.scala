package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.TargetUser

object MrUserStateUt l {
  def updateMrUserStateStats(target: TargetUser)( mpl c  statsRece ver: StatsRece ver) = {
    statsRece ver.counter("AllUserStates"). ncr()
    target.targetMrUserState.map {
      case So (state) =>
        statsRece ver.counter(state.na ). ncr()
      case _ =>
        statsRece ver.counter("UnknownUserState"). ncr()
    }
  }
}
