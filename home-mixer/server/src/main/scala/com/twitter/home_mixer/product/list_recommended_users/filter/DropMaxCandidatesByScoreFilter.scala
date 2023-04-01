package com.twitter.home_mixer.product.list_recommended_users.filter

import com.twitter.home_mixer.product.list_recommended_users.model.ListFeatures.ScoreFeature
import com.twitter.product_mixer.component_library.model.candidate.UserCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object DropMaxCandidatesByScoreFilter extends Filter[PipelineQuery, UserCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("DropMaxCandidatesByScore")

  private val MaxSimilarUserCandidates = 1000

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[UserCandidate]]
  ): Stitch[FilterResult[UserCandidate]] = {

    val sortedCandidates = candidates.sortBy(-_.features.getOrElse(ScoreFeature, 0.0))

    val (kept, removed) = sortedCandidates.map(_.candidate).splitAt(MaxSimilarUserCandidates)

    Stitch.value(FilterResult(kept, removed))
  }
}
