package com.ExTwitter.home_mixer.functional_component.filter

import com.ExTwitter.home_mixer.model.HomeFeatures.ServedTweetPreviewIdsFeature
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.functional_component.filter.Filter
import com.ExTwitter.product_mixer.core.functional_component.filter.FilterResult
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch

object PreviouslyServedTweetPreviewsFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("PreviouslyServedTweetPreviews")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val servedTweetPreviewIds =
      query.features.map(_.getOrElse(ServedTweetPreviewIdsFeature, Seq.empty)).toSeq.flatten.toSet

    val (removed, kept) = candidates.partition { candidate =>
      servedTweetPreviewIds.contains(candidate.candidate.id)
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
