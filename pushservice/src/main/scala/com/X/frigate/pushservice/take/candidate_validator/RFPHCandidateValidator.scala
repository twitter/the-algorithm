package com.X.frigate.pushservice.take.candidate_validator

import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.take.predicates.candidate_map.CandidatePredicatesMap
import com.X.hermit.predicate.Predicate
import com.X.util.Future

class RFPHCandidateValidator(override val config: Config) extends CandidateValidator {
  private val rFPHCandidateValidatorStats = statsReceiver.scope(this.getClass.getSimpleName)
  private val concurrentPredicateCount = rFPHCandidateValidatorStats.counter("concurrent")
  private val sequentialPredicateCount = rFPHCandidateValidatorStats.counter("sequential")

  override protected val candidatePredicatesMap = CandidatePredicatesMap(config)

  override def validateCandidate[C <: PushCandidate](candidate: C): Future[Option[Predicate[C]]] = {
    val candidatePredicates = getCRTPredicates(candidate.commonRecType)
    val predicates = rfphPrePredicates ++ candidatePredicates ++ postPredicates
    if (candidate.target.isEmailUser) {
      concurrentPredicateCount.incr()
      executeConcurrentPredicates(candidate, predicates).map(_.headOption)
    } else {
      sequentialPredicateCount.incr()
      executeSequentialPredicates(candidate, predicates)
    }
  }
}
