package com.X.frigate.pushservice.take.candidate_validator

import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.take.predicates.candidate_map.SendHandlerCandidatePredicatesMap
import com.X.hermit.predicate.Predicate
import com.X.util.Future

class SendHandlerPreCandidateValidator(override val config: Config) extends CandidateValidator {

  override protected val candidatePredicatesMap =
    SendHandlerCandidatePredicatesMap.preCandidatePredicates(config)

  private val sendHandlerPreCandidateValidatorStats =
    statsReceiver.counter("sendHandlerPreCandidateValidator_stats")

  override def validateCandidate[C <: PushCandidate](candidate: C): Future[Option[Predicate[C]]] = {
    val candidatePredicates = getCRTPredicates(candidate.commonRecType)
    val predicates = sendHandlerPrePredicates ++ candidatePredicates

    sendHandlerPreCandidateValidatorStats.incr()
    executeSequentialPredicates(candidate, predicates)
  }
}
