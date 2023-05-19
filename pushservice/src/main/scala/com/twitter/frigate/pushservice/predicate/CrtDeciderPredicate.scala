package com.twitter.frigate.pushservice.predicate

import com.twitter.decider.Decider
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.hermit.predicate.NamedPredicate
import com.twitter.hermit.predicate.Predicate

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
