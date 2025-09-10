package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.param.HomeGlobalParams.EnableNsfwFilter
import com.twitter.home_mixer.param.HomeGlobalParams.EnableSoftNsfwFilter
import com.twitter.home_mixer.model.HomeFeatures.GrokAnnotationsFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Filter out NSFW tweets based on grok annotations
 */
object GrokNsfwFilter
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("GrokNsfw")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = query.params(EnableNsfwFilter)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val (removed, kept) = candidates.partition { candidate =>
      val annotations = candidate.features.getOrElse(GrokAnnotationsFeature, None)
      val isNsfw = annotations.flatMap(_.metadata.map(_.isNsfw)).getOrElse(false)
      val isSoftNsfw = annotations.flatMap(_.metadata.map(_.isSoftNsfw)).getOrElse(false)

      if (query.params(EnableSoftNsfwFilter)) {
        isNsfw || isSoftNsfw
      } else {
        isNsfw
      }
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
