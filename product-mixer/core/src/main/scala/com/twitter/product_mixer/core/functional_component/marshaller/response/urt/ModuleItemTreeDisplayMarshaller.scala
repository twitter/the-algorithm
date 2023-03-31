package com.twitter.product_mixer.core.functional_component.marshaller.response.urt

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.timeline_module.ModuleDisplayTypeMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.ModuleItemTreeDisplay
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleItemTreeDisplayMarshaller @Inject() (
  moduleDisplayTypeMarshaller: ModuleDisplayTypeMarshaller) {

  def apply(moduleItemTreeDisplay: ModuleItemTreeDisplay): urt.ModuleItemTreeDisplay =
    urt.ModuleItemTreeDisplay(
      parentModuleItemEntryId = moduleItemTreeDisplay.parentModuleEntryItemId,
      indentFromParent = moduleItemTreeDisplay.indentFromParent,
      displayType = moduleItemTreeDisplay.displayType.map(moduleDisplayTypeMarshaller(_)),
      isAnchorChild = moduleItemTreeDisplay.isAnchorChild
    )
}
