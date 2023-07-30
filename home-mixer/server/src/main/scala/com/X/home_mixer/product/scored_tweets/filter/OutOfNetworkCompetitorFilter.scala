package com.X.home_mixer.product.scored_tweets.filter

import com.X.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.X.home_mixer.model.HomeFeatures.InNetworkFeature
import com.X.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.X.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CompetitorSetParam
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.filter.FilterResult
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FilterIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

object OutOfNetworkCompetitorFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("OutOfNetworkCompetitor")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val competitorAuthors = query.params(CompetitorSetParam)
    val (removed, kept) =
      candidates.partition(isOutOfNetworkTweetFromCompetitor(_, competitorAuthors))

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }

  def isOutOfNetworkTweetFromCompetitor(
    candidate: CandidateWithFeatures[TweetCandidate],
    competitorAuthors: Set[Long]
  ): Boolean = {
    !candidate.features.getOrElse(InNetworkFeature, true) &&
    !candidate.features.getOrElse(IsRetweetFeature, false) &&
    candidate.features.getOrElse(AuthorIdFeature, None).exists(competitorAuthors.contains)
  }
}
