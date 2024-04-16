package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em. ssage

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssageAct onType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssageTextAct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage.UserFacep le
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage.UserFacep leD splayType

case class UserFaceP leBu lder(
  user ds: Seq[Long],
  featuredUser ds: Seq[Long],
  act on: Opt on[ ssageTextAct on],
  act onType: Opt on[ ssageAct onType],
  d splaysFeatur ngText: Opt on[Boolean],
  d splayType: Opt on[UserFacep leD splayType]) {

  def apply(): UserFacep le = UserFacep le(
    user ds = user ds,
    featuredUser ds = featuredUser ds,
    act on = act on,
    act onType = act onType,
    d splaysFeatur ngText = d splaysFeatur ngText,
    d splayType = d splayType
  )
}
