package com.ExTwitter.follow_recommendations.common.base

import com.ExTwitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.ExTwitter.stitch.Stitch

/**
 * SideEffectsUtil applies side effects to the intermediate candidate results from a recommendation flow pipeline.
 *
 * @tparam Target target to recommend the candidates
 * @tparam Candidate candidate type to rank
 */
trait SideEffectsUtil[Target, Candidate] {
  def applySideEffects(
    target: Target,
    candidateSources: Seq[CandidateSource[Target, Candidate]],
    candidatesFromCandidateSources: Seq[Candidate],
    mergedCandidates: Seq[Candidate],
    filteredCandidates: Seq[Candidate],
    rankedCandidates: Seq[Candidate],
    transformedCandidates: Seq[Candidate],
    truncatedCandidates: Seq[Candidate],
    results: Seq[Candidate]
  ): Stitch[Unit] = Stitch.Unit
}
