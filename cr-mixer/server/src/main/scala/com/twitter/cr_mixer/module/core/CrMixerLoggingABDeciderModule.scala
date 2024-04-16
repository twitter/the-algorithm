package com.tw ter.cr_m xer.module.core

 mport com.google. nject.Prov des
 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.cr_m xer.featuresw ch.CrM xerLogg ngABDec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport javax. nject.S ngleton

object CrM xerLogg ngABDec derModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov deABDec der(
    logg ngABDec der: Logg ngABDec der,
    statsRece ver: StatsRece ver
  ): CrM xerLogg ngABDec der = {
    CrM xerLogg ngABDec der(logg ngABDec der, statsRece ver)
  }
}
