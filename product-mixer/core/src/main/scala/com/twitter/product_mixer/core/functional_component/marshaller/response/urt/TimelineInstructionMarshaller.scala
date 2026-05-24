package com.twitter.product_mixer.core.functional_component.marshaller.response.urt

import com.twitter.product_mixer.core.model.marshalling.response.urt.AddEntriesTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.AddToModuleTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.ClearCacheTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.MarkEntriesUnreadInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.PinEntryTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.ReplaceEntryTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.ShowAlertInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.ShowCoverInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.TerminateTimelineInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineInstruction
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelineInstructionMarshaller @Inject() (
  addEntriesInstructionMarshaller: AddEntriesInstructionMarshaller,
  addToModuleInstructionMarshaller: AddToModuleInstructionMarshaller,
  markEntriesUnreadInstructionMarshaller: MarkEntriesUnreadInstructionMarshaller,
  pinEntryInstructionMarshaller: PinEntryInstructionMarshaller,
  replaceEntryInstructionMarshaller: ReplaceEntryInstructionMarshaller,
  showAlertInstructionMarshaller: ShowAlertInstructionMarshaller,
  terminateTimelineInstructionMarshaller: TerminateTimelineInstructionMarshaller,
  coverMarshaller: CoverMarshaller) {

  def apply(instruction: TimelineInstruction): urt.TimelineInstruction = instruction match {
    case instruction: AddEntriesTimelineInstruction =>
      urt.TimelineInstruction.AddEntries(addEntriesInstructionMarshaller(instruction))
    case instruction: AddToModuleTimelineInstruction =>
      urt.TimelineInstruction.AddToModule(addToModuleInstructionMarshaller(instruction))
    case _: ClearCacheTimelineInstruction =>
      urt.TimelineInstruction.ClearCache(urt.ClearCache())
    case instruction: MarkEntriesUnreadInstruction =>
      urt.TimelineInstruction.MarkEntriesUnread(
        markEntriesUnreadInstructionMarshaller(instruction)
      )
    case instruction: PinEntryTimelineInstruction =>
      urt.TimelineInstruction.PinEntry(pinEntryInstructionMarshaller(instruction))
    case instruction: ReplaceEntryTimelineInstruction =>
      urt.TimelineInstruction.ReplaceEntry(replaceEntryInstructionMarshaller(instruction))
    case instruction: ShowCoverInstruction =>
      urt.TimelineInstruction.ShowCover(coverMarshaller(instruction.cover))
    case instruction: ShowAlertInstruction =>
      urt.TimelineInstruction.ShowAlert(showAlertInstructionMarshaller(instruction))
    case instruction: TerminateTimelineInstruction =>
      urt.TimelineInstruction.TerminateTimeline(terminateTimelineInstructionMarshaller(instruction))
  }
}
