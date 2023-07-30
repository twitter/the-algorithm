package com.X.frigate.pushservice.refresh_handler

import com.X.finagle.stats.Stat
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.CandidateDetails
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.thriftscala.CommonRecommendationType

class RFPHStatsRecorder(implicit statsReceiver: StatsReceiver) {

  private val selectedCandidateScoreStats: StatsReceiver =
    statsReceiver.scope("score_of_sent_candidate_times_10000")

  private val emptyScoreStats: StatsReceiver =
    statsReceiver.scope("score_of_sent_candidate_empty")

  def trackPredictionScoreStats(candidate: PushCandidate): Unit = {
    candidate.mrWeightedOpenOrNtabClickRankingProbability.foreach {
      case Some(s) =>
        selectedCandidateScoreStats
          .stat("weighted_open_or_ntab_click_ranking")
          .add((s * 10000).toFloat)
      case None =>
        emptyScoreStats.counter("weighted_open_or_ntab_click_ranking").incr()
    }
    candidate.mrWeightedOpenOrNtabClickFilteringProbability.foreach {
      case Some(s) =>
        selectedCandidateScoreStats
          .stat("weighted_open_or_ntab_click_filtering")
          .add((s * 10000).toFloat)
      case None =>
        emptyScoreStats.counter("weighted_open_or_ntab_click_filtering").incr()
    }
    candidate.mrWeightedOpenOrNtabClickRankingProbability.foreach {
      case Some(s) =>
        selectedCandidateScoreStats
          .scope(candidate.commonRecType.toString)
          .stat("weighted_open_or_ntab_click_ranking")
          .add((s * 10000).toFloat)
      case None =>
        emptyScoreStats
          .scope(candidate.commonRecType.toString)
          .counter("weighted_open_or_ntab_click_ranking")
          .incr()
    }
  }

  def refreshRequestExceptionStats(
    exception: Throwable,
    bStats: StatsReceiver
  ): Unit = {
    bStats.counter("failures").incr()
    bStats.scope("failures").counter(exception.getClass.getCanonicalName).incr()
  }

  def loggedOutRequestExceptionStats(
    exception: Throwable,
    bStats: StatsReceiver
  ): Unit = {
    bStats.counter("logged_out_failures").incr()
    bStats.scope("failures").counter(exception.getClass.getCanonicalName).incr()
  }

  def rankDistributionStats(
    candidatesDetails: Seq[CandidateDetails[PushCandidate]],
    numRecsPerTypeStat: (CommonRecommendationType => Stat)
  ): Unit = {
    candidatesDetails
      .groupBy { c =>
        c.candidate.commonRecType
      }
      .mapValues { s =>
        s.size
      }
      .foreach { case (crt, numRecs) => numRecsPerTypeStat(crt).add(numRecs) }
  }
}
