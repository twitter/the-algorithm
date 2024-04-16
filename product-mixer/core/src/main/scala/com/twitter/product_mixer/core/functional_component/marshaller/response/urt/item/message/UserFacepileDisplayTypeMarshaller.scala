package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage.LargeUserFacep leD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage.CompactUserFacep leD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage.UserFacep leD splayType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class UserFacep leD splayTypeMarshaller @ nject() () {

  def apply(userFacep leD splayType: UserFacep leD splayType): urt.UserFacep leD splayType =
    userFacep leD splayType match {
      case LargeUserFacep leD splayType => urt.UserFacep leD splayType.Large
      case CompactUserFacep leD splayType => urt.UserFacep leD splayType.Compact
    }
}
