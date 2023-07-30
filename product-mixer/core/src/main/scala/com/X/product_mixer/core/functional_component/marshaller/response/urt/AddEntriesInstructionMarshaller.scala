package com.X.product_mixer.core.functional_component.marshaller.response.urt

import com.X.product_mixer.core.model.marshalling.response.urt.AddEntriesTimelineInstruction
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddEntriesInstructionMarshaller @Inject() (
  timelineEntryMarshaller: TimelineEntryMarshaller) {

  def apply(instruction: AddEntriesTimelineInstruction): urt.AddEntries = urt.AddEntries(
    entries = instruction.entries.map(timelineEntryMarshaller(_))
  )
}
