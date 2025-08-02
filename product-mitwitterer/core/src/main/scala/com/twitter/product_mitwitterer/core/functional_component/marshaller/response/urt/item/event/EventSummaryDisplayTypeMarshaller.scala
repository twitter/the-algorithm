package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.event

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.event.EventSummaryDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.event.CellEventSummaryDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.event.HeroEventSummaryDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.event.CellWithProminentSocialContetwittertEventSummaryDisplayType
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class EventSummaryDisplayTypeMarshaller @Inject() () {

  def apply(
    eventSummaryDisplayType: EventSummaryDisplayType
  ): urt.EventSummaryDisplayType = eventSummaryDisplayType match {
    case CellEventSummaryDisplayType =>
      urt.EventSummaryDisplayType.Cell
    case HeroEventSummaryDisplayType =>
      urt.EventSummaryDisplayType.Hero
    case CellWithProminentSocialContetwittertEventSummaryDisplayType =>
      urt.EventSummaryDisplayType.CellWithProminentSocialContetwittert
  }
}
