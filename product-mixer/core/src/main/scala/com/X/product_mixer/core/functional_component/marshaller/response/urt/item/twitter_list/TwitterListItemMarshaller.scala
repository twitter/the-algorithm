package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.X_list

import com.X.product_mixer.core.model.marshalling.response.urt.item.X_list.XListItem
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XListItemMarshaller @Inject() (
  XListDisplayTypeMarshaller: XListDisplayTypeMarshaller) {

  def apply(XListItem: XListItem): urt.TimelineItemContent =
    urt.TimelineItemContent.XList(
      urt.XList(
        id = XListItem.id,
        displayType = XListItem.displayType.map(XListDisplayTypeMarshaller(_))
      )
    )
}
