package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.event

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.event.EventSummaryD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.event.CellEventSummaryD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.event. roEventSummaryD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.event.CellW hProm nentSoc alContextEventSummaryD splayType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class EventSummaryD splayTypeMarshaller @ nject() () {

  def apply(
    eventSummaryD splayType: EventSummaryD splayType
  ): urt.EventSummaryD splayType = eventSummaryD splayType match {
    case CellEventSummaryD splayType =>
      urt.EventSummaryD splayType.Cell
    case  roEventSummaryD splayType =>
      urt.EventSummaryD splayType. ro
    case CellW hProm nentSoc alContextEventSummaryD splayType =>
      urt.EventSummaryD splayType.CellW hProm nentSoc alContext
  }
}
