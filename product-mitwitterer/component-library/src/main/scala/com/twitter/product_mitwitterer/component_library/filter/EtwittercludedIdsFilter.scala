package com.twitter.product_mitwitterer.component_library.filter

import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasEtwittercludedIds
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

case class EtwittercludedIdsFilter[
  Query <: PipelineQuery with HasEtwittercludedIds,
  Candidate <: UniversalNoun[Long]
]() etwittertends Filter[Query, Candidate] {
  override val identifier: FilterIdentifier = FilterIdentifier("EtwittercludedIds")

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = {
    val (kept, removed) =
      candidates.map(_.candidate).partition(candidate => !query.etwittercludedIds.contains(candidate.id))

    val filterResult = FilterResult(kept = kept, removed = removed)
    Stitch.value(filterResult)
  }
}
