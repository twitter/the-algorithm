package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.cover

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.CenterCoverHalfCoverD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.CoverHalfCoverD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.HalfCoverD splayType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class HalfCoverD splayTypeMarshaller @ nject() () {

  def apply(halfCoverD splayType: HalfCoverD splayType): urt.HalfCoverD splayType =
    halfCoverD splayType match {
      case CenterCoverHalfCoverD splayType => urt.HalfCoverD splayType.CenterCover
      case CoverHalfCoverD splayType => urt.HalfCoverD splayType.Cover
    }
}
