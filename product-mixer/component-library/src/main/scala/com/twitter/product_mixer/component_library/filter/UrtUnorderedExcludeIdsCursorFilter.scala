package com.twitter.product_mixer.component_library.filter

import com.twitter.product_mixer.component_library.model.cursor.UrtUnorderedExcludeIdsCursor
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.HasPipelineCursor
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

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
