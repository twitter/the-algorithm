package com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.AddEntriesTimelineInstruction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class AddEntriesInstructionBuilder[Query <: PipelineQuery](
  override val includeInstruction: IncludeInstruction[Query] = AlwaysInclude)
    etwittertends UrtInstructionBuilder[Query, AddEntriesTimelineInstruction] {

  override def build(
    query: Query,
    entries: Seq[TimelineEntry]
  ): Seq[AddEntriesTimelineInstruction] = {
    if (entries.nonEmpty && includeInstruction(query, entries))
      Seq(AddEntriesTimelineInstruction(entries))
    else Seq.empty
  }
}
