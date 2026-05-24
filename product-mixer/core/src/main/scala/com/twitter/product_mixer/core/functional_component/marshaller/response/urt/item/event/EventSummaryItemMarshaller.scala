package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.event

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.ImageVariantMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.event.EventSummaryItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventSummaryItemMarshaller @Inject() (
  eventSummaryDisplayTypeMarshaller: EventSummaryDisplayTypeMarshaller,
  imageVariantMarshaller: ImageVariantMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(eventSummary: EventSummaryItem): urt.TimelineItemContent =
    urt.TimelineItemContent.EventSummary(
      urt.EventSummary(
        id = eventSummary.id,
        title = eventSummary.title,
        displayType = eventSummaryDisplayTypeMarshaller(eventSummary.displayType),
        url = urlMarshaller(eventSummary.url),
        image = eventSummary.image.map(imageVariantMarshaller(_)),
        timeString = eventSummary.timeString
      )
    )
}
