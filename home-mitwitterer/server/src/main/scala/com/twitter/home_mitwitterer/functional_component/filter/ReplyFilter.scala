package com.twitter.home_mitwitterer.functional_component.filter

import com.twitter.home_mitwitterer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object ReplyFilter etwittertends Filter[PipelineQuery, TweetCandidate] {
  override val identifier: FilterIdentifier = FilterIdentifier("Reply")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val (kept, removed) = candidates
      .partition { candidate =>
        candidate.features.getOrElse(InReplyToTweetIdFeature, None).isEmpty
      }

    val filterResult = FilterResult(
      kept = kept.map(_.candidate),
      removed = removed.map(_.candidate)
    )

    Stitch.value(filterResult)
  }
}
