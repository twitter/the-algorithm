package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.event

import com.X.product_mixer.core.model.marshalling.response.urt.item.event.EventSummaryDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.item.event.CellEventSummaryDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.item.event.HeroEventSummaryDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.item.event.CellWithProminentSocialContextEventSummaryDisplayType
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventSummaryDisplayTypeMarshaller @Inject() () {

  def apply(
    eventSummaryDisplayType: EventSummaryDisplayType
  ): urt.EventSummaryDisplayType = eventSummaryDisplayType match {
    case CellEventSummaryDisplayType =>
      urt.EventSummaryDisplayType.Cell
    case HeroEventSummaryDisplayType =>
      urt.EventSummaryDisplayType.Hero
    case CellWithProminentSocialContextEventSummaryDisplayType =>
      urt.EventSummaryDisplayType.CellWithProminentSocialContext
  }
}
