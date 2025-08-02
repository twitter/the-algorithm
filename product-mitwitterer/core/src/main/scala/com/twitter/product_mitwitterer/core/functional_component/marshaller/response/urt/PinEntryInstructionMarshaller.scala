package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.PinEntryTimelineInstruction
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class PinEntryInstructionMarshaller @Inject() (
  timelineEntryMarshaller: TimelineEntryMarshaller) {

  def apply(instruction: PinEntryTimelineInstruction): urt.PinEntry = {
    urt.PinEntry(entry = timelineEntryMarshaller(instruction.entry))
  }
}
