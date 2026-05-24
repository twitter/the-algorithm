package com.twitter.home_mixer.functional_component.filter

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.model.HomeFeatures.BookmarkedTweetTimestamp
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.util.Time

object WeeklyBookmarkFilter extends Filter[PipelineQuery, TweetCandidate] {
  override val identifier: FilterIdentifier = FilterIdentifier("WeeklyBookmark")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val (keptCandidates, removedCandidates) = candidates.partition { filterCandidate =>
      filterCandidate.features
        .get(BookmarkedTweetTimestamp).exists { timestamp =>
          val aWeekAgo = Time.now - 7.days
          Time.fromMilliseconds(timestamp) >= aWeekAgo
        }
    }

    Stitch.value(
      FilterResult(
        kept = keptCandidates.map(_.candidate),
        removed = removedCandidates.map(_.candidate)))
  }
}
