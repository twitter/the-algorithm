package com.tw ter.cr_m xer.param.dec der

 mport com.tw ter.servo.dec der.Dec derKeyEnum

object Dec derConstants {
  val enable althS gnalsScoreDec derKey = "enable_t et_ alth_score"
  val enableUTGRealT  T etEngage ntScoreDec derKey = "enable_utg_realt  _t et_engage nt_score"
  val enableUserAgathaScoreDec derKey = "enable_user_agatha_score"
  val enableUserT etEnt yGraphTraff cDec derKey = "enable_user_t et_ent y_graph_traff c"
  val enableUserT etGraphTraff cDec derKey = "enable_user_t et_graph_traff c"
  val enableUserV deoGraphTraff cDec derKey = "enable_user_v deo_graph_traff c"
  val enableUserAdGraphTraff cDec derKey = "enable_user_ad_graph_traff c"
  val enableS mClustersANN2DarkTraff cDec derKey = "enable_s mclusters_ann_2_dark_traff c"
  val enableQ gS m larT etsTraff cDec derKey = "enable_q g_s m lar_t ets_traff c"
  val enableFRSTraff cDec derKey = "enable_frs_traff c"
  val upperFunnelPerStepScr beRate = "upper_funnel_per_step_scr be_rate"
  val kafka ssageScr beSampleRate = "kafka_ ssage_scr be_sample_rate"
  val enableRealGraphMhStoreDec derKey = "enable_real_graph_mh_store"
  val topLevelAp Ddg tr csScr beRate = "top_level_ap _ddg_ tr cs_scr be_rate"
  val adsRecom ndat onsPerExper  ntScr beRate = "ads_recom ndat ons_per_exper  nt_scr be_rate"
  val enableScr beForBlueVer f edT etCand dates =
    "enable_scr be_for_blue_ver f ed_t et_cand dates"

  val enableUserStateStoreDec derKey = "enable_user_state_store"
  val enableUser d aRepresentat onStoreDec derKey =
    "enable_user_ d a_representat on_store"
  val enableMag cRecsRealT  AggregatesStoreDec derKey =
    "enable_mag c_recs_real_t  _aggregates_store"

  val enableEarlyb rdTraff cDec derKey = "enable_earlyb rd_traff c"

  val enableTop cT etTraff cDec derKey = "enable_top c_t et_traff c"

  val getT etRecom ndat onsCac Rate = "get_t et_recom ndat ons_cac _rate"
}

object Dec derKey extends Dec derKeyEnum {

  val enable althS gnalsScoreDec derKey: Value = Value(
    Dec derConstants.enable althS gnalsScoreDec derKey
  )

  val enableUtgRealT  T etEngage ntScoreDec derKey: Value = Value(
    Dec derConstants.enableUTGRealT  T etEngage ntScoreDec derKey
  )
  val enableUserAgathaScoreDec derKey: Value = Value(
    Dec derConstants.enableUserAgathaScoreDec derKey
  )
  val enableUser d aRepresentat onStoreDec derKey: Value = Value(
    Dec derConstants.enableUser d aRepresentat onStoreDec derKey
  )

  val enableMag cRecsRealT  AggregatesStore: Value = Value(
    Dec derConstants.enableMag cRecsRealT  AggregatesStoreDec derKey
  )

  val enableUserStateStoreDec derKey: Value = Value(
    Dec derConstants.enableUserStateStoreDec derKey
  )

  val enableRealGraphMhStoreDec derKey: Value = Value(
    Dec derConstants.enableRealGraphMhStoreDec derKey
  )

  val enableEarlyb rdTraff cDec derKey: Value = Value(
    Dec derConstants.enableEarlyb rdTraff cDec derKey)
}
