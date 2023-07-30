package com.X.frigate.pushservice.util

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.TargetUser

object MrUserStateUtil {
  def updateMrUserStateStats(target: TargetUser)(implicit statsReceiver: StatsReceiver) = {
    statsReceiver.counter("AllUserStates").incr()
    target.targetMrUserState.map {
      case Some(state) =>
        statsReceiver.counter(state.name).incr()
      case _ =>
        statsReceiver.counter("UnknownUserState").incr()
    }
  }
}
