package com.twitter.cr_mixer.ranker

import com.twitter.cr_mixer.model.BlendedCandidate
import com.twitter.cr_mixer.model.RankedCandidate
import com.twitter.util.Future
import javax.inject.Singleton

/**
 * Keep the same order as the input.
 */
@Singleton
class DefaultRanker() {
  def rank(
    candidates: Seq[BlendedCandidate],
  ): Future[Seq[RankedCandidate]] = {
    val candidateSize = candidates.size
    val rankedCandidates = candidates.zipWithIndex.map {
      case (candidate, index) =>
        candidate.toRankedCandidate((candidateSize - index).toDouble)
    }
    Future.value(rankedCandidates)
  }
}
