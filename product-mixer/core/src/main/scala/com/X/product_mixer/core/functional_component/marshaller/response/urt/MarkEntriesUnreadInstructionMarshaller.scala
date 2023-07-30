package com.X.product_mixer.core.functional_component.marshaller.response.urt

import com.X.product_mixer.core.model.marshalling.response.urt.MarkEntriesUnreadInstruction
import javax.inject.Inject
import javax.inject.Singleton
import com.X.timelines.render.{thriftscala => urt}

@Singleton
class MarkEntriesUnreadInstructionMarshaller @Inject() () {

  def apply(instruction: MarkEntriesUnreadInstruction): urt.MarkEntriesUnread =
    urt.MarkEntriesUnread(entryIds = instruction.entryIds)
}
