package com.twitter.frigate.pushservice.take.predicates
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.hermit.predicate.NamedPredicate

trait BasicRFPHPredicates[C <: PushCandidate] {
  val predicates: List[NamedPredicate[C]]
}
