package com.X.product_mixer.core.functional_component.marshaller.response.urt.commerce

import com.X.product_mixer.core.model.marshalling.response.urt.item.commerce.CommerceProductItem
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommerceProductItemMarshaller @Inject() () {

  def apply(commerceProductItem: CommerceProductItem): urt.TimelineItemContent =
    urt.TimelineItemContent.CommerceProduct(urt.CommerceProduct(commerceProductItem.id))
}
