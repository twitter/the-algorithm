package com.twitter.product_mixer.core.functional_component.marshaller.response.urt

import com.twitter.product_mixer.core.model.marshalling.response.urt.ModuleItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleItemMarshaller @Inject() (
  timelineItemMarshaller: TimelineItemMarshaller,
  moduleItemTreeDisplayMarshaller: ModuleItemTreeDisplayMarshaller) {

  def apply(moduleItem: ModuleItem, moduleEntryId: String): urt.ModuleItem = urt.ModuleItem(
    /* Module items have an identifier comprising both the module entry id and the module item id.
    Some URT clients will deduplicate globally across different modules.
    Using the entry id as a prefix ensures that deduplication only happens within a single module,
    which we believe better aligns with engineers' intentions. */
    entryId = moduleEntryId + "-" + moduleItem.item.entryIdentifier,
    item = timelineItemMarshaller(moduleItem.item),
    dispensable = moduleItem.dispensable,
    treeDisplay = moduleItem.treeDisplay.map(moduleItemTreeDisplayMarshaller.apply)
  )
}
