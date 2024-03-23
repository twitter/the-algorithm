package com.ExTwitter.product_mixer.component_library.filter

import com.ExTwitter.product_mixer.core.functional_component.filter.Filter
import com.ExTwitter.product_mixer.core.functional_component.filter.FilterResult
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.UniversalNoun
import com.ExTwitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasExcludedIds
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch

case class ExcludedIdsFilter[
  Query <: PipelineQuery with HasExcludedIds,
  Candidate <: UniversalNoun[Long]
]() extends Filter[Query, Candidate] {
  override val identifier: FilterIdentifier = FilterIdentifier("ExcludedIds")

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = {
    val (kept, removed) =
      candidates.map(_.candidate).partition(candidate => !query.excludedIds.contains(candidate.id))

    val filterResult = FilterResult(kept = kept, removed = removed)
    Stitch.value(filterResult)
  }
}
