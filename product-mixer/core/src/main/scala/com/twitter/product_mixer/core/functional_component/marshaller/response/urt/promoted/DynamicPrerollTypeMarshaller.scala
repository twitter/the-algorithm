package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Ampl fy
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Dynam cPrerollType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.L veTvEvent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Marketplace
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Dynam cPrerollTypeMarshaller @ nject() () {

  def apply(dynam cPrerollType: Dynam cPrerollType): urt.Dynam cPrerollType =
    dynam cPrerollType match {
      case Ampl fy => urt.Dynam cPrerollType.Ampl fy
      case Marketplace => urt.Dynam cPrerollType.Marketplace
      case L veTvEvent => urt.Dynam cPrerollType.L veTvEvent
    }
}
