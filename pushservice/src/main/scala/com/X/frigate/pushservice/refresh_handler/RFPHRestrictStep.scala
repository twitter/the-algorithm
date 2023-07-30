package com.X.frigate.pushservice.refresh_handler

import com.X.finagle.stats.Stat
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.CandidateDetails
import com.X.frigate.common.base.TargetUser
import com.X.frigate.common.candidate.TargetABDecider
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.target.TargetScoringDetails

class RFPHRestrictStep()(implicit stats: StatsReceiver) {

  private val statsReceiver: StatsReceiver = stats.scope("RefreshForPushHandler")
  private val restrictStepStats: StatsReceiver = statsReceiver.scope("restrict")
  private val restrictStepNumCandidatesDroppedStat: Stat =
    restrictStepStats.stat("candidates_dropped")

  /**
   * Limit the number of candidates that enter the Take step
   */
  def restrict(
    target: TargetUser with TargetABDecider with TargetScoringDetails,
    candidates: Seq[CandidateDetails[PushCandidate]]
  ): (Seq[CandidateDetails[PushCandidate]], Seq[CandidateDetails[PushCandidate]]) = {
    if (target.params(PushFeatureSwitchParams.EnableRestrictStep)) {
      val restrictSizeParam = PushFeatureSwitchParams.RestrictStepSize
      val (newCandidates, filteredCandidates) = candidates.splitAt(target.params(restrictSizeParam))
      val numDropped = candidates.length - newCandidates.length
      restrictStepNumCandidatesDroppedStat.add(numDropped)
      (newCandidates, filteredCandidates)
    } else (candidates, Seq.empty)
  }
}
