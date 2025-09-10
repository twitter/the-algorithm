package com.twitter.tweet_mixer.functional_component.filter

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.feature.MediaClusterIdFeature
import com.twitter.tweet_mixer.model.request.HasVideoType
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableMediaClusterIdDedupFilter

case class MediaClusterIdDedupFilter(
  candidatePipelinesToExclude: Set[CandidatePipelineIdentifier] = Set.empty)
    extends Filter[PipelineQuery with HasVideoType, TweetCandidate]
    with Filter.Conditionally[PipelineQuery with HasVideoType, TweetCandidate]
    with ShouldIgnoreCandidatePipelinesFilter {

  override val identifier: FilterIdentifier = FilterIdentifier("MediaClusterIdDedup")

  override def onlyIf(
    query: PipelineQuery with HasVideoType,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = query.params(EnableMediaClusterIdDedupFilter)

  override def apply(
    query: PipelineQuery with HasVideoType,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val (removedCandidateIds, _) = candidates.foldLeft((Set[Long](), Set[Long]())) {
      case ((removeIds, clusterIdsToExclude), candidate) =>
        if (shouldIgnore(candidate)) {
          (removeIds, clusterIdsToExclude + candidate.candidate.id)
        } else {
          candidate.features.getOrElse(MediaClusterIdFeature, None) match {
            case Some(clusterId) =>
              if (clusterIdsToExclude.contains(clusterId)) {
                (removeIds + candidate.candidate.id, clusterIdsToExclude)
              } else {
                (removeIds, clusterIdsToExclude + clusterId)
              }
            case _ => (removeIds, clusterIdsToExclude)
          }
        }
    }

    val (removed, kept) = candidates
      .map(_.candidate)
      .partition(candidate => removedCandidateIds.contains(candidate.id))

    Stitch.value(FilterResult(kept = kept, removed = removed))
  }
}
