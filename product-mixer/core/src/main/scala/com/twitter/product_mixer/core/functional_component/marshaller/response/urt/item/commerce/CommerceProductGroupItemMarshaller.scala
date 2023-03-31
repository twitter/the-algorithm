package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.commerce

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.commerce.CommerceProductGroupItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommerceProductGroupItemMarshaller @Inject() () {

  def apply(commerceProductGroupItem: CommerceProductGroupItem): urt.TimelineItemContent =
    urt.TimelineItemContent.CommerceProductGroup(
      urt.CommerceProductGroup(commerceProductGroupItem.id))
}
