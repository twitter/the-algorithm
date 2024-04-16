package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.BottomS et
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Conf rmat onD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. nl ne
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Conf rmat onD splayTypeMarshaller @ nject() () {

  def apply(conf rmat onD splayType: Conf rmat onD splayType): urt.Conf rmat onD splayType =
    conf rmat onD splayType match {
      case  nl ne => urt.Conf rmat onD splayType. nl ne
      case BottomS et => urt.Conf rmat onD splayType.BottomS et
    }
}
