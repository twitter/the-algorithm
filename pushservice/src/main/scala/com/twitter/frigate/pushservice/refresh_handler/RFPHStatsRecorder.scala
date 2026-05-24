package com.twitter.frigate.pushservice.refresh_handler

import com.twitter.finagle.stats.Stat
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base.CandidateDetails
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.thriftscala.CommonRecommendationType

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
