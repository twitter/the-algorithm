package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.MarkEntriesUnreadInstruction
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import com.twitter.timelines.render.{thriftscala => urt}

@Singleton
class MarkEntriesUnreadInstructionMarshaller @Inject() () {

  def apply(instruction: MarkEntriesUnreadInstruction): urt.MarkEntriesUnread =
    urt.MarkEntriesUnread(entryIds = instruction.entryIds)
}
