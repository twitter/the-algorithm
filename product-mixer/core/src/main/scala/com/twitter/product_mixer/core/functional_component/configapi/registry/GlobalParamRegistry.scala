package com.tw ter.product_m xer.core.funct onal_component.conf gap .reg stry

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class GlobalParamReg stry @ nject() (
  globalParamConf g: GlobalParamConf g,
  dec derGateBu lder: Dec derGateBu lder,
  statsRece ver: StatsRece ver) {

  def bu ld(): Conf g = {
    val globalConf gs = globalParamConf g.bu ld(dec derGateBu lder, statsRece ver)

    BaseConf gBu lder(globalConf gs).bu ld("GlobalParamReg stry")
  }
}
