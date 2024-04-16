package com.tw ter.fr gate.pushserv ce.params

 mport com.tw ter.rux.common.context.thr ftscala.Exper  ntKey
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.t  l nes.conf gap .dec der.BooleanDec derParam

object PushParams {

  /**
   * D sable ML models  n f lter ng
   */
  object D sableMl nF lter ngParam extends BooleanDec derParam(Dec derKey.d sableML nF lter ng)

  /**
   * D sable ML models  n rank ng, use random rank ng  nstead
   * T  param  s used for ML holdback and tra n ng data collect on
   */
  object UseRandomRank ngParam extends Param(false)

  /**
   * D sable feature hydrat on, ML rank ng, and ML f lter ng
   * Use default order from cand date s ce
   * T  param  s for serv ce cont nu y
   */
  object D sableAllRelevanceParam extends BooleanDec derParam(Dec derKey.d sableAllRelevance)

  /**
   * D sable ML  avy rank ng
   * Use default order from cand date s ce
   * T  param  s for serv ce cont nu y
   */
  object D sable avyRank ngParam extends BooleanDec derParam(Dec derKey.d sable avyRank ng)

  /**
   * Restr ct ML l ght rank ng by select ng top3 cand dates
   * Use default order from cand date s ce
   * T  param  s for serv ce cont nu y
   */
  object Restr ctL ghtRank ngParam extends BooleanDec derParam(Dec derKey.restr ctL ghtRank ng)

  /**
   * Downsample ML l ght rank ng scr bed cand dates
   */
  object DownSampleL ghtRank ngScr beCand datesParam
      extends BooleanDec derParam(Dec derKey.downSampleL ghtRank ngScr beCand dates)

  /**
   * Set   to true only for Andro d only rank ng exper  nts
   */
  object Andro dOnlyRank ngExper  ntParam extends Param(false)

  /**
   * Enable t  user_t et_ent y_graph t et cand date s ce.
   */
  object UTEGT etCand dateS ceParam
      extends BooleanDec derParam(Dec derKey.ent yGraphT etRecsDec derKey)

  /**
   * Enable wr es to Not f cat on Serv ce
   */
  object EnableWr esToNot f cat onServ ceParam
      extends BooleanDec derParam(Dec derKey.enablePushserv ceWr esToNot f cat onServ ceDec derKey)

  /**
   * Enable wr es to Not f cat on Serv ce for all employees
   */
  object EnableWr esToNot f cat onServ ceForAllEmployeesParam
      extends BooleanDec derParam(
        Dec derKey.enablePushserv ceWr esToNot f cat onServ ceForAllEmployeesDec derKey)

  /**
   * Enable wr es to Not f cat on Serv ce for everyone
   */
  object EnableWr esToNot f cat onServ ceForEveryoneParam
      extends BooleanDec derParam(
        Dec derKey.enablePushserv ceWr esToNot f cat onServ ceForEveryoneDec derKey)

  /**
   * Enable fat gu ng MR for Ntab caret cl ck
   */
  object EnableFat gueNtabCaretCl ck ngParam extends Param(true)

  /**
   * Param for d sabl ng  n-network T et cand dates
   */
  object D sable nNetworkT etCand datesParam extends Param(false)

  /**
   * Dec der controlled param to enable prompt feedback response NO pred cate
   */
  object EnablePromptFeedbackFat gueResponseNoPred cate
      extends BooleanDec derParam(
        Dec derKey.enablePromptFeedbackFat gueResponseNoPred cateDec derKey)

  /**
   * Enable hydrat on and generat on of Soc al context (TF, TR) based cand dates for Earlyb rd T ets
   */
  object EarlyB rdSCBasedCand datesParam
      extends BooleanDec derParam(Dec derKey.enableUTEGSCForEarlyb rdT etsDec der)

  /**
   * Param to allow reduce to one soc al proof for t et param  n UTEG
   */
  object AllowOneSoc alProofForT et nUTEGParam extends Param(true)

  /**
   * Param to query UTEG for out network t ets only
   */
  object OutNetworkT etsOnlyForUTEGParam extends Param(false)

  object EnablePushSendEventBus extends BooleanDec derParam(Dec derKey.enablePushSendEventBus)

  /**
   * Enable RUX T et land ng page for push open on  OS
   */
  object EnableRuxLand ngPage OSParam extends Param[Boolean](true)

  /**
   * Enable RUX T et land ng page for push open on Andro d
   */
  object EnableRuxLand ngPageAndro dParam extends Param[Boolean](true)

  /**
   * Param to dec de wh ch Exper  ntKey to be encoded  nto Rux land ng page context object.
   * T  context object  s sent to rux-ap  and rux-ap  appl es log c (e.g. show reply module on
   * rux land ng page or not) accord ngly based on t  exper  nt key.
   */
  object RuxLand ngPageExper  ntKey OSParam extends Param[Opt on[Exper  ntKey]](None)
  object RuxLand ngPageExper  ntKeyAndro dParam extends Param[Opt on[Exper  ntKey]](None)

  /**
   * Param to enable MR T et Fav Recs
   */
  object MRT etFavRecsParam extends BooleanDec derParam(Dec derKey.enableT etFavRecs)

  /**
   * Param to enable MR T et Ret et Recs
   */
  object MRT etRet etRecsParam extends BooleanDec derParam(Dec derKey.enableT etRet etRecs)

  /**
   * Param to d sable wr  ng to NTAB
   * */
  object D sableWr  ngToNTAB extends Param[Boolean](default = false)

  /**
   * Param to show RUX land ng page as a modal on  OS
   */
  object ShowRuxLand ngPageAsModalOn OS extends Param[Boolean](default = false)

  /**
   * Param to enable mr end to end scr b ng
   */
  object EnableMrRequestScr b ng extends BooleanDec derParam(Dec derKey.enableMrRequestScr b ng)

  /**
   * Param to enable scr b ng of h gh qual y cand date scores
   */
  object EnableH ghQual yCand dateScoresScr b ng
      extends BooleanDec derParam(Dec derKey.enableH ghQual yCand dateScoresScr b ng)

  /**
   * Dec der controlled param to pNeg mult modal pred ct ons for F1 t ets
   */
  object EnablePnegMult modalPred ct onForF1T ets
      extends BooleanDec derParam(Dec derKey.enablePnegMult modalPred ct onForF1T ets)

  /**
   * Dec der controlled param to scr be oonFav score for F1 t ets
   */
  object EnableScr beOonFavScoreForF1T ets
      extends BooleanDec derParam(Dec derKey.enableScr b ngOonFavScoreForF1T ets)

  /**
   * Param to enable htl user aggregates extended hydrat on
   */
  object EnableHtlOffl neUserAggregatesExtendedHydrat on
      extends BooleanDec derParam(Dec derKey.enableHtlOffl neUserAggregateExtendedFeaturesHydrat on)

  /**
   * Param to enable pred cate deta led  nfo scr b ng
   */
  object EnablePred cateDeta led nfoScr b ng
      extends BooleanDec derParam(Dec derKey.enablePred cateDeta led nfoScr b ng)

  /**
   * Param to enable pred cate deta led  nfo scr b ng
   */
  object EnablePushCap nfoScr b ng
      extends BooleanDec derParam(Dec derKey.enablePred cateDeta led nfoScr b ng)

  /**
   * Param to enable user s gnal language feature hydrat on
   */
  object EnableUserS gnalLanguageFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableUserS gnalLanguageFeatureHydrat on)

  /**
   * Param to enable user preferred language feature hydrat on
   */
  object EnableUserPreferredLanguageFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableUserPreferredLanguageFeatureHydrat on)

  /**
   * Param to enable ner erg feature hydrat on
   */
  object EnableNerErgFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableNerErgFeaturesHydrat on)

  /**
   * Param to enable  nl ne act on on push copy for Andro d
   */
  object MRAndro d nl neAct onOnPushCopyParam extends Param[Boolean](default = true)

  /**
   * Param to enable hydrat ng mr user semant c core embedd ng features
   * */
  object EnableMrUserSemant cCoreFeaturesHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrUserSemant cCoreFeaturesHydrat on)

  /**
   * Param to enable hydrat ng mr user semant c core embedd ng features f ltered by 0.0000001
   * */
  object EnableMrUserSemant cCoreNoZeroFeaturesHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrUserSemant cCoreNoZeroFeaturesHydrat on)

  /*
   * Param to enable days s nce user's recent resurrect on features hydrat on
   */
  object EnableDaysS nceRecentResurrect onFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableDaysS nceRecentResurrect onFeatureHydrat on)

  /*
   * Param to enable days s nce user past aggregates features hydrat on
   */
  object EnableUserPastAggregatesFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableUserPastAggregatesFeatureHydrat on)

  /*
   * Param to enable mr user s mcluster features (v2020) hydrat on
   * */
  object EnableMrUserS mclusterV2020FeaturesHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrUserS mclusterV2020FeaturesHydrat on)

  /*
   * Param to enable mr user s mcluster features (v2020) hydrat on
   * */
  object EnableMrUserS mclusterV2020NoZeroFeaturesHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrUserS mclusterV2020NoZeroFeaturesHydrat on)

  /*
   * Param to enable HTL top c engage nt realt   aggregate features
   * */
  object EnableTop cEngage ntRealT  AggregatesFeatureHydrat on
      extends BooleanDec derParam(
        Dec derKey.enableTop cEngage ntRealT  AggregatesFeatureHydrat on)

  object EnableUserTop cAggregatesFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableUserTop cAggregatesFeatureHydrat on)

  /**
   * Param to enable user author RTA feature hydrat on
   */
  object EnableHtlUserAuthorRTAFeaturesFromFeatureStoreHydrat on
      extends BooleanDec derParam(Dec derKey.enableHtlUserAuthorRealT  AggregateFeatureHydrat on)

  /**
   * Param to enable durat on s nce last v s  features
   */
  object EnableDurat onS nceLastV s Features
      extends BooleanDec derParam(Dec derKey.enableDurat onS nceLastV s FeatureHydrat on)

  object EnableT etAnnotat onFeaturesHydrat on
      extends BooleanDec derParam(Dec derKey.enableT etAnnotat onFeatureHydrat on)

  /**
   * Param to Enable v s b l y f lter ng through SpaceV s b l yL brary from SpacePred cate
   */
  object EnableSpaceV s b l yL braryF lter ng
      extends BooleanDec derParam(Dec derKey.enableSpaceV s b l yL braryF lter ng)

  /*
   * Param to enable user top c follow feature set hydrat on
   * */
  object EnableUserTop cFollowFeatureSetHydrat on
      extends BooleanDec derParam(Dec derKey.enableUserTop cFollowFeatureSet)

  /*
   * Param to enable onboard ng new user feature set hydrat on
   * */
  object EnableOnboard ngNewUserFeatureSetHydrat on
      extends BooleanDec derParam(Dec derKey.enableOnboard ngNewUserFeatureSet)

  /*
   * Param to enable mr user author sparse cont nuous feature set hydrat on
   * */
  object EnableMrUserAuthorSparseContFeatureSetHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrUserAuthorSparseContFeatureSet)

  /*
   * Param to enable mr user top c sparse cont nuous feature set hydrat on
   * */
  object EnableMrUserTop cSparseContFeatureSetHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrUserTop cSparseContFeatureSet)

  /*
   * Param to enable pengu n language feature set hydrat on
   * */
  object EnableUserPengu nLanguageFeatureSetHydrat on
      extends BooleanDec derParam(Dec derKey.enableUserPengu nLanguageFeatureSet)

  /*
   * Param to enable user engaged t et tokens feature hydrat on
   * */
  object EnableMrUserEngagedT etTokensFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrUserEngagedT etTokensFeaturesHydrat on)

  /*
   * Param to enable cand date t et tokens feature hydrat on
   * */
  object EnableMrCand dateT etTokensFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrCand dateT etTokensFeaturesHydrat on)

  /*
   * Param to enable mr user hashspace embedd ng feature set hydrat on
   * */
  object EnableMrUserHashspaceEmbedd ngFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrUserHashspaceEmbedd ngFeatureSet)

  /*
   * Param to enable mr t et sent  nt feature set hydrat on
   * */
  object EnableMrT etSent  ntFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrT etSent  ntFeatureSet)

  /*
   * Param to enable mr t et_author aggregates feature set hydrat on
   * */
  object EnableMrT etAuthorAggregatesFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrT etAuthorAggregatesFeatureSet)

  /**
   * Param to enable tw stly aggregated features
   */
  object EnableTw stlyAggregatesFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableTw stlyAggregatesFeatureHydrat on)

  /**
   * Param to enable t et twh n favor ate features
   */
  object EnableT etTwH NFavFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableT etTwH NFavFeaturesHydrat on)

  /*
   * Param to enable mr user geo feature set hydrat on
   * */
  object EnableUserGeoFeatureSetHydrat on
      extends BooleanDec derParam(Dec derKey.enableUserGeoFeatureSet)

  /*
   * Param to enable mr author geo feature set hydrat on
   * */
  object EnableAuthorGeoFeatureSetHydrat on
      extends BooleanDec derParam(Dec derKey.enableAuthorGeoFeatureSet)

  /*
   * Param to ramp up mr user geo feature set hydrat on
   * */
  object RampupUserGeoFeatureSetHydrat on
      extends BooleanDec derParam(Dec derKey.rampupUserGeoFeatureSet)

  /*
   * Param to ramp up mr author geo feature set hydrat on
   * */
  object RampupAuthorGeoFeatureSetHydrat on
      extends BooleanDec derParam(Dec derKey.rampupAuthorGeoFeatureSet)

  /*
   *  Dec der controlled param to enable Pop Geo T ets
   * */
  object PopGeoCand datesDec der extends BooleanDec derParam(Dec derKey.enablePopGeoT ets)

  /**
   * Dec der controlled param to enable Tr p Geo T ets
   */
  object Tr pGeoT etCand datesDec der
      extends BooleanDec derParam(Dec derKey.enableTr pGeoT etCand dates)

  /**
   * Dec der controlled param to enable ContentRecom nderM xerAdaptor
   */
  object ContentRecom nderM xerAdaptorDec der
      extends BooleanDec derParam(Dec derKey.enableContentRecom nderM xerAdaptor)

  /**
   * Dec der controlled param to enable Gener cCand dateAdaptor
   */
  object Gener cCand dateAdaptorDec der
      extends BooleanDec derParam(Dec derKey.enableGener cCand dateAdaptor)

  /**
   * Dec der controlled param to enable dark traff c to ContentM xer for Tr p Geo T ets
   */
  object Tr pGeoT etContentM xerDarkTraff cDec der
      extends BooleanDec derParam(Dec derKey.enableTr pGeoT etContentM xerDarkTraff c)

  /*
   *  Dec der controlled param to enable Pop Geo T ets
   * */
  object TrendsCand dateDec der extends BooleanDec derParam(Dec derKey.enableTrendsT ets)

  /*
   *  Dec der controlled param to enable  NS Traff c
   **/
  object Enable nsTraff cDec der extends BooleanDec derParam(Dec derKey.enable nsTraff c)

  /**
   * Param to enable ass gn ng pushcap w h ML pred ct ons (read from MH table).
   * D sabl ng w ll fallback to only use  ur st cs and default values.
   */
  object EnableModelBasedPushcapAss gn nts
      extends BooleanDec derParam(Dec derKey.enableModelBasedPushcapAss gn nts)

  /**
   * Param to enable twh n user engage nt feature hydrat on
   */
  object EnableTwH NUserEngage ntFeaturesHydrat on
      extends BooleanDec derParam(Dec derKey.enableTwH NUserEngage ntFeaturesHydrat on)

  /**
   * Param to enable twh n user follow feature hydrat on
   */
  object EnableTwH NUserFollowFeaturesHydrat on
      extends BooleanDec derParam(Dec derKey.enableTwH NUserFollowFeaturesHydrat on)

  /**
   * Param to enable twh n author follow feature hydrat on
   */
  object EnableTwH NAuthorFollowFeaturesHydrat on
      extends BooleanDec derParam(Dec derKey.enableTwH NAuthorFollowFeaturesHydrat on)

  /**
   * Param to enable calls to t   sT etTranslatable strato column
   */
  object Enable sT etTranslatableC ck
      extends BooleanDec derParam(Dec derKey.enable sT etTranslatable)

  /**
   * Dec der controlled param to enable mr t et s mcluster feature set hydrat on
   */
  object EnableMrT etS mClusterFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrT etS mClusterFeatureSet)

  /**
   * Dec der controlled param to enable real graph v2 feature set hydrat on
   */
  object EnableRealGraphV2FeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableRealGraphV2FeatureHydrat on)

  /**
   * Dec der controlled param to enable T et BeT feature set hydrat on
   */
  object EnableT etBeTFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableT etBeTFeatureHydrat on)

  /**
   * Dec der controlled param to enable mr user t et top c feature set hydrat on
   */
  object EnableMrOffl neUserT etTop cAggregateHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrOffl neUserT etTop cAggregate)

  /**
   * Dec der controlled param to enable mr t et s mcluster feature set hydrat on
   */
  object EnableMrOffl neUserT etS mClusterAggregateHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrOffl neUserT etS mClusterAggregate)

  /**
   * Dec der controlled param to enable user send t   features
   */
  object EnableUserSendT  FeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableUserSendT  FeatureHydrat on)

  /**
   * Dec der controlled param to enable mr user utc send t   aggregate features
   */
  object EnableMrUserUtcSendT  AggregateFeaturesHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrUserUtcSendT  AggregateFeaturesHydrat on)

  /**
   * Dec der controlled param to enable mr user local send t   aggregate features
   */
  object EnableMrUserLocalSendT  AggregateFeaturesHydrat on
      extends BooleanDec derParam(Dec derKey.enableMrUserLocalSendT  AggregateFeaturesHydrat on)

  /**
   * Dec der controlled param to enable BQML report model pred ct ons for F1 t ets
   */
  object EnableBqmlReportModelPred ct onForF1T ets
      extends BooleanDec derParam(Dec derKey.enableBqmlReportModelPred ct onForF1T ets)

  /**
   * Dec der controlled param to enable user Twh n embedd ng feature hydrat on
   */
  object EnableUserTwh nEmbedd ngFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableUserTwh nEmbedd ngFeatureHydrat on)

  /**
   * Dec der controlled param to enable author follow Twh n embedd ng feature hydrat on
   */
  object EnableAuthorFollowTwh nEmbedd ngFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableAuthorFollowTwh nEmbedd ngFeatureHydrat on)

  object EnableScr b ngMLFeaturesAsDataRecord
      extends BooleanDec derParam(Dec derKey.enableScr b ngMLFeaturesAsDataRecord)

  /**
   * Dec der controlled param to enable feature hydrat on for Ver f ed related feature
   */
  object EnableAuthorVer f edFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableAuthorVer f edFeatureHydrat on)

  /**
   * Dec der controlled param to enable feature hydrat on for creator subscr pt on related feature
   */
  object EnableAuthorCreatorSubscr pt onFeatureHydrat on
      extends BooleanDec derParam(Dec derKey.enableAuthorCreatorSubscr pt onFeatureHydrat on)

  /**
   * Dec der controlled param to d rect MH+ mcac  hydrat on for t  UserFeaturesDataset
   */
  object EnableD rectHydrat onForUserFeatures
      extends BooleanDec derParam(Dec derKey.enableD rectHydrat onForUserFeatures)
}
