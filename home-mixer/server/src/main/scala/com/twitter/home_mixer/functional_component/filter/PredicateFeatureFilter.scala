package com.twitter.home_mixer.functional_component.filter

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Predicate which will be applied to each candidate. True indicates that the candidate will be
 * @tparam Candidate - the type of the candidate
 */
trait ShouldKeepCandidate {
  def apply(features: FeatureMap): Boolean
}

object PredicateFeatureFilter {

  /**
   * Builds a simple Filter out of a predicate function from the candidate to a boolean. For clarity,
   * we recommend including the name of the shouldKeepCandidate parameter.
   *
   * @param identifier A FilterIdentifier for the new filter
   * @param shouldKeepCandidate A predicate function. Candidates will be kept when
   *                            this function returns True.
   */
  def fromPredicate[Candidate <: UniversalNoun[Any]](
    identifier: FilterIdentifier,
    shouldKeepCandidate: ShouldKeepCandidate
  ): Filter[PipelineQuery, Candidate] = {
    val i = identifier

    new Filter[PipelineQuery, Candidate] {
      override val identifier: FilterIdentifier = i

      /**
       * Filter the list of candidates
       *
       * @return a FilterResult including both the list of kept candidate and the list of removed candidates
       */
      override def apply(
        query: PipelineQuery,
        candidates: Seq[CandidateWithFeatures[Candidate]]
      ): Stitch[FilterResult[Candidate]] = {
        val allowedIds = candidates
          .filter(candidate => shouldKeepCandidate(candidate.features)).map(_.candidate.id).toSet

        val (keptCandidates, removedCandidates) = candidates.map(_.candidate).partition {
          candidate => allowedIds.contains(candidate.id)
        }

        Stitch.value(FilterResult(kept = keptCandidates, removed = removedCandidates))
      }
    }
  }
}
