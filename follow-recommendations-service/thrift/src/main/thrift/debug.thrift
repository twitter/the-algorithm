namelonspacelon java com.twittelonr.follow_reloncommelonndations.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndation

// Thelonselon arelon brokelonn into thelonir own union
// beloncauselon welon can havelon felonaturelons that arelon
// complelonx flavors of thelonselon (such as Selonq)
union PrimitivelonFelonaturelonValuelon {
    1: i32 intValuelon
    2: i64 longValuelon
    3: string strValuelon
    4: bool boolValuelon
}

union FelonaturelonValuelon {
    1: PrimitivelonFelonaturelonValuelon primitivelonValuelon
}

struct DelonbugParams {
    1: optional map<string, FelonaturelonValuelon> felonaturelonOvelonrridelons
    2: optional i64 randomizationSelonelond
    3: optional bool includelonDelonbugInfoInRelonsults
    4: optional bool doNotLog
}

elonnum DelonbugCandidatelonSourcelonIdelonntifielonr {
  UTT_INTelonRelonSTS_RelonLATelonD_USelonRS_SOURCelon = 0
  UTT_PRODUCelonR_elonXPANSION_SOURCelon = 1
  UTT_SelonelonD_ACCOUNT_SOURCelon = 2
  BYF_USelonR_FOLLOW_CLUSTelonR_SIMS_SOURCelon = 3
  BYF_USelonR_FOLLOW_CLUSTelonR_SOURCelon = 4
  USelonR_FOLLOW_CLUSTelonR_SOURCelon = 5
  RelonCelonNT_SelonARCH_BASelonD_SOURCelon = 6
  PelonOPLelon_ACTIVITY_RelonCelonNT_elonNGAGelonMelonNT_SOURCelon = 7
  PelonOPLelon_ACTIVITY_RelonCelonNT_elonNGAGelonMelonNT_SIMS_SOURCelon = 8,
  RelonVelonRSelon_PHONelon_BOOK_SOURCelon = 9,
  RelonVelonRSelon_elonMAIL_BOOK_SOURCelon = 10,
  SIMS_DelonBUG_STORelon = 11,
  UTT_PRODUCelonR_ONLINelon_MBCG_SOURCelon = 12,
  BONUS_FOLLOW_CONDITIONAL_elonNGAGelonMelonNT_STORelon = 13,
  // 14 (BONUS_FOLLOW_PMI_STORelon) was delonlelontelond as it's not uselond anymorelon
  FOLLOW2VelonC_NelonARelonST_NelonIGHBORS_STORelon = 15,
  OFFLINelon_STP = 16,
  OFFLINelon_STP_BIG = 17,
  OFFLINelon_MUTUAL_FOLLOW_elonXPANSION = 18,
  RelonPelonATelonD_PROFILelon_VISITS = 19,
  TIMelon_DelonCAY_FOLLOW2VelonC_NelonARelonST_NelonIGHBORS_STORelon = 20,
  LINelonAR_RelonGRelonSSION_FOLLOW2VelonC_NelonARelonST_NelonIGHBORS_STORelon = 21,
  RelonAL_GRAPH_elonXPANSION_SOURCelon = 22,
  RelonLATABLelon_ACCOUNTS_BY_INTelonRelonST = 23,
  elonMAIL_TWelonelonT_CLICK = 24,
  GOOD_TWelonelonT_CLICK_elonNGAGelonMelonNTS = 25,
  elonNGAGelonD_FOLLOWelonR_RATIO = 26,
  TWelonelonT_SHARelon_elonNGAGelonMelonNTS = 27,
  BULK_FRIelonND_FOLLOWS = 28,
  RelonAL_GRAPH_OON_V2_SOURCelon = 30,
  CROWD_SelonARCH_ACCOUNTS = 31,
  POP_GelonOHASH = 32,
  POP_COUNTRY = 33,
  POP_COUNTRY_BACKFILL = 34,
  TWelonelonT_SHARelonR_TO_SHARelon_RelonCIPIelonNT_elonNGAGelonMelonNTS = 35,
  TWelonelonT_AUTHOR_TO_SHARelon_RelonCIPIelonNT_elonNGAGelonMelonNTS = 36,
  BULK_FRIelonND_FOLLOWS_NelonW_USelonR = 37,
  ONLINelon_STP_elonPSCORelonR = 38,
  ORGANIC_FOLLOW_ACCOUNTS = 39,
  NUX_LO_HISTORY = 40,
  TRAFFIC_ATTRIBUTION_ACCOUNTS = 41,
  ONLINelon_STP_RAW_ADDRelonSS_BOOK = 42,
  POP_GelonOHASH_QUALITY_FOLLOW = 43,
  NOTIFICATION_elonNGAGelonMelonNT = 44,
  elonFR_BY_WORLDWIDelon_PICTURelon_PRODUCelonR = 45,
  POP_GelonOHASH_RelonAL_GRAPH = 46,
}
