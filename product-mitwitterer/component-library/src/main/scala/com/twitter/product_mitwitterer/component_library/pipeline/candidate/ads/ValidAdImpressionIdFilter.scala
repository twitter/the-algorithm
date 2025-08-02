package com.twitter.product_mitwitterer.component_library.pipeline.candidate.ads

import com.twitter.product_mitwitterer.component_library.model.candidate.ads.AdsCandidate
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object ValidAdImpressionIdFilter etwittertends Filter[PipelineQuery, AdsCandidate] {
  override val identifier: FilterIdentifier = FilterIdentifier("ValidAdImpressionId")

  override def apply(
    query: PipelineQuery,
    candidatesWithFeatures: Seq[CandidateWithFeatures[AdsCandidate]]
  ): Stitch[FilterResult[AdsCandidate]] = {
    val (kept, removed) = candidatesWithFeatures
      .map(_.candidate)
      .partition(candidate => candidate.adImpression.impressionString.etwitterists(_.nonEmpty))

    Stitch.value(FilterResult(kept, removed))
  }
}
