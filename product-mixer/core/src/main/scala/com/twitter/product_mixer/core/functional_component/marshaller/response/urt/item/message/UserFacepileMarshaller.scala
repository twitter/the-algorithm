package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage.UserFacep le
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class UserFacep leMarshaller @ nject() (
   ssageAct onTypeMarshaller:  ssageAct onTypeMarshaller,
   ssageTextAct onMarshaller:  ssageTextAct onMarshaller,
  userFacep leD splayTypeMarshaller: UserFacep leD splayTypeMarshaller) {

  def apply(userFacep le: UserFacep le): urt.UserFacep le =
    urt.UserFacep le(
      user ds = userFacep le.user ds,
      featuredUser ds = userFacep le.featuredUser ds,
      act on = userFacep le.act on.map( ssageTextAct onMarshaller(_)),
      act onType = userFacep le.act onType.map( ssageAct onTypeMarshaller(_)),
      d splaysFeatur ngText = userFacep le.d splaysFeatur ngText,
      d splayType = userFacep le.d splayType.map(userFacep leD splayTypeMarshaller(_))
    )
}
