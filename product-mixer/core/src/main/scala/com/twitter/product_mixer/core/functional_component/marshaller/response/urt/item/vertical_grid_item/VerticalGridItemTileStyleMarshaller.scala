package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.vert cal_gr d_ em

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.S ngleStateDefaultVert calGr d emT leStyle
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.DoubleStateDefaultVert calGr d emT leStyle
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.Vert calGr d emT leStyle
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Vert calGr d emT leStyleMarshaller @ nject() () {

  def apply(vert calGr d emT leStyle: Vert calGr d emT leStyle): urt.Vert calGr d emT leStyle =
    vert calGr d emT leStyle match {
      case S ngleStateDefaultVert calGr d emT leStyle =>
        urt.Vert calGr d emT leStyle.S ngleStateDefault
      case DoubleStateDefaultVert calGr d emT leStyle =>
        urt.Vert calGr d emT leStyle.DoubleStateDefault
    }
}
