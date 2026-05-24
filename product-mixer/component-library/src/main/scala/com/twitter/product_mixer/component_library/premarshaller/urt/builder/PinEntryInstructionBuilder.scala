package com.twitter.product_mixer.component_library.premarshaller.urt.builder

import com.twitter.product_mixer.core.model.marshalling.response.urt.PinEntryTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.PinnableEntry

case class PinEntryInstructionBuilder()
    extends UrtInstructionBuilder[PipelineQuery, PinEntryTimelineInstruction] {

  override def build(
    query: PipelineQuery,
    entries: Seq[TimelineEntry]
  ): Seq[PinEntryTimelineInstruction] = {
    // Only one entry can be pinned and the desirable behavior is to pick the entry with the highest
    // sort index in the event that multiple pinned items exist. Since the entries are already
    // sorted we can accomplish this by picking the first one.
    entries.collectFirst {
      case entry: PinnableEntry if entry.isPinned.getOrElse(false) =>
        PinEntryTimelineInstruction(entry)
    }.toSeq
  }
}
