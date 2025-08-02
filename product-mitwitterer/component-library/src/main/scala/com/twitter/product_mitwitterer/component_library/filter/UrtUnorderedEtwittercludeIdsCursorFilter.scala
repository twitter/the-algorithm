package com.twitter.product_mitwitterer.component_library.filter

import com.twitter.product_mitwitterer.component_library.model.cursor.UrtUnorderedEtwittercludeIdsCursor
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

case class UrtUnorderedEtwittercludeIdsCursorFilter[
  Candidate <: UniversalNoun[Long],
  Query <: PipelineQuery with HasPipelineCursor[UrtUnorderedEtwittercludeIdsCursor]
]() etwittertends Filter[Query, Candidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("UnorderedEtwittercludeIdsCursor")

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = {

    val etwittercludeIds = query.pipelineCursor.map(_.etwittercludedIds.toSet).getOrElse(Set.empty)
    val (kept, removed) =
      candidates.map(_.candidate).partition(candidate => !etwittercludeIds.contains(candidate.id))

    val filterResult = FilterResult(kept = kept, removed = removed)
    Stitch.value(filterResult)
  }
}
