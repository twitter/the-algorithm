package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.vert cal_gr d_ em

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.P votVert calGr d emTop cFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.Recom ndat onVert calGr d emTop cFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.Vert calGr d emTop cFunct onal yType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Vert calGr d emTop cFunct onal yTypeMarshaller @ nject() () {

  def apply(
    vert calGr d emTop cFunct onal yType: Vert calGr d emTop cFunct onal yType
  ): urt.Vert calGr d emTop cFunct onal yType = vert calGr d emTop cFunct onal yType match {
    case P votVert calGr d emTop cFunct onal yType =>
      urt.Vert calGr d emTop cFunct onal yType.P vot
    case Recom ndat onVert calGr d emTop cFunct onal yType =>
      urt.Vert calGr d emTop cFunct onal yType.Recom ndat on
  }
}
