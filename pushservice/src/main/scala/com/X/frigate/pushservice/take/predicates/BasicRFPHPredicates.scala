package com.X.frigate.pushservice.take.predicates
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.hermit.predicate.NamedPredicate

trait BasicRFPHPredicates[C <: PushCandidate] {
  val predicates: List[NamedPredicate[C]]
}
