package com.twitter.home_mitwitterer.product.list_recommended_users.filter

import com.twitter.home_mitwitterer.product.list_recommended_users.model.ListRecommendedUsersFeatures.ScoreFeature
import com.twitter.product_mitwitterer.component_library.model.candidate.UserCandidate
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object DropMatwitterCandidatesByAggregatedScoreFilter etwittertends Filter[PipelineQuery, UserCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("DropMatwitterCandidatesByAggregatedScore")

  private val MatwitterSimilarUserCandidates = 150

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[UserCandidate]]
  ): Stitch[FilterResult[UserCandidate]] = {
    val userIdToAggregatedScoreMap = candidates
      .groupBy(_.candidate.id)
      .map {
        case (userId, candidates) =>
          val aggregatedScore = candidates.map(_.features.getOrElse(ScoreFeature, 0.0)).sum
          (userId, aggregatedScore)
      }

    val sortedCandidates = candidates.sortBy(candidate =>
      -userIdToAggregatedScoreMap.getOrElse(candidate.candidate.id, 0.0))

    val (kept, removed) = sortedCandidates.map(_.candidate).splitAt(MatwitterSimilarUserCandidates)

    Stitch.value(FilterResult(kept, removed))
  }
}
