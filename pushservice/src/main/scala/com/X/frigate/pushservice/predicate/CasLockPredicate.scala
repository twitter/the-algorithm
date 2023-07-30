package com.X.frigate.pushservice.predicate

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.util.CasLock
import com.X.frigate.common.util.CasSuccess
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.thriftscala.CommonRecommendationType
import com.X.hermit.predicate.NamedPredicate
import com.X.hermit.predicate.Predicate
import com.X.util.Duration
import com.X.util.Future

object CasLockPredicate {
  def apply(
    casLock: CasLock,
    expiryDuration: Duration
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val stats = statsReceiver.scope("predicate_addcaslock_for_candidate")
    Predicate
      .fromAsync { candidate: PushCandidate =>
        if (candidate.target.pushContext.exists(_.darkWrite.exists(_ == true))) {
          Future.True
        } else if (candidate.commonRecType == CommonRecommendationType.MagicFanoutSportsEvent) {
          Future.True
        } else {
          candidate.target.history flatMap { h =>
            val now = candidate.createdAt
            val expiry = now + expiryDuration
            val oldTimestamp = h.lastNotificationTime map {
              _.inSeconds
            } getOrElse 0
            casLock.cas(candidate.target.targetId, oldTimestamp, now.inSeconds, expiry) map {
              casResult =>
                stats.counter(s"cas_$casResult").incr()
                casResult == CasSuccess
            }
          }
        }
      }
      .withStats(stats)
      .withName("add_cas_lock")
  }
}
