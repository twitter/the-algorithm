package com.ExTwitter.product_mixer.component_library.filter

import com.ExTwitter.product_mixer.component_library.model.candidate.TweetAuthorIdFeature
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.functional_component.filter.Filter
import com.ExTwitter.product_mixer.core.functional_component.filter.FilterResult
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch

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
