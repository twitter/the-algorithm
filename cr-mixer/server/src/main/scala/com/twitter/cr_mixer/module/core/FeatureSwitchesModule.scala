package com.tw ter.cr_m xer.module.core

 mport com.google. nject.Prov des
 mport com.tw ter.cr_m xer.featuresw ch.CrM xerLogg ngABDec der
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.featuresw c s.v2.bu lder.FeatureSw c sBu lder
 mport com.tw ter.featuresw c s.v2.exper  ntat on.NullBucket mpressor
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.ut l.Durat on
 mport javax. nject.S ngleton

object FeatureSw c sModule extends Tw terModule {

  flag(
    na  = "featuresw c s.path",
    default = "/features/cr-m xer/ma n",
     lp = "path to t  featuresw ch conf gurat on d rectory"
  )
  flag(
    "use_conf g_repo_m rror.bool",
    false,
    " f true, read conf g from a d fferent d rectory, to fac l ate test ng.")

  val DefaultFastRefresh: Boolean = false
  val AddServ ceDeta lsFromAurora: Boolean = true
  val  mpressExper  nts: Boolean = true

  @Prov des
  @S ngleton
  def prov desFeatureSw c s(
    @Flag("featuresw c s.path") featureSw chD rectory: Str ng,
    @Flag("use_conf g_repo_m rror.bool") useConf gRepoM rrorFlag: Boolean,
    abDec der: CrM xerLogg ngABDec der,
    statsRece ver: StatsRece ver
  ): FeatureSw c s = {
    val conf gRepoAbsPath =
      getConf gRepoAbsPath(useConf gRepoM rrorFlag)
    val fastRefresh =
      shouldFastRefresh(useConf gRepoM rrorFlag)

    val featureSw c s = FeatureSw c sBu lder()
      .abDec der(abDec der)
      .statsRece ver(statsRece ver.scope("featuresw c s-v2"))
      .conf gRepoAbsPath(conf gRepoAbsPath)
      .featuresD rectory(featureSw chD rectory)
      .l m ToReferencedExper  nts(shouldL m  = true)
      .exper  nt mpress onStatsEnabled(true)

     f (! mpressExper  nts) featureSw c s.exper  ntBucket mpressor(NullBucket mpressor)
     f (AddServ ceDeta lsFromAurora) featureSw c s.serv ceDeta lsFromAurora()
     f (fastRefresh) featureSw c s.refreshPer od(Durat on.fromSeconds(10))

    featureSw c s.bu ld()
  }

  pr vate def getConf gRepoAbsPath(
    useConf gRepoM rrorFlag: Boolean
  ): Str ng = {
     f (useConf gRepoM rrorFlag)
      "conf g_repo_m rror/"
    else "/usr/local/conf g"
  }

  pr vate def shouldFastRefresh(
    useConf gRepoM rrorFlag: Boolean
  ): Boolean = {
     f (useConf gRepoM rrorFlag)
      true
    else DefaultFastRefresh
  }

}
