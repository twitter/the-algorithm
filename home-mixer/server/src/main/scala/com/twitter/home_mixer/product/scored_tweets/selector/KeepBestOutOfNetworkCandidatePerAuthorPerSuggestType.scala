package com.ExTwitter.home_mixer.product.scored_tweets.selector

import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.ExTwitter.product_mixer.core.functional_component.common.CandidateScope
import com.ExTwitter.product_mixer.core.functional_component.selector.Selector
import com.ExTwitter.product_mixer.core.functional_component.selector.SelectorResult
import com.ExTwitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery

case class KeepBestOutOfNetworkCandidatePerAuthorPerSuggestType(
  override val pipelineScope: CandidateScope)
    extends Selector[PipelineQuery] {

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val (selectedCandidates, otherCandidates) =
      remainingCandidates.partition(candidate =>
        pipelineScope.contains(candidate) && !candidate.features.getOrElse(InNetworkFeature, true))

    val filteredCandidates = selectedCandidates
      .groupBy { candidate =>
        (
          candidate.features.getOrElse(AuthorIdFeature, None),
          candidate.features.getOrElse(SuggestTypeFeature, None)
        )
      }
      .values.map(_.maxBy(_.features.getOrElse(ScoreFeature, None)))
      .toSeq

    val updatedCandidates = otherCandidates ++ filteredCandidates
    SelectorResult(remainingCandidates = updatedCandidates, result = result)
  }
}
