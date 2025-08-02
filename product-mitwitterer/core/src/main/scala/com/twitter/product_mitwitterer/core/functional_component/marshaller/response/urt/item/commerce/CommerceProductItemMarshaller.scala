package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.commerce

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.commerce.CommerceProductItem
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CommerceProductItemMarshaller @Inject() () {

  def apply(commerceProductItem: CommerceProductItem): urt.TimelineItemContent =
    urt.TimelineItemContent.CommerceProduct(urt.CommerceProduct(commerceProductItem.id))
}
