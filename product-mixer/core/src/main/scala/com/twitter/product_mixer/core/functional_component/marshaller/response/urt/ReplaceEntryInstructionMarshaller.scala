package com.twitter.product_mixer.core.functional_component.marshaller.response.urt

import com.twitter.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.ReplaceEntryTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReplaceEntryInstructionMarshaller @Inject() (
  timelineEntryMarshaller: TimelineEntryMarshaller) {

  def apply(instruction: ReplaceEntryTimelineInstruction): urt.ReplaceEntry = {
    val instructionEntry = instruction.entry
    urt.ReplaceEntry(
      entryIdToReplace = instructionEntry.entryIdToReplace
        .getOrElse(throw new MissingEntryToReplaceException(instructionEntry)),
      entry = timelineEntryMarshaller(instructionEntry)
    )
  }
}

class MissingEntryToReplaceException(entry: TimelineEntry)
    extends IllegalArgumentException(
      s"Missing entry ID to replace ${TransportMarshaller.getSimpleName(entry.getClass)}")
