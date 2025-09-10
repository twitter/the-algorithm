package com.twitter.home_mixer.product.for_you.filter

import com.twitter.product_mixer.component_library.model.candidate.trends_events.PromotedTrendNameFeature
import com.twitter.product_mixer.component_library.model.candidate.trends_events.UnifiedTrendCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * A filter that drops promoted trends
 */
object PromotedTrendFilter extends Filter[PipelineQuery, UnifiedTrendCandidate] {
  override val identifier = FilterIdentifier("PromotedTrend")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[UnifiedTrendCandidate]]
  ): Stitch[FilterResult[UnifiedTrendCandidate]] = {
    val filterResult = {

      val (removed, kept) = candidates.partition { candidate =>
        candidate.features.get(PromotedTrendNameFeature).isDefined
      }
      FilterResult(kept.map(_.candidate), removed.map(_.candidate))
    }
    Stitch.value(filterResult)
  }
}
