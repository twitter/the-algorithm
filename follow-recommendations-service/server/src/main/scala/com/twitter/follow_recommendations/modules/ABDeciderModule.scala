package com.tw ter.follow_recom ndat ons.modules

 mport com.google. nject.Prov des
 mport com.google. nject.na .Na d
 mport com.tw ter.abdec der.ABDec derFactory
 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.logg ng.LoggerFactory
 mport javax. nject.S ngleton

object ABDec derModule extends Tw terModule {
  @Prov des
  @S ngleton
  def prov deABDec der(
    stats: StatsRece ver,
    @Na d(Gu ceNa dConstants.CL ENT_EVENT_LOGGER) factory: LoggerFactory
  ): Logg ngABDec der = {

    val ymlPath = "/usr/local/conf g/abdec der/abdec der.yml"

    val abDec derFactory = ABDec derFactory(
      abDec derYmlPath = ymlPath,
      scr beLogger = So (factory()),
      env ron nt = So ("product on")
    )

    abDec derFactory.bu ldW hLogg ng()
  }
}
