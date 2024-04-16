package com.tw ter.follow_recom ndat ons.modules

 mport com.google. nject.Prov des
 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.featuresw c s.v2.Feature
 mport com.tw ter.featuresw c s.v2.FeatureF lter
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.featuresw c s.v2.bu lder.FeatureSw c sBu lder
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants.PRODUCER_S DE_FEATURE_SW TCHES
 mport com.tw ter. nject.Tw terModule
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object FeaturesSw c sModule extends Tw terModule {
  pr vate val DefaultConf gRepoPath = "/usr/local/conf g"
  pr vate val FeaturesPath = "/features/onboard ng/follow-recom ndat ons-serv ce/ma n"
  val  sLocal = flag("conf grepo.local", false, " s t  server runn ng locally or  n a DC")
  val localConf gRepoPath = flag(
    "local.conf grepo",
    System.getProperty("user.ho ") + "/workspace/conf g",
    "Path to y  local conf g repo"
  )

  @Prov des
  @S ngleton
  def prov desFeatureSw c s(
    abDec der: Logg ngABDec der,
    statsRece ver: StatsRece ver
  ): FeatureSw c s = {
    val conf gRepoPath =  f ( sLocal()) {
      localConf gRepoPath()
    } else {
      DefaultConf gRepoPath
    }

    FeatureSw c sBu lder
      .createDefault(FeaturesPath, abDec der, So (statsRece ver))
      .conf gRepoAbsPath(conf gRepoPath)
      .serv ceDeta lsFromAurora()
      .bu ld()
  }

  @Prov des
  @S ngleton
  @Na d(PRODUCER_S DE_FEATURE_SW TCHES)
  def prov desProducerFeatureSw c s(
    abDec der: Logg ngABDec der,
    statsRece ver: StatsRece ver
  ): FeatureSw c s = {
    val conf gRepoPath =  f ( sLocal()) {
      localConf gRepoPath()
    } else {
      DefaultConf gRepoPath
    }

    /**
     * Feature Sw c s evaluate all t ed FS Keys on Params construct on t  , wh ch  s very  neff c ent
     * for producer/cand date s de holdbacks because   have 100s of cand dates, and 100s of FS wh ch result
     *  n 10,000 FS evaluat ons w n   want 1 per cand date (100 total), so   create a new FS Cl ent
     * wh ch has a [[ProducerFeatureF lter]] set for feature f lter to reduce t  FS Keys   evaluate.
     */
    FeatureSw c sBu lder
      .createDefault(FeaturesPath, abDec der, So (statsRece ver.scope("producer_s de_fs")))
      .conf gRepoAbsPath(conf gRepoPath)
      .serv ceDeta lsFromAurora()
      .addFeatureF lter(ProducerFeatureF lter)
      .bu ld()
  }
}

case object ProducerFeatureF lter extends FeatureF lter {
  pr vate val Allo dKeys = Set(
    "post_nux_ml_flow_cand date_user_scorer_ d",
    "frs_rece ver_holdback_keep_soc al_user_cand date",
    "frs_rece ver_holdback_keep_user_cand date")

  overr de def f lter(feature: Feature): Opt on[Feature] = {
     f (Allo dKeys.ex sts(feature.para ters.conta ns)) {
      So (feature)
    } else {
      None
    }
  }
}
