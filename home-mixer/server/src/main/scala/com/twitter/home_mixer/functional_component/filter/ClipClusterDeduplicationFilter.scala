package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.model.HomeFeatures.ClipImageClusterIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaClusterIdsFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

trait ClipClusterDeduplicationFilter extends Filter[PipelineQuery, TweetCandidate] {

  val clusterIdFeature: Feature[TweetCandidate, Map[Long, Long]]

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val (removedCandidateIds, _) = candidates.foldLeft((Set.empty[Long], Set.empty[Long])) {
      case ((removedIds, seenClusterIds), candidate) =>
        val clusterIds = candidate.features
          .getOrElse(clusterIdFeature, Map.empty[Long, Long])
          .values
          .toSet

        if (clusterIds.size == 1) {
          val clusterId = clusterIds.head
          if (seenClusterIds.contains(clusterId)) {
            (removedIds + candidate.candidate.id, seenClusterIds)
          } else {
            (removedIds, seenClusterIds + clusterId)
          }
        } else {
          (removedIds, seenClusterIds)
        }
    }

    val (removed, kept) = candidates
      .map(_.candidate)
      .partition(c => removedCandidateIds.contains(c.id))

    Stitch.value(FilterResult(kept = kept, removed = removed))
  }
}

object ClipImageClusterDeduplicationFilter extends ClipClusterDeduplicationFilter {
  override val identifier: FilterIdentifier = FilterIdentifier("ClipImageClusterDeduplication")
  override val clusterIdFeature: Feature[TweetCandidate, Map[Long, Long]] =
    ClipImageClusterIdsFeature
}

object ClipVideoClusterDeduplicationFilter extends ClipClusterDeduplicationFilter {
  override val identifier: FilterIdentifier = FilterIdentifier("ClipVideoClusterDeduplication")
  override val clusterIdFeature: Feature[TweetCandidate, Map[Long, Long]] =
    TweetMediaClusterIdsFeature
}
