package com.X.product_mixer.component_library.filter

import com.X.product_mixer.component_library.model.candidate.TweetAuthorIdFeature
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.filter.FilterResult
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FilterIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

/**
 * A filter that checks for presence of a successfully hydrated [[TweetAuthorIdFeature]]
 */
case class HasAuthorIdFeatureFilter[Candidate <: TweetCandidate]()
    extends Filter[PipelineQuery, Candidate] {

  override val identifier = FilterIdentifier("HasAuthorIdFeature")

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = {
    val (kept, removed) = candidates.partition(_.features.getTry(TweetAuthorIdFeature).isReturn)
    Stitch.value(FilterResult(kept.map(_.candidate), removed.map(_.candidate)))
  }
}
