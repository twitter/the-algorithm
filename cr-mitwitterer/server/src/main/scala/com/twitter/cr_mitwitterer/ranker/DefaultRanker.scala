package com.twitter.cr_mitwitterer.ranker

import com.twitter.cr_mitwitterer.model.BlendedCandidate
import com.twitter.cr_mitwitterer.model.RankedCandidate
import com.twitter.util.Future
import javatwitter.inject.Singleton

/**
 * Keep the same order as the input.
 */
@Singleton
class DefaultRanker() {
  def rank(
    candidates: Seq[BlendedCandidate],
  ): Future[Seq[RankedCandidate]] = {
    val candidateSize = candidates.size
    val rankedCandidates = candidates.zipWithIndetwitter.map {
      case (candidate, indetwitter) =>
        candidate.toRankedCandidate((candidateSize - indetwitter).toDouble)
    }
    Future.value(rankedCandidates)
  }
}
