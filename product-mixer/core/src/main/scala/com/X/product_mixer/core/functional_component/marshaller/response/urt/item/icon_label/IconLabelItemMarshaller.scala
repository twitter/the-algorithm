package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.icon_label

import com.X.product_mixer.core.functional_component.marshaller.response.urt.icon.HorizonIconMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.item.icon_label.IconLabelItem
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IconLabelItemMarshaller @Inject() (
  richTextMarshaller: RichTextMarshaller,
  horizonIconMarshaller: HorizonIconMarshaller) {

  def apply(iconLabelItem: IconLabelItem): urt.TimelineItemContent =
    urt.TimelineItemContent.IconLabel(
      urt.IconLabel(
        text = richTextMarshaller(iconLabelItem.text),
        icon = iconLabelItem.icon.map(horizonIconMarshaller(_))
      )
    )
}
