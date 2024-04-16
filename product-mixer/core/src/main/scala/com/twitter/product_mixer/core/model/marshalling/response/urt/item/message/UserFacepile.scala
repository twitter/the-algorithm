package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage

case class UserFacep le(
  user ds: Seq[Long],
  featuredUser ds: Seq[Long],
  act on: Opt on[ ssageTextAct on],
  act onType: Opt on[ ssageAct onType],
  d splaysFeatur ngText: Opt on[Boolean],
  d splayType: Opt on[UserFacep leD splayType])
