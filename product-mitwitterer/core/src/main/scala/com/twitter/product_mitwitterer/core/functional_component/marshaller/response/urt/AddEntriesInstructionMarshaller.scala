package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.AddEntriesTimelineInstruction
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class AddEntriesInstructionMarshaller @Inject() (
  timelineEntryMarshaller: TimelineEntryMarshaller) {

  def apply(instruction: AddEntriesTimelineInstruction): urt.AddEntries = urt.AddEntries(
    entries = instruction.entries.map(timelineEntryMarshaller(_))
  )
}
