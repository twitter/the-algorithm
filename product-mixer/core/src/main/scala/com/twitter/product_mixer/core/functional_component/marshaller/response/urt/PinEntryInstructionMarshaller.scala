package com.twitter.product_mixer.core.functional_component.marshaller.response.urt

import com.twitter.product_mixer.core.model.marshalling.response.urt.PinEntryTimelineInstruction
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PinEntryInstructionMarshaller @Inject() (
  timelineEntryMarshaller: TimelineEntryMarshaller) {

  def apply(instruction: PinEntryTimelineInstruction): urt.PinEntry = {
    urt.PinEntry(entry = timelineEntryMarshaller(instruction.entry))
  }
}
