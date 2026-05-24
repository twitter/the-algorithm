package com.twitter.product_mixer.component_library.premarshaller.urt.builder

import com.twitter.product_mixer.core.model.marshalling.response.urt.AddEntriesTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineModule
import com.twitter.product_mixer.core.pipeline.PipelineQuery

/**
 * Build the AddEntries instruction with special handling for AddToModule entries.
 *
 * Entries which are going to be added to a module are going to be added via
 * AddToModuleInstructionBuilder, for other entries in the same response (like cursor entries) we
 * still need an AddEntriesTimelineInstruction which is going to be created by this builder.
 */
case class AddEntriesWithAddToModuleInstructionBuilder[Query <: PipelineQuery](
  override val includeInstruction: IncludeInstruction[Query] = AlwaysInclude)
    extends UrtInstructionBuilder[Query, AddEntriesTimelineInstruction] {

  override def build(
    query: Query,
    entries: Seq[TimelineEntry]
  ): Seq[AddEntriesTimelineInstruction] = {
    if (includeInstruction(query, entries)) {
      val entriesToAdd = entries.filter {
        case _: TimelineModule => false
        case _ => true
      }
      if (entriesToAdd.nonEmpty) Seq(AddEntriesTimelineInstruction(entriesToAdd))
      else Seq.empty
    } else
      Seq.empty
  }
}
