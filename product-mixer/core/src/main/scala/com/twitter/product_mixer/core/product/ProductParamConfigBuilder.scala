package com.tw ter.product_m xer.core.product

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .reg stry.ParamConf gBu lder
 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Opt onalOverr de
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derUt ls

tra  ProductParamConf gBu lder extends ParamConf gBu lder {
  productParamConf g: ProductParamConf g =>

  overr de def bu ld(
    dec derGateBu lder: Dec derGateBu lder,
    statsRece ver: StatsRece ver
  ): Seq[Opt onalOverr de[_]] = {
    Dec derUt ls.getBooleanDec derOverr des(dec derGateBu lder, EnabledDec derParam) ++
      FeatureSw chOverr deUt l.getBooleanFSOverr des(SupportedCl entParam) ++
      super.bu ld(dec derGateBu lder, statsRece ver)
  }
}
