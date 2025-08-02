package com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.ClearCacheTimelineInstruction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class ClearCacheInstructionBuilder[Query <: PipelineQuery](
  override val includeInstruction: IncludeInstruction[Query] = AlwaysInclude)
    etwittertends UrtInstructionBuilder[Query, ClearCacheTimelineInstruction] {

  override def build(
    query: Query,
    entries: Seq[TimelineEntry]
  ): Seq[ClearCacheTimelineInstruction] =
    if (includeInstruction(query, entries)) Seq(ClearCacheTimelineInstruction()) else Seq.empty
}
