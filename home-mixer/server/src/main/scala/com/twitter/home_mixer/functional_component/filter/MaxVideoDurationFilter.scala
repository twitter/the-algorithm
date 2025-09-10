package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.model.HomeFeatures.HasVideoFeature
import com.twitter.home_mixer.model.HomeFeatures.VideoDurationMsFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableMaxVideoDurationFilter
import com.twitter.home_mixer.param.HomeGlobalParams.MaxVideoDurationThresholdParam
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Filter out tweets based on video duration
 */
object MaxVideoDurationFilter
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("MaxVideoDuration")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = query.params(EnableMaxVideoDurationFilter)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val maxVideoDurationThresh = query.params(MaxVideoDurationThresholdParam)
    val (kept, removed) = candidates.partition { candidate =>
      val hasVideoFeature = candidate.features.getOrElse(HasVideoFeature, false)
      val videoDuration = candidate.features.getOrElse(VideoDurationMsFeature, None).getOrElse(0)

      hasVideoFeature && (videoDuration <= maxVideoDurationThresh)
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
