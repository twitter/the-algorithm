package com.X.product_mixer.component_library.filter

import com.X.product_mixer.component_library.model.cursor.UrtUnorderedExcludeIdsCursor
import com.X.product_mixer.core.functional_component.filter.Filter
import com.X.product_mixer.core.functional_component.filter.FilterResult
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.common.identifier.FilterIdentifier
import com.X.product_mixer.core.pipeline.HasPipelineCursor
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

case class UrtUnorderedExcludeIdsCursorFilter[
  Candidate <: UniversalNoun[Long],
  Query <: PipelineQuery with HasPipelineCursor[UrtUnorderedExcludeIdsCursor]
]() extends Filter[Query, Candidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("UnorderedExcludeIdsCursor")

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[FilterResult[Candidate]] = {

    val excludeIds = query.pipelineCursor.map(_.excludedIds.toSet).getOrElse(Set.empty)
    val (kept, removed) =
      candidates.map(_.candidate).partition(candidate => !excludeIds.contains(candidate.id))

    val filterResult = FilterResult(kept = kept, removed = removed)
    Stitch.value(filterResult)
  }
}
