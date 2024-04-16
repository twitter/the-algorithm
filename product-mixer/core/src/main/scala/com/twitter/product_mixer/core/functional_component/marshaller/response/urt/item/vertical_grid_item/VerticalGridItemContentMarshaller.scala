package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.vert cal_gr d_ em

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.Vert calGr d em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.Vert calGr d emTop cT le
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Vert calGr d emContentMarshaller @ nject() (
  vert calGr d emTop cT leMarshaller: Vert calGr d emTop cT leMarshaller) {

  def apply( em: Vert calGr d em): urt.Vert calGr d emContent =  em match {
    case vert calGr d emTop cT le: Vert calGr d emTop cT le =>
      vert calGr d emTop cT leMarshaller(vert calGr d emTop cT le)
  }
}
