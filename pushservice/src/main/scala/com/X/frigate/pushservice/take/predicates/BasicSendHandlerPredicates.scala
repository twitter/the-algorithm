package com.X.frigate.pushservice.take.predicates

import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.hermit.predicate.NamedPredicate

trait BasicSendHandlerPredicates[C <: PushCandidate] {

  // specific predicates per candidate type before basic SendHandler predicates
  val preCandidateSpecificPredicates: List[NamedPredicate[C]] = List.empty

  // specific predicates per candidate type after basic SendHandler predicates, could be empty
  val postCandidateSpecificPredicates: List[NamedPredicate[C]] = List.empty
}
