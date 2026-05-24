package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery

/**
 * Defines a listwise rescoring provider for use of rescoring a candidate dependent on the other candidates that
 * co-exist with it.
 *
 * Requires:
 *  1) groupByKey: How to define the set of candidates that are dependent on each other for scoring.
 *  2) candidateRescoringFactor: Compute the final rescoring factor for each candidate in the group.
 *
 *  This rescorer will sort the grouped candidates by their current score, and then apply the rescoring factors to
 *  each candidate in their group.
 */
trait ListwiseRescoringProvider[C <: CandidateWithFeatures[TweetCandidate], K] {

  /**
   * Fetch the key used to create groups of candidates
   */
  def groupByKey(candidate: C): Option[K]

  /**
   * Compute the factor for each candidate based on position (zero-based)
   * relative to other candidates associated with the same key
   */
  def candidateRescoringFactor(query: PipelineQuery, candidate: C, index: Int): Double

  /**
   * Group by the specified key (e.g. authors, likers, followers)
   * Sort each group by score in descending order
   * Determine the rescoring factor based on the position of each candidate
   */
  def apply(
    query: PipelineQuery,
    candidates: Seq[C]
  ): Map[Long, Double] = candidates
    .groupBy(groupByKey)
    .flatMap {
      case (Some(_), groupedCandidates) =>
        val sortedCandidates = groupedCandidates
          .sortBy(_.features.getOrElse(ScoreFeature, None).getOrElse(0.0))(Ordering.Double.reverse)

        sortedCandidates.zipWithIndex.map {
          case (candidate, index) =>
            candidate.candidate.id -> candidateRescoringFactor(query, candidate, index)
        }

      case _ => Map.empty
    }
}
