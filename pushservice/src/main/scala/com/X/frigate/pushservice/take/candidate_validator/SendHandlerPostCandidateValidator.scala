package com.X.frigate.pushservice.take.candidate_validator

import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.take.predicates.candidate_map.SendHandlerCandidatePredicatesMap
import com.X.hermit.predicate.Predicate
import com.X.util.Future

class SendHandlerPostCandidateValidator(override val config: Config) extends CandidateValidator {

  override protected val candidatePredicatesMap =
    SendHandlerCandidatePredicatesMap.postCandidatePredicates(config)

  private val sendHandlerPostCandidateValidatorStats =
    statsReceiver.counter("sendHandlerPostCandidateValidator_stats")

  override def validateCandidate[C <: PushCandidate](candidate: C): Future[Option[Predicate[C]]] = {
    val candidatePredicates = getCRTPredicates(candidate.commonRecType)
    val predicates = candidatePredicates ++ postPredicates

    sendHandlerPostCandidateValidatorStats.incr()

    executeConcurrentPredicates(candidate, predicates)
      .map(_.headOption)
  }
}
