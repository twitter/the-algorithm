package com.twitter.follow_recommendations.common.base

import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.stitch.Stitch

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
