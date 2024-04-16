package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.vert cal_gr d_ em

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em.Vert calGr d em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Vert calGr d emMarshaller @ nject() (
  vert calGr d emContentMarshaller: Vert calGr d emContentMarshaller) {

  def apply(vert calGr d em: Vert calGr d em): urt.T  l ne emContent =
    urt.T  l ne emContent.Vert calGr d em(
      urt.Vert calGr d em(
        content = vert calGr d emContentMarshaller(vert calGr d em)
      )
    )
}
