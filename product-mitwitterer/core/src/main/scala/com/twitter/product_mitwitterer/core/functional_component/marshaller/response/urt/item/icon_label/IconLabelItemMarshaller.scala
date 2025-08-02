package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.icon_label

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.icon.HorizonIconMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.icon_label.IconLabelItem
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class IconLabelItemMarshaller @Inject() (
  richTetwittertMarshaller: RichTetwittertMarshaller,
  horizonIconMarshaller: HorizonIconMarshaller) {

  def apply(iconLabelItem: IconLabelItem): urt.TimelineItemContent =
    urt.TimelineItemContent.IconLabel(
      urt.IconLabel(
        tetwittert = richTetwittertMarshaller(iconLabelItem.tetwittert),
        icon = iconLabelItem.icon.map(horizonIconMarshaller(_))
      )
    )
}
