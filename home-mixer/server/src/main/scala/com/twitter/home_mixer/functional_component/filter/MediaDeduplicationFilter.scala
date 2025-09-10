package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.model.HomeFeatures.TweetMediaIdsFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Remove duplicate media in the same payload
 */
object MediaIdDeduplicationFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("MediaDeduplication")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val (removedCandidateIds, _) = candidates.foldLeft((Set.empty[Long], Set.empty[Long])) {
      case ((removedIds, seenMediaIds), candidate) =>
        val mediaIds = candidate.features.getOrElse(TweetMediaIdsFeature, Seq.empty[Long])
        if (mediaIds.size == 1) {
          val mediaId = mediaIds.head
          if (seenMediaIds.contains(mediaId)) {
            (removedIds + candidate.candidate.id, seenMediaIds)
          } else {
            (removedIds, seenMediaIds + mediaId)
          }
        } else {
          (removedIds, seenMediaIds)
        }
    }

    val (removed, kept) = candidates.map(_.candidate)
      .partition(c => removedCandidateIds.contains(c.id))
    Stitch.value(FilterResult(kept = kept, removed = removed))
  }
}
