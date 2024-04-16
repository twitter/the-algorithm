package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.cover

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.CoverFullCoverD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.FullCoverD splayType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class FullCoverD splayTypeMarshaller @ nject() () {

  def apply(halfCoverD splayType: FullCoverD splayType): urt.FullCoverD splayType =
    halfCoverD splayType match {
      case CoverFullCoverD splayType => urt.FullCoverD splayType.Cover
    }
}
