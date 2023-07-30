package com.X.frigate.pushservice.take.predicates

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.predicate.CrtDeciderPredicate
import com.X.frigate.pushservice.predicate.PredicatesForCandidate
import com.X.frigate.pushservice.predicate.ScarecrowPredicate
import com.X.frigate.pushservice.predicate.ntab_caret_fatigue.NtabCaretClickFatiguePredicate
import com.X.hermit.predicate.NamedPredicate

trait TakeCommonPredicates {
  def config: Config

  implicit def statsReceiver: StatsReceiver

  lazy val rfphPrePredicates: List[NamedPredicate[PushCandidate]] = List(
    CrtDeciderPredicate(config.decider),
    PredicatesForCandidate.isChannelValidPredicate,
  )

  lazy val sendHandlerPrePredicates: List[NamedPredicate[PushCandidate]] = List(
    CrtDeciderPredicate(config.decider),
    PredicatesForCandidate.enableSendHandlerCandidates,
    PredicatesForCandidate.mrWebHoldbackPredicate,
    PredicatesForCandidate.targetUserExists,
    PredicatesForCandidate.authorInSocialContext,
    PredicatesForCandidate.recommendedTweetIsAuthoredBySelf,
    PredicatesForCandidate.selfInSocialContext,
    NtabCaretClickFatiguePredicate()
  )

  lazy val postPredicates: List[NamedPredicate[PushCandidate]] = List(
    ScarecrowPredicate(config.scarecrowCheckEventStore)
  )
}
