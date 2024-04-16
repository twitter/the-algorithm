package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.top c

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Bas cTop cD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.No conTop cD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.P llTop cD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.P llW houtAct on conD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Top cD splayType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Top cD splayTypeMarshaller @ nject() () {

  def apply(top cD splayType: Top cD splayType): urt.Top cD splayType = top cD splayType match {
    case Bas cTop cD splayType => urt.Top cD splayType.Bas c
    case P llTop cD splayType => urt.Top cD splayType.P ll
    case No conTop cD splayType => urt.Top cD splayType.No con
    case P llW houtAct on conD splayType => urt.Top cD splayType.P llW houtAct on con
  }
}
