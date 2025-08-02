package com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.AddEntriesTimelineInstruction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.ShowAlert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class AddEntriesWithReplaceAndShowAlertInstructionBuilder[Query <: PipelineQuery](
  override val includeInstruction: IncludeInstruction[Query] = AlwaysInclude)
    etwittertends UrtInstructionBuilder[Query, AddEntriesTimelineInstruction] {

  override def build(
    query: Query,
    entries: Seq[TimelineEntry]
  ): Seq[AddEntriesTimelineInstruction] = {
    if (includeInstruction(query, entries)) {
      val entriesToAdd = entries
        .filterNot(_.isInstanceOf[ShowAlert])
        .filter(_.entryIdToReplace.isEmpty)
      if (entriesToAdd.nonEmpty) Seq(AddEntriesTimelineInstruction(entriesToAdd))
      else Seq.empty
    } else
      Seq.empty
  }
}
