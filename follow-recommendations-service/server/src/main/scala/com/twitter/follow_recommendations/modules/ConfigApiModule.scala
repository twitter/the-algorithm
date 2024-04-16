package com.tw ter.follow_recom ndat ons.modules

 mport com.google. nject.Prov des
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.follow_recom ndat ons.conf gap .Conf gBu lder
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport javax. nject.S ngleton

object Conf gAp Module extends Tw terModule {
  @Prov des
  @S ngleton
  def prov desDec derGateBu lder(dec der: Dec der): Dec derGateBu lder =
    new Dec derGateBu lder(dec der)

  @Prov des
  @S ngleton
  def prov desConf g(conf gBu lder: Conf gBu lder): Conf g = conf gBu lder.bu ld()
}
