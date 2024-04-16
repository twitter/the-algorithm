package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.D scla  r ssue
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.D scla  rPol  cal
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.D scla  rType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class D scla  rTypeMarshaller @ nject() () {

  def apply(d scla  rType: D scla  rType): urt.D scla  rType = d scla  rType match {
    case D scla  rPol  cal => urt.D scla  rType.Pol  cal
    case D scla  r ssue => urt.D scla  rType. ssue
  }
}
