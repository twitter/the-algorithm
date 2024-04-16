package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.event

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata. mageVar antMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.event.EventSummary em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class EventSummary emMarshaller @ nject() (
  eventSummaryD splayTypeMarshaller: EventSummaryD splayTypeMarshaller,
   mageVar antMarshaller:  mageVar antMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(eventSummary: EventSummary em): urt.T  l ne emContent =
    urt.T  l ne emContent.EventSummary(
      urt.EventSummary(
         d = eventSummary. d,
        t le = eventSummary.t le,
        d splayType = eventSummaryD splayTypeMarshaller(eventSummary.d splayType),
        url = urlMarshaller(eventSummary.url),
         mage = eventSummary. mage.map( mageVar antMarshaller(_)),
        t  Str ng = eventSummary.t  Str ng
      )
    )
}
