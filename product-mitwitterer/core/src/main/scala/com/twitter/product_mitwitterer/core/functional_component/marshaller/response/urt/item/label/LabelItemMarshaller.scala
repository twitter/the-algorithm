package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.label

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.label.LabelItem
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class LabelItemMarshaller @Inject() (
  displayTypeMarshaller: LabelDisplayTypeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(labelItem: LabelItem): urt.TimelineItemContent = {
    urt.TimelineItemContent.Label(
      urt.Label(
        tetwittert = labelItem.tetwittert,
        subtetwittert = labelItem.subtetwittert,
        disclosureIndicator = labelItem.disclosureIndicator,
        url = labelItem.url.map(urlMarshaller(_)),
        displayType = labelItem.displayType.map(displayTypeMarshaller(_))
      )
    )
  }
}
