package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.model.HomeFeatures.VideoAspectRatioFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Param

/**
 * Filter for making sure all video posts in a carousel has consistent aspect ratio
 */
case class ConsistentAspectRatioFilter(
  allowVerticalVideosParam: Param[Boolean],
  allowHorizontalVideosParam: Param[Boolean])
    extends Filter[PipelineQuery, TweetCandidate] {

  /** @see [[FilterIdentifier]] */
  override val identifier: FilterIdentifier = FilterIdentifier("ConsistentAspectRatio")

  /**
   * Filter the list of candidates
   *
   * @return a FilterResult including both the list of kept candidate and the list of removed candidates
   */
  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val (hasAspectRatio, doesNotHaveAspectRatio) = candidates
      .partition(_.features.getOrElse(VideoAspectRatioFeature, None).nonEmpty)
    if (hasAspectRatio.nonEmpty) {
      val isHorizontal =
        (query.params(allowVerticalVideosParam), query.params(allowHorizontalVideosParam)) match {
          case (false, _) => false
          case (_, false) => true
          case _ => // If both video types are allowed, take the first video's aspect ratio
            val aspectRatioFirstVideo =
              hasAspectRatio.head.features.getOrElse(VideoAspectRatioFeature, None).get
            aspectRatioFirstVideo > 1.0
        }
      val (consistentAspectRatio, differentAspectRatio) = hasAspectRatio.partition { candidate =>
        candidate.features.getOrElse(VideoAspectRatioFeature, None).get > 1.0 == isHorizontal
      }
      Stitch.value(
        FilterResult(
          kept = consistentAspectRatio.map(_.candidate),
          removed = ((differentAspectRatio ++ doesNotHaveAspectRatio)).map(_.candidate)))
    } else {
      Stitch.value(
        FilterResult(kept = Seq.empty, removed = doesNotHaveAspectRatio.map(_.candidate)))
    }
  }
}
