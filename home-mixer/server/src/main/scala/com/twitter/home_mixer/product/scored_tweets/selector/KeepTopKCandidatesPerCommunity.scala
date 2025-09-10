package com.twitter.home_mixer.product.scored_tweets.selector

import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityIdFeature
import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.selector.SelectorResult
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery

case class KeepTopKCandidatesPerCommunity(override val pipelineScope: CandidateScope)
    extends Selector[PipelineQuery] {

  private val MaxCandidatesPerCommunity = 1
  private val MaxCommunityCandidates = 3

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val (selectedCandidates, otherCandidates) = remainingCandidates.partition { candidate =>
      pipelineScope.contains(candidate) &&
      candidate.features.getOrElse(CommunityIdFeature, None).isDefined
    }

    val filteredCandidates = selectedCandidates
      .groupBy { candidate => candidate.features.getOrElse(CommunityIdFeature, None) }
      .values.flatMap {
        _.sortBy(_.features.getOrElse(ScoreFeature, None)).reverse.take(MaxCandidatesPerCommunity)
      }
      .toSeq.sortBy(_.features.getOrElse(ScoreFeature, None)).reverse.take(MaxCommunityCandidates)

    val updatedCandidates = otherCandidates ++ filteredCandidates
    SelectorResult(remainingCandidates = updatedCandidates, result = result)
  }
}
