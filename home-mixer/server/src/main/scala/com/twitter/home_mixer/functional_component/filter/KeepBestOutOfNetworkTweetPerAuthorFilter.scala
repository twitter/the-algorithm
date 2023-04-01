package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object KeepBestOutOfNetworkTweetPerAuthorFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("KeepBestOutOfNetworkTweetPerAuthor")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    // Set containing best OON tweet for each authorId
    val bestCandidatesForAuthorId = candidates
      .filter(!_.features.getOrElse(InNetworkFeature, true))
      .groupBy(_.features.getOrElse(AuthorIdFeature, None))
      .values.map(_.maxBy(_.features.getOrElse(ScoreFeature, None)))
      .toSet

    val (removed, kept) = candidates.partition { candidate =>
      !candidate.features.getOrElse(InNetworkFeature, true) &&
      !bestCandidatesForAuthorId.contains(candidate)
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
