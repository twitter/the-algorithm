package com.ExTwitter.cr_mixer.ranker

import com.ExTwitter.cr_mixer.model.BlendedCandidate
import com.ExTwitter.cr_mixer.model.RankedCandidate
import com.ExTwitter.util.Future
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
