package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.art cle

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle.Art cleD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle.Default
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Art cleD splayTypeMarshaller @ nject() () {
  def apply(art cleD splayType: Art cleD splayType): urt.Art cleD splayType =
    art cleD splayType match {
      case Default => urt.Art cleD splayType.Default
    }
}
