package com.X.frigate.pushservice.predicate

import com.X.decider.Decider
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.hermit.predicate.NamedPredicate
import com.X.hermit.predicate.Predicate

object CrtDeciderPredicate {
  val name = "crt_decider"
  def apply(
    decider: Decider
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    Predicate
      .from { (candidate: PushCandidate) =>
        val prefix = "frigate_pushservice_"
        val deciderKey = prefix + candidate.commonRecType
        decider.feature(deciderKey).isAvailable
      }
      .withStats(statsReceiver.scope(s"predicate_$name"))
      .withName(name)
  }
}
