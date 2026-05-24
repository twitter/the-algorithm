package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.model.HomeFeatures.EarlybirdFeature
import com.twitter.home_mixer.model.HomeFeatures.HasVideoFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaCompletionRateFeature
import com.twitter.home_mixer.model.HomeFeatures.VideoDurationMsFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableMinVideoDurationFilter
import com.twitter.home_mixer.param.HomeGlobalParams.MinVideoDurationThresholdParam
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
object MinVideoDurationFilter
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("MinVideoDuration")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = query.params(EnableMinVideoDurationFilter)

  private val CompletionRateThreshold = 90
  private val FavThreshold = 30
  private val LongDuration = 5000

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val minVideoDurationThresh = query.params(MinVideoDurationThresholdParam)
    val (removed, kept) = candidates.partition { candidate =>
      val hasVideoFeature = candidate.features.getOrElse(HasVideoFeature, false)
      val completionRate =
        candidate.features.getOrElse(TweetMediaCompletionRateFeature, None).getOrElse(0.0)
      val videoDuration = candidate.features.getOrElse(VideoDurationMsFeature, None).getOrElse(0)
      val ebFeatures = candidate.features.getOrElse(EarlybirdFeature, None)
      val favCount = ebFeatures.flatMap(_.favCountV2)

      val sketchyCompletionRate =
        favCount.forall(_ > FavThreshold) &&
          completionRate > CompletionRateThreshold &&
          videoDuration > LongDuration

      val lowDuration = videoDuration <= minVideoDurationThresh

      hasVideoFeature && (lowDuration || sketchyCompletionRate)
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
