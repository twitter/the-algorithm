package com.tw ter.follow_recom ndat ons.assembler.models

sealed tra  Recom ndat onOpt ons

case class UserL stOpt ons(
  userB oEnabled: Boolean,
  userB oTruncated: Boolean,
  userB oMaxL nes: Opt on[Long],
) extends Recom ndat onOpt ons

case class CarouselOpt ons() extends Recom ndat onOpt ons
