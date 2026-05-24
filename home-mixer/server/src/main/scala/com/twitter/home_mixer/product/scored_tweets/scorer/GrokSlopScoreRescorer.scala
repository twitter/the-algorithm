package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.GrokSlopScoreFeature
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.GrokSlopScoreDecayValueParam
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object GrokSlopScoreRescorer {

  private val treatmentValue = 3L

  private val statsReceiver = DefaultStatsReceiver.scope("GrokSlopScoreRescorer")
  private val numRescoredCandidatesCounter = statsReceiver.counter("rescored")
  private val totalCandidatesCounter = statsReceiver.counter("total")

  private def onlyIf(query: PipelineQuery): Boolean = {
    query.params(GrokSlopScoreDecayValueParam) < 1.0
  }

  def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Map[Long, Double] = {
    if (!onlyIf(query)) {
      return candidates.map { candidate => candidate.candidate.id -> 1.0 }.toMap
    }

    val decayValue = query.params(GrokSlopScoreDecayValueParam)

    val rescoredCandidates = candidates.map { candidate =>
      val featureValue = candidate.features.getOrElse(GrokSlopScoreFeature, None)
      val rescoreFactor = if (featureValue.contains(treatmentValue)) decayValue else 1.0
      candidate.candidate.id -> rescoreFactor
    }

    numRescoredCandidatesCounter.incr(rescoredCandidates.count(_._2 != 1.0))
    totalCandidatesCounter.incr(candidates.size)

    rescoredCandidates.toMap
  }
}
