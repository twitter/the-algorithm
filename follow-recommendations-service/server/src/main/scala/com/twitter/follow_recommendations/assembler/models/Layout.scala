package com.tw ter.follow_recom ndat ons.assembler.models

sealed tra  La t

case class UserL stLa t(
   ader: Opt on[ aderConf g],
  userL stOpt ons: UserL stOpt ons,
  soc alProofs: Opt on[Seq[Soc alProof]],
  footer: Opt on[FooterConf g])
    extends La t

case class CarouselLa t(
   ader: Opt on[ aderConf g],
  carouselOpt ons: CarouselOpt ons,
  soc alProofs: Opt on[Seq[Soc alProof]])
    extends La t
