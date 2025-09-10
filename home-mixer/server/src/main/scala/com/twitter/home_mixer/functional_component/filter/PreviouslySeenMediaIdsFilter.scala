package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.model.HomeFeatures.ImpressedMediaIds
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaIdsFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Filter out users' previously seen mediaIds
 */
object PreviouslySeenMediaIdsFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("PreviouslySeenMediaIds")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val seenMediaIds =
      query.features.map(_.getOrElse(ImpressedMediaIds, Seq.empty)).toSet.flatten

    val (removed, kept) = candidates.partition { candidate =>
      candidate.features.getOrElse(TweetMediaIdsFeature, Seq.empty).exists(seenMediaIds.contains)
    }

    val filterResult = FilterResult(
      kept = kept.map(_.candidate),
      removed = removed.map(_.candidate)
    )

    Stitch.value(filterResult)
  }
}
