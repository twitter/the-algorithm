package com.tw ter.follow_recom ndat ons.conf gap 

 mport com.tw ter.t  l nes.conf gap .Compos eConf g
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Conf gBu lder @ nject() (
  dec derConf gs: Dec derConf gs,
  featureSw chConf gs: FeatureSw chConf gs) {
  // T  order of conf gs added to `Compos eConf g`  s  mportant. T  conf g w ll be matc d w h
  // t  f rst poss ble rule. So, current setup w ll g ve pr or y to Dec ders  nstead of FS
  def bu ld(): Conf g =
    new Compos eConf g(Seq(dec derConf gs.conf g, featureSw chConf gs.conf g))
}
