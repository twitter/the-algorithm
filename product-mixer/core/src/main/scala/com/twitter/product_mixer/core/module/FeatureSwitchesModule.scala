package com.tw ter.product_m xer.core.module

 mport com.google. nject.Prov des
 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.featuresw c s.v2.bu lder.FeatureSw c sBu lder
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.Conf gRepoLocalPath
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.FeatureSw c sPath
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.Serv ceLocal
 mport com.tw ter.t  l nes.features.app.Forc bleFeatureValuesModule
 mport javax. nject.S ngleton

object FeatureSw c sModule extends Tw terModule w h Forc bleFeatureValuesModule {
  pr vate val DefaultConf gRepoPath = "/usr/local/conf g"

  @Prov des
  @S ngleton
  def prov desFeatureSw c s(
    abDec der: Logg ngABDec der,
    statsRece ver: StatsRece ver,
    @Flag(Serv ceLocal)  sServ ceLocal: Boolean,
    @Flag(Conf gRepoLocalPath) localConf gRepoPath: Str ng,
    @Flag(FeatureSw c sPath) featuresPath: Str ng
  ): FeatureSw c s = {
    val conf gRepoPath =  f ( sServ ceLocal) {
      localConf gRepoPath
    } else {
      DefaultConf gRepoPath
    }

    val baseBu lder = FeatureSw c sBu lder
      .createDefault(featuresPath, abDec der, So (statsRece ver))
      .conf gRepoAbsPath(conf gRepoPath)
      .forcedValues(getFeatureSw chOverr des)
      // Track stats w n an exper  nt  mpress on  s made. For example:
      // "exper  nt_ mpress ons/test_exper  nt_1234/"
      // "exper  nt_ mpress ons/test_exper  nt_1234/control"
      // "exper  nt_ mpress ons/test_exper  nt_1234/treat nt"
      .exper  nt mpress onStatsEnabled(true)
      .un sOfD vers onEnable(true)

    val f nalBu lder =  f ( sServ ceLocal) {
      baseBu lder
    } else {
      baseBu lder.serv ceDeta lsFromAurora()
    }

    f nalBu lder.bu ld()
  }
}
