package com.tw ter.v s b l y.conf gap .conf gs

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.t  l nes.conf gap ._
 mport com.tw ter.ut l.T  
 mport com.tw ter.v s b l y.conf gap .params.FSEnumRuleParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams._

pr vate[v s b l y] object V s b l yFeatureSw c s {

  val booleanFsOverr des: Seq[Opt onalOverr de[Boolean]] =
    FeatureSw chOverr deUt l.getBooleanFSOverr des(
      AgeGat ngAdultContentExper  ntRuleEnabledParam,
      Commun yT etCommun yUnava lableL m edAct onsRulesEnabledParam,
      Commun yT etDropProtectedRuleEnabledParam,
      Commun yT etDropRuleEnabledParam,
      Commun yT etL m edAct onsRulesEnabledParam,
      Commun yT et mberRemovedL m edAct onsRulesEnabledParam,
      Commun yT etNon mberL m edAct onsRuleEnabledParam,
      NsfwAgeBasedDropRulesHoldbackParam,
      Sk pT etDeta lL m edEngage ntRuleEnabledParam,
      StaleT etL m edAct onsRulesEnabledParam,
      TrustedFr endsT etL m edEngage ntsRuleEnabledParam,
      FosnrFallbackDropRulesEnabledParam,
      FosnrRulesEnabledParam
    )

  val doubleFsOverr des: Seq[Opt onalOverr de[Double]] =
    FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(
      H ghSpam T etContentScoreSearchTopProdT etLabelDropRuleThresholdParam,
      H ghSpam T etContentScoreSearchLatestProdT etLabelDropRuleThresholdParam,
      H ghSpam T etContentScoreTrendTopT etLabelDropRuleThresholdParam,
      H ghSpam T etContentScoreTrendLatestT etLabelDropRuleThresholdParam,
      H ghSpam T etContentScoreConvoDownrankAbus veQual yThresholdParam,
      H ghTox c yModelScoreSpaceThresholdParam,
      AdAvo danceH ghTox c yModelScoreThresholdParam,
      AdAvo danceReportedT etModelScoreThresholdParam,
    )

  val t  FsOverr des: Seq[Opt onalOverr de[T  ]] =
    FeatureSw chOverr deUt l.getT  FromStr ngFSOverr des()

  val str ngSeqFeatureSw chOverr des: Seq[Opt onalOverr de[Seq[Str ng]]] =
    FeatureSw chOverr deUt l.getStr ngSeqFSOverr des(
      CountrySpec f cNsfwContentGat ngCountr esParam,
      AgeGat ngAdultContentExper  ntCountr esParam,
      CardUr RootDoma nDenyL stParam
    )

  val enumFsParams: Seq[FSEnumRuleParam[_ <: Enu rat on]] = Seq()

  val mkOpt onalEnumFsOverr des: (StatsRece ver, Logger) => Seq[Opt onalOverr de[_]] = {
    (statsRece ver: StatsRece ver, logger: Logger) =>
      FeatureSw chOverr deUt l.getEnumFSOverr des(
        statsRece ver,
        logger,
        enumFsParams: _*
      )
  }

  def overr des(statsRece ver: StatsRece ver, logger: Logger): Seq[Opt onalOverr de[_]] = {
    val enumOverr des = mkOpt onalEnumFsOverr des(statsRece ver, logger)
    booleanFsOverr des ++
      doubleFsOverr des ++
      t  FsOverr des ++
      str ngSeqFeatureSw chOverr des ++
      enumOverr des
  }

  def conf g(statsRece ver: StatsRece ver, logger: Logger): BaseConf g =
    BaseConf gBu lder(overr des(statsRece ver.scope("features_sw c s"), logger))
      .bu ld("V s b l yFeatureSw c s")
}
