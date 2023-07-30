package com.X.home_mixer.product.scored_tweets.filter

import com.X.home_mixer.model.HomeFeatures.InNetworkFeature
import com.X.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.X.home_mixer.model.HomeFeatures.TweetUrlsFeature
import com.X.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CompetitorURLSeqParam
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.filter.FilterResult
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FilterIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

object OutOfNetworkCompetitorURLFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("OutOfNetworkCompetitorURL")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val competitorUrls = query.params(CompetitorURLSeqParam).toSet
    val (removed, kept) = candidates.partition(hasOutOfNetworkUrlFromCompetitor(_, competitorUrls))

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }

  def hasOutOfNetworkUrlFromCompetitor(
    candidate: CandidateWithFeatures[TweetCandidate],
    competitorUrls: Set[String]
  ): Boolean = {
    !candidate.features.getOrElse(InNetworkFeature, true) &&
    !candidate.features.getOrElse(IsRetweetFeature, false) &&
    candidate.features
      .getOrElse(TweetUrlsFeature, Seq.empty).toSet.intersect(competitorUrls).nonEmpty
  }
}
