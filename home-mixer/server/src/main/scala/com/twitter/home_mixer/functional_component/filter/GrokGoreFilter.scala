package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.param.HomeGlobalParams.EnableGrokGoreFilter
import com.twitter.home_mixer.model.HomeFeatures.GrokAnnotationsFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Filter out gore tweets based on grok annotations
 */
object GrokGoreFilter
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("GrokGore")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = query.params(EnableGrokGoreFilter)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val (removed, kept) = candidates.partition { candidate =>
      val annotations = candidate.features.getOrElse(GrokAnnotationsFeature, None)
      annotations.flatMap(_.metadata.map(_.isGore)).getOrElse(false)
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
