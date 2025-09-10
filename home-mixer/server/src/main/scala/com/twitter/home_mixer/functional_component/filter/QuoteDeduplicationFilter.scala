package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.model.HomeFeatures.QuotedTweetIdFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Remove base tweet if it is quoted in another candidate
 */
object QuoteDeduplicationFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("QuoteDeduplication")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val quotedTweets = candidates.flatMap(_.features.getOrElse(QuotedTweetIdFeature, None)).toSet

    val (removed, kept) =
      candidates.map(_.candidate).partition(candidate => quotedTweets.contains(candidate.id))

    Stitch.value(FilterResult(kept = kept, removed = removed))
  }
}
