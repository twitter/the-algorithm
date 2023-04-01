package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.label

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.label.LabelItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LabelItemMarshaller @Inject() (
  displayTypeMarshaller: LabelDisplayTypeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(labelItem: LabelItem): urt.TimelineItemContent = {
    urt.TimelineItemContent.Label(
      urt.Label(
        text = labelItem.text,
        subtext = labelItem.subtext,
        disclosureIndicator = labelItem.disclosureIndicator,
        url = labelItem.url.map(urlMarshaller(_)),
        displayType = labelItem.displayType.map(displayTypeMarshaller(_))
      )
    )
  }
}
