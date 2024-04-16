package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.gener c_summary_ em

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.gener c_summary.Gener cSummary emD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.gener c_summary. roD splayType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Gener cSummaryD splayTypeMarshaller @ nject() () {

  def apply(
    gener cSummary emD splayType: Gener cSummary emD splayType
  ): urt.Gener cSummaryD splayType =
    gener cSummary emD splayType match {
      case  roD splayType => urt.Gener cSummaryD splayType. ro
    }
}
