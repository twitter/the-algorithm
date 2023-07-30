package com.X.frigate.pushservice.util

import com.X.finagle.stats.Counter
import com.X.finagle.stats.Stat
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.Invalid
import com.X.frigate.common.base.OK
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.refresh_handler.ResultWithDebugInfo
import com.X.frigate.pushservice.predicate.BigFilteringEpsilonGreedyExplorationPredicate
import com.X.frigate.pushservice.predicate.MlModelsHoldbackExperimentPredicate
import com.X.frigate.pushservice.take.candidate_validator.RFPHCandidateValidator
import com.X.frigate.pushservice.thriftscala.PushStatus
import com.X.hermit.predicate.NamedPredicate
import com.X.util.Future

class RFPHTakeStepUtil()(globalStats: StatsReceiver) {

  implicit val statsReceiver: StatsReceiver =
    globalStats.scope("RefreshForPushHandler")
  private val takeStats: StatsReceiver = statsReceiver.scope("take")
  private val notifierStats = takeStats.scope("notifier")
  private val validatorStats = takeStats.scope("validator")
  private val validatorLatency: Stat = validatorStats.stat("latency")

  private val executedPredicatesInTandem: Counter =
    takeStats.counter("predicates_executed_in_tandem")

  private val bigFilteringEpsGreedyPredicate: NamedPredicate[PushCandidate] =
    BigFilteringEpsilonGreedyExplorationPredicate()(takeStats)
  private val bigFilteringEpsGreedyStats: StatsReceiver =
    takeStats.scope("big_filtering_eps_greedy_predicate")

  private val modelPredicate: NamedPredicate[PushCandidate] =
    MlModelsHoldbackExperimentPredicate()(takeStats)
  private val mlPredicateStats: StatsReceiver = takeStats.scope("ml_predicate")

  private def updateFilteredStatusExptStats(candidate: PushCandidate, predName: String): Unit = {

    val recTypeStat = globalStats.scope(
      candidate.commonRecType.toString
    )

    recTypeStat.counter(PushStatus.Filtered.toString).incr()
    recTypeStat
      .scope(PushStatus.Filtered.toString)
      .counter(predName)
      .incr()
  }

  def isCandidateValid(
    candidate: PushCandidate,
    candidateValidator: RFPHCandidateValidator
  ): Future[ResultWithDebugInfo] = {
    val predResultFuture = Stat.timeFuture(validatorLatency) {
      Future
        .join(
          bigFilteringEpsGreedyPredicate.apply(Seq(candidate)),
          modelPredicate.apply(Seq(candidate))
        ).flatMap {
          case (Seq(true), Seq(true)) =>
            executedPredicatesInTandem.incr()

            bigFilteringEpsGreedyStats
              .scope(candidate.commonRecType.toString)
              .counter("passed")
              .incr()

            mlPredicateStats
              .scope(candidate.commonRecType.toString)
              .counter("passed")
              .incr()
            candidateValidator.validateCandidate(candidate).map((_, Nil))
          case (Seq(false), _) =>
            bigFilteringEpsGreedyStats
              .scope(candidate.commonRecType.toString)
              .counter("filtered")
              .incr()
            Future.value((Some(bigFilteringEpsGreedyPredicate), Nil))
          case (_, _) =>
            mlPredicateStats
              .scope(candidate.commonRecType.toString)
              .counter("filtered")
              .incr()
            Future.value((Some(modelPredicate), Nil))
        }
    }

    predResultFuture.map {
      case (Some(pred: NamedPredicate[_]), candPredicateResults) =>
        takeStats.counter("filtered_by_named_general_predicate").incr()
        updateFilteredStatusExptStats(candidate, pred.name)
        ResultWithDebugInfo(
          Invalid(Some(pred.name)),
          candPredicateResults
        )

      case (Some(_), candPredicateResults) =>
        takeStats.counter("filtered_by_unnamed_general_predicate").incr()
        updateFilteredStatusExptStats(candidate, predName = "unk")
        ResultWithDebugInfo(
          Invalid(Some("unnamed_candidate_predicate")),
          candPredicateResults
        )

      case (None, candPredicateResults) =>
        takeStats.counter("accepted_push_ok").incr()
        ResultWithDebugInfo(
          OK,
          candPredicateResults
        )
    }
  }
}
