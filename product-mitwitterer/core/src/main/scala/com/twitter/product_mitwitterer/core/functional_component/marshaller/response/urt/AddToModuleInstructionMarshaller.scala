package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.AddToModuleTimelineInstruction
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class AddToModuleInstructionMarshaller @Inject() (moduleItemMarshaller: ModuleItemMarshaller) {

  def apply(instruction: AddToModuleTimelineInstruction): urt.AddToModule = urt.AddToModule(
    moduleItems = instruction.moduleItems.map(moduleItemMarshaller(_, instruction.moduleEntryId)),
    moduleEntryId = instruction.moduleEntryId,
    moduleItemEntryId = instruction.moduleItemEntryId,
    prepend = instruction.prepend
  )
}
