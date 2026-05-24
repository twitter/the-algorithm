package com.twitter.frigate.pushservice.take.predicates

import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.hermit.predicate.NamedPredicate

trait BasicSendHandlerPredicates[C <: PushCandidate] {

  // specific predicates per candidate type before basic SendHandler predicates
  val preCandidateSpecificPredicates: List[NamedPredicate[C]] = List.empty

  // specific predicates per candidate type after basic SendHandler predicates, could be empty
  val postCandidateSpecificPredicates: List[NamedPredicate[C]] = List.empty
}
