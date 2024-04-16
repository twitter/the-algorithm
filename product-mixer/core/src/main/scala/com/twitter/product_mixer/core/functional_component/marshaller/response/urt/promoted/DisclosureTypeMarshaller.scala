package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.D sclosureType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Earned
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted. ssue
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.NoD sclosure
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Pol  cal
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class D sclosureTypeMarshaller @ nject() () {

  def apply(d sclosureType: D sclosureType): urt.D sclosureType = d sclosureType match {
    case NoD sclosure => urt.D sclosureType.NoD sclosure
    case Pol  cal => urt.D sclosureType.Pol  cal
    case Earned => urt.D sclosureType.Earned
    case  ssue => urt.D sclosureType. ssue
  }
}
