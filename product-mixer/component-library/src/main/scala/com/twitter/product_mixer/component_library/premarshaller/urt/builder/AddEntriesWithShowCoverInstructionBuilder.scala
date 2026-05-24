package com.twitter.product_mixer.component_library.premarshaller.urt.builder

import com.twitter.product_mixer.core.model.marshalling.response.urt.AddEntriesTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.Cover
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.pipeline.PipelineQuery

/**
 * Build AddEntries instruction with special handling for Covers.
 *
 * Cover Entries should be collected and transformed into ShowCover instructions. These should be
 * filtered out of the AddEntries instruction. We avoid doing this as part of the regular
 * AddEntriesInstructionBuilder because covers are used only used when using a Flip Pipeline and
 * detecting cover entries takes linear time.
 */
case class AddEntriesWithShowCoverInstructionBuilder[Query <: PipelineQuery](
  override val includeInstruction: IncludeInstruction[Query] = AlwaysInclude)
    extends UrtInstructionBuilder[Query, AddEntriesTimelineInstruction] {
  override def build(
    query: Query,
    entries: Seq[TimelineEntry]
  ): Seq[AddEntriesTimelineInstruction] = {
    if (includeInstruction(query, entries)) {
      val entriesToAdd = entries.filterNot(_.isInstanceOf[Cover])
      if (entriesToAdd.nonEmpty) Seq(AddEntriesTimelineInstruction(entriesToAdd)) else Seq.empty
    } else
      Seq.empty
  }
}
