package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceSignalFeature
import com.twitter.home_mixer.model.candidate_source.SourceSignal
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object CandidateSourceDiversityListwiseRescoringProvider
    extends ListwiseRescoringProvider[
      CandidateWithFeatures[TweetCandidate],
      (hmt.ServedType, Option[SourceSignal])
    ] {

  override def groupByKey(
    candidate: CandidateWithFeatures[TweetCandidate]
  ): Option[(hmt.ServedType, Option[SourceSignal])] = {
    val servedType = candidate.features.get(ServedTypeFeature)
    val sourceSignalOpt = candidate.features.getOrElse(SourceSignalFeature, None)
    Some((servedType, sourceSignalOpt))
  }

  override def candidateRescoringFactor(
    query: PipelineQuery,
    candidate: CandidateWithFeatures[TweetCandidate],
    index: Int
  ): Double =
    candidate.features.get(ServedTypeFeature) match {
      case hmt.ServedType.ForYouInNetwork => 1.0
      case _ =>
        if (query.params(ScoredTweetsParam.EnableCandidateSourceDiversityDecay)) {
          val decayFactor = query.params(ScoredTweetsParam.CandidateSourceDiversityDecayFactor)
          val floor = query.params(ScoredTweetsParam.CandidateSourceDiversityFloor)
          candidateSourceDiversityRescorer(index = index, decayFactor = decayFactor, floor = floor)
        } else 1.0
    }

  /**
   * Re-scoring multiplier to apply to multiple tweets from the same candidate source and reason.
   * Provides an exponential decay based discount by position (with a floor).
   */
  def candidateSourceDiversityRescorer(
    index: Int,
    decayFactor: Double,
    floor: Double
  ): Double = (1 - floor) * Math.pow(decayFactor, index) + floor
}
