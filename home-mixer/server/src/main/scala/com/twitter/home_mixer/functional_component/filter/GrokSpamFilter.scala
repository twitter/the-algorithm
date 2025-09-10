package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.param.HomeGlobalParams.EnableGrokSpamFilter
import com.twitter.home_mixer.model.HomeFeatures.GrokAnnotationsFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Filter out spam tweets based on grok annotations
 */
object GrokSpamFilter
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("GrokSpam")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = query.params(EnableGrokSpamFilter)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val (removed, kept) = candidates.partition { candidate =>
      val annotations = candidate.features.getOrElse(GrokAnnotationsFeature, None)
      annotations.flatMap(_.metadata.map(_.isSpam)).getOrElse(false)
    }

    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}
