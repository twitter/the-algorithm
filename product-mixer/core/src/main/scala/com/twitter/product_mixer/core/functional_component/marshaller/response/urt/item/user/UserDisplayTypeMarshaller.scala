package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.user

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.Pend ngFollowUser
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.User
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.UserDeta led
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.UserD splayType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class UserD splayTypeMarshaller @ nject() () {

  def apply(userD splayType: UserD splayType): urt.UserD splayType =
    userD splayType match {
      case User => urt.UserD splayType.User
      case UserDeta led => urt.UserD splayType.UserDeta led
      case Pend ngFollowUser => urt.UserD splayType.Pend ngFollowUser
    }
}
