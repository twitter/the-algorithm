package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt

import com.twitter.product_mitwitterer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.ReplaceEntryTimelineInstruction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ReplaceEntryInstructionMarshaller @Inject() (
  timelineEntryMarshaller: TimelineEntryMarshaller) {

  def apply(instruction: ReplaceEntryTimelineInstruction): urt.ReplaceEntry = {
    val instructionEntry = instruction.entry
    urt.ReplaceEntry(
      entryIdToReplace = instructionEntry.entryIdToReplace
        .getOrElse(throw new MissingEntryToReplaceEtwitterception(instructionEntry)),
      entry = timelineEntryMarshaller(instructionEntry)
    )
  }
}

class MissingEntryToReplaceEtwitterception(entry: TimelineEntry)
    etwittertends IllegalArgumentEtwitterception(
      s"Missing entry ID to replace ${TransportMarshaller.getSimpleName(entry.getClass)}")
