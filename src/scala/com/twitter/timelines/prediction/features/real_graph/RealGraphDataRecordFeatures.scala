package com.tw ter.t  l nes.pred ct on.features.real_graph

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport com.tw ter.ml.ap .Feature._
 mport com.tw ter.t  l nes.real_graph.v1.thr ftscala.RealGraphEdgeFeature
 mport scala.collect on.JavaConverters._


object RealGraphDataRecordFeatures {
  // t  s ce user  d
  val SRC_ D = new D screte("realgraph.src_ d", Set(User d).asJava)
  // t  dest nat on user  d
  val DST_ D = new D screte("realgraph.dst_ d", Set(User d).asJava)
  // real graph   ght
  val WE GHT = new Cont nuous("realgraph.  ght", Set(UsersRealGraphScore).asJava)
  // t  number of ret ets that t  s ce user sent to t  dest nat on user
  val NUM_RETWEETS_MEAN =
    new Cont nuous("realgraph.num_ret ets. an", Set(Pr vateRet ets, Publ cRet ets).asJava)
  val NUM_RETWEETS_EWMA =
    new Cont nuous("realgraph.num_ret ets.ewma", Set(Pr vateRet ets, Publ cRet ets).asJava)
  val NUM_RETWEETS_VAR ANCE =
    new Cont nuous("realgraph.num_ret ets.var ance", Set(Pr vateRet ets, Publ cRet ets).asJava)
  val NUM_RETWEETS_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_ret ets.non_zero_days",
    Set(Pr vateRet ets, Publ cRet ets).asJava)
  val NUM_RETWEETS_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_ret ets.elapsed_days",
    Set(Pr vateRet ets, Publ cRet ets).asJava)
  val NUM_RETWEETS_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_ret ets.days_s nce_last",
    Set(Pr vateRet ets, Publ cRet ets).asJava)
  val NUM_RETWEETS_ S_M SS NG =
    new B nary("realgraph.num_ret ets. s_m ss ng", Set(Pr vateRet ets, Publ cRet ets).asJava)
  // t  number of favor es that t  s ce user sent to t  dest nat on user
  val NUM_FAVOR TES_MEAN =
    new Cont nuous("realgraph.num_favor es. an", Set(Publ cL kes, Pr vateL kes).asJava)
  val NUM_FAVOR TES_EWMA =
    new Cont nuous("realgraph.num_favor es.ewma", Set(Publ cL kes, Pr vateL kes).asJava)
  val NUM_FAVOR TES_VAR ANCE =
    new Cont nuous("realgraph.num_favor es.var ance", Set(Publ cL kes, Pr vateL kes).asJava)
  val NUM_FAVOR TES_NON_ZERO_DAYS =
    new Cont nuous("realgraph.num_favor es.non_zero_days", Set(Publ cL kes, Pr vateL kes).asJava)
  val NUM_FAVOR TES_ELAPSED_DAYS =
    new Cont nuous("realgraph.num_favor es.elapsed_days", Set(Publ cL kes, Pr vateL kes).asJava)
  val NUM_FAVOR TES_DAYS_S NCE_LAST =
    new Cont nuous("realgraph.num_favor es.days_s nce_last", Set(Publ cL kes, Pr vateL kes).asJava)
  val NUM_FAVOR TES_ S_M SS NG =
    new B nary("realgraph.num_favor es. s_m ss ng", Set(Publ cL kes, Pr vateL kes).asJava)
  // t  number of  nt ons that t  s ce user sent to t  dest nat on user
  val NUM_MENT ONS_MEAN =
    new Cont nuous("realgraph.num_ nt ons. an", Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val NUM_MENT ONS_EWMA =
    new Cont nuous("realgraph.num_ nt ons.ewma", Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val NUM_MENT ONS_VAR ANCE = new Cont nuous(
    "realgraph.num_ nt ons.var ance",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val NUM_MENT ONS_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_ nt ons.non_zero_days",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val NUM_MENT ONS_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_ nt ons.elapsed_days",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val NUM_MENT ONS_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_ nt ons.days_s nce_last",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val NUM_MENT ONS_ S_M SS NG = new B nary(
    "realgraph.num_ nt ons. s_m ss ng",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  // t  number of d rect  ssages that t  s ce user sent to t  dest nat on user
  val NUM_D RECT_MESSAGES_MEAN = new Cont nuous(
    "realgraph.num_d rect_ ssages. an",
    Set(DmEnt  esAnd tadata, CountOfDms).asJava)
  val NUM_D RECT_MESSAGES_EWMA = new Cont nuous(
    "realgraph.num_d rect_ ssages.ewma",
    Set(DmEnt  esAnd tadata, CountOfDms).asJava)
  val NUM_D RECT_MESSAGES_VAR ANCE = new Cont nuous(
    "realgraph.num_d rect_ ssages.var ance",
    Set(DmEnt  esAnd tadata, CountOfDms).asJava)
  val NUM_D RECT_MESSAGES_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_d rect_ ssages.non_zero_days",
    Set(DmEnt  esAnd tadata, CountOfDms).asJava
  )
  val NUM_D RECT_MESSAGES_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_d rect_ ssages.elapsed_days",
    Set(DmEnt  esAnd tadata, CountOfDms).asJava
  )
  val NUM_D RECT_MESSAGES_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_d rect_ ssages.days_s nce_last",
    Set(DmEnt  esAnd tadata, CountOfDms).asJava
  )
  val NUM_D RECT_MESSAGES_ S_M SS NG = new B nary(
    "realgraph.num_d rect_ ssages. s_m ss ng",
    Set(DmEnt  esAnd tadata, CountOfDms).asJava)
  // t  number of t et cl cks that t  s ce user sent to t  dest nat on user
  val NUM_TWEET_CL CKS_MEAN =
    new Cont nuous("realgraph.num_t et_cl cks. an", Set(T etsCl cked).asJava)
  val NUM_TWEET_CL CKS_EWMA =
    new Cont nuous("realgraph.num_t et_cl cks.ewma", Set(T etsCl cked).asJava)
  val NUM_TWEET_CL CKS_VAR ANCE =
    new Cont nuous("realgraph.num_t et_cl cks.var ance", Set(T etsCl cked).asJava)
  val NUM_TWEET_CL CKS_NON_ZERO_DAYS =
    new Cont nuous("realgraph.num_t et_cl cks.non_zero_days", Set(T etsCl cked).asJava)
  val NUM_TWEET_CL CKS_ELAPSED_DAYS =
    new Cont nuous("realgraph.num_t et_cl cks.elapsed_days", Set(T etsCl cked).asJava)
  val NUM_TWEET_CL CKS_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_t et_cl cks.days_s nce_last",
    Set(T etsCl cked).asJava
  )
  val NUM_TWEET_CL CKS_ S_M SS NG =
    new B nary("realgraph.num_t et_cl cks. s_m ss ng", Set(T etsCl cked).asJava)
  // t  number of l nk cl cks that t  s ce user sent to t  dest nat on user
  val NUM_L NK_CL CKS_MEAN =
    new Cont nuous("realgraph.num_l nk_cl cks. an", Set(CountOfT etEnt  esCl cked).asJava)
  val NUM_L NK_CL CKS_EWMA =
    new Cont nuous("realgraph.num_l nk_cl cks.ewma", Set(CountOfT etEnt  esCl cked).asJava)
  val NUM_L NK_CL CKS_VAR ANCE =
    new Cont nuous("realgraph.num_l nk_cl cks.var ance", Set(CountOfT etEnt  esCl cked).asJava)
  val NUM_L NK_CL CKS_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_l nk_cl cks.non_zero_days",
    Set(CountOfT etEnt  esCl cked).asJava)
  val NUM_L NK_CL CKS_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_l nk_cl cks.elapsed_days",
    Set(CountOfT etEnt  esCl cked).asJava)
  val NUM_L NK_CL CKS_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_l nk_cl cks.days_s nce_last",
    Set(CountOfT etEnt  esCl cked).asJava)
  val NUM_L NK_CL CKS_ S_M SS NG =
    new B nary("realgraph.num_l nk_cl cks. s_m ss ng", Set(CountOfT etEnt  esCl cked).asJava)
  // t  number of prof le v ews that t  s ce user sent to t  dest nat on user
  val NUM_PROF LE_V EWS_MEAN =
    new Cont nuous("realgraph.num_prof le_v ews. an", Set(Prof lesV e d).asJava)
  val NUM_PROF LE_V EWS_EWMA =
    new Cont nuous("realgraph.num_prof le_v ews.ewma", Set(Prof lesV e d).asJava)
  val NUM_PROF LE_V EWS_VAR ANCE =
    new Cont nuous("realgraph.num_prof le_v ews.var ance", Set(Prof lesV e d).asJava)
  val NUM_PROF LE_V EWS_NON_ZERO_DAYS =
    new Cont nuous("realgraph.num_prof le_v ews.non_zero_days", Set(Prof lesV e d).asJava)
  val NUM_PROF LE_V EWS_ELAPSED_DAYS =
    new Cont nuous("realgraph.num_prof le_v ews.elapsed_days", Set(Prof lesV e d).asJava)
  val NUM_PROF LE_V EWS_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_prof le_v ews.days_s nce_last",
    Set(Prof lesV e d).asJava
  )
  val NUM_PROF LE_V EWS_ S_M SS NG =
    new B nary("realgraph.num_prof le_v ews. s_m ss ng", Set(Prof lesV e d).asJava)
  // t  total d ll t   t  s ce user spends on t  target user's t ets
  val TOTAL_DWELL_T ME_MEAN =
    new Cont nuous("realgraph.total_d ll_t  . an", Set(CountOf mpress on).asJava)
  val TOTAL_DWELL_T ME_EWMA =
    new Cont nuous("realgraph.total_d ll_t  .ewma", Set(CountOf mpress on).asJava)
  val TOTAL_DWELL_T ME_VAR ANCE =
    new Cont nuous("realgraph.total_d ll_t  .var ance", Set(CountOf mpress on).asJava)
  val TOTAL_DWELL_T ME_NON_ZERO_DAYS =
    new Cont nuous("realgraph.total_d ll_t  .non_zero_days", Set(CountOf mpress on).asJava)
  val TOTAL_DWELL_T ME_ELAPSED_DAYS =
    new Cont nuous("realgraph.total_d ll_t  .elapsed_days", Set(CountOf mpress on).asJava)
  val TOTAL_DWELL_T ME_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.total_d ll_t  .days_s nce_last",
    Set(CountOf mpress on).asJava
  )
  val TOTAL_DWELL_T ME_ S_M SS NG =
    new B nary("realgraph.total_d ll_t  . s_m ss ng", Set(CountOf mpress on).asJava)
  // t  number of t  target user's t ets that t  s ce user has  nspected
  val NUM_ NSPECTED_TWEETS_MEAN =
    new Cont nuous("realgraph.num_ nspected_t ets. an", Set(CountOf mpress on).asJava)
  val NUM_ NSPECTED_TWEETS_EWMA =
    new Cont nuous("realgraph.num_ nspected_t ets.ewma", Set(CountOf mpress on).asJava)
  val NUM_ NSPECTED_TWEETS_VAR ANCE =
    new Cont nuous("realgraph.num_ nspected_t ets.var ance", Set(CountOf mpress on).asJava)
  val NUM_ NSPECTED_TWEETS_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_ nspected_t ets.non_zero_days",
    Set(CountOf mpress on).asJava
  )
  val NUM_ NSPECTED_TWEETS_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_ nspected_t ets.elapsed_days",
    Set(CountOf mpress on).asJava
  )
  val NUM_ NSPECTED_TWEETS_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_ nspected_t ets.days_s nce_last",
    Set(CountOf mpress on).asJava
  )
  val NUM_ NSPECTED_TWEETS_ S_M SS NG =
    new B nary("realgraph.num_ nspected_t ets. s_m ss ng", Set(CountOf mpress on).asJava)
  // t  number of photos  n wh ch t  s ce user has tagged t  target user
  val NUM_PHOTO_TAGS_MEAN = new Cont nuous(
    "realgraph.num_photo_tags. an",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val NUM_PHOTO_TAGS_EWMA = new Cont nuous(
    "realgraph.num_photo_tags.ewma",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val NUM_PHOTO_TAGS_VAR ANCE = new Cont nuous(
    "realgraph.num_photo_tags.var ance",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val NUM_PHOTO_TAGS_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_photo_tags.non_zero_days",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val NUM_PHOTO_TAGS_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_photo_tags.elapsed_days",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val NUM_PHOTO_TAGS_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_photo_tags.days_s nce_last",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val NUM_PHOTO_TAGS_ S_M SS NG = new B nary(
    "realgraph.num_photo_tags. s_m ss ng",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)

  val NUM_FOLLOW_MEAN = new Cont nuous(
    "realgraph.num_follow. an",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)
  val NUM_FOLLOW_EWMA = new Cont nuous(
    "realgraph.num_follow.ewma",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)
  val NUM_FOLLOW_VAR ANCE = new Cont nuous(
    "realgraph.num_follow.var ance",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)
  val NUM_FOLLOW_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_follow.non_zero_days",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)
  val NUM_FOLLOW_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_follow.elapsed_days",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)
  val NUM_FOLLOW_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_follow.days_s nce_last",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)
  val NUM_FOLLOW_ S_M SS NG = new B nary(
    "realgraph.num_follow. s_m ss ng",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)
  // t  number of blocks that t  s ce user sent to t  dest nat on user
  val NUM_BLOCKS_MEAN =
    new Cont nuous("realgraph.num_blocks. an", Set(CountOfBlocks).asJava)
  val NUM_BLOCKS_EWMA =
    new Cont nuous("realgraph.num_blocks.ewma", Set(CountOfBlocks).asJava)
  val NUM_BLOCKS_VAR ANCE =
    new Cont nuous("realgraph.num_blocks.var ance", Set(CountOfBlocks).asJava)
  val NUM_BLOCKS_NON_ZERO_DAYS =
    new Cont nuous("realgraph.num_blocks.non_zero_days", Set(CountOfBlocks).asJava)
  val NUM_BLOCKS_ELAPSED_DAYS =
    new Cont nuous("realgraph.num_blocks.elapsed_days", Set(CountOfBlocks).asJava)
  val NUM_BLOCKS_DAYS_S NCE_LAST =
    new Cont nuous("realgraph.num_blocks.days_s nce_last", Set(CountOfBlocks).asJava)
  val NUM_BLOCKS_ S_M SS NG =
    new B nary("realgraph.num_blocks. s_m ss ng", Set(CountOfBlocks).asJava)
  // t  number of mutes that t  s ce user sent to t  dest nat on user
  val NUM_MUTES_MEAN =
    new Cont nuous("realgraph.num_mutes. an", Set(CountOfMutes).asJava)
  val NUM_MUTES_EWMA =
    new Cont nuous("realgraph.num_mutes.ewma", Set(CountOfMutes).asJava)
  val NUM_MUTES_VAR ANCE =
    new Cont nuous("realgraph.num_mutes.var ance", Set(CountOfMutes).asJava)
  val NUM_MUTES_NON_ZERO_DAYS =
    new Cont nuous("realgraph.num_mutes.non_zero_days", Set(CountOfMutes).asJava)
  val NUM_MUTES_ELAPSED_DAYS =
    new Cont nuous("realgraph.num_mutes.elapsed_days", Set(CountOfMutes).asJava)
  val NUM_MUTES_DAYS_S NCE_LAST =
    new Cont nuous("realgraph.num_mutes.days_s nce_last", Set(CountOfMutes).asJava)
  val NUM_MUTES_ S_M SS NG =
    new B nary("realgraph.num_mutes. s_m ss ng", Set(CountOfMutes).asJava)
  // t  number of report as abuses that t  s ce user sent to t  dest nat on user
  val NUM_REPORTS_AS_ABUSES_MEAN =
    new Cont nuous("realgraph.num_report_as_abuses. an", Set(CountOfAbuseReports).asJava)
  val NUM_REPORTS_AS_ABUSES_EWMA =
    new Cont nuous("realgraph.num_report_as_abuses.ewma", Set(CountOfAbuseReports).asJava)
  val NUM_REPORTS_AS_ABUSES_VAR ANCE =
    new Cont nuous("realgraph.num_report_as_abuses.var ance", Set(CountOfAbuseReports).asJava)
  val NUM_REPORTS_AS_ABUSES_NON_ZERO_DAYS =
    new Cont nuous("realgraph.num_report_as_abuses.non_zero_days", Set(CountOfAbuseReports).asJava)
  val NUM_REPORTS_AS_ABUSES_ELAPSED_DAYS =
    new Cont nuous("realgraph.num_report_as_abuses.elapsed_days", Set(CountOfAbuseReports).asJava)
  val NUM_REPORTS_AS_ABUSES_DAYS_S NCE_LAST =
    new Cont nuous(
      "realgraph.num_report_as_abuses.days_s nce_last",
      Set(CountOfAbuseReports).asJava)
  val NUM_REPORTS_AS_ABUSES_ S_M SS NG =
    new B nary("realgraph.num_report_as_abuses. s_m ss ng", Set(CountOfAbuseReports).asJava)
  // t  number of report as spams that t  s ce user sent to t  dest nat on user
  val NUM_REPORTS_AS_SPAMS_MEAN =
    new Cont nuous(
      "realgraph.num_report_as_spams. an",
      Set(CountOfAbuseReports, SafetyRelat onsh ps).asJava)
  val NUM_REPORTS_AS_SPAMS_EWMA =
    new Cont nuous(
      "realgraph.num_report_as_spams.ewma",
      Set(CountOfAbuseReports, SafetyRelat onsh ps).asJava)
  val NUM_REPORTS_AS_SPAMS_VAR ANCE =
    new Cont nuous(
      "realgraph.num_report_as_spams.var ance",
      Set(CountOfAbuseReports, SafetyRelat onsh ps).asJava)
  val NUM_REPORTS_AS_SPAMS_NON_ZERO_DAYS =
    new Cont nuous(
      "realgraph.num_report_as_spams.non_zero_days",
      Set(CountOfAbuseReports, SafetyRelat onsh ps).asJava)
  val NUM_REPORTS_AS_SPAMS_ELAPSED_DAYS =
    new Cont nuous(
      "realgraph.num_report_as_spams.elapsed_days",
      Set(CountOfAbuseReports, SafetyRelat onsh ps).asJava)
  val NUM_REPORTS_AS_SPAMS_DAYS_S NCE_LAST =
    new Cont nuous(
      "realgraph.num_report_as_spams.days_s nce_last",
      Set(CountOfAbuseReports, SafetyRelat onsh ps).asJava)
  val NUM_REPORTS_AS_SPAMS_ S_M SS NG =
    new B nary(
      "realgraph.num_report_as_spams. s_m ss ng",
      Set(CountOfAbuseReports, SafetyRelat onsh ps).asJava)

  val NUM_MUTUAL_FOLLOW_MEAN = new Cont nuous(
    "realgraph.num_mutual_follow. an",
    Set(
      Follow,
      Pr vateAccountsFollo dBy,
      Publ cAccountsFollo dBy,
      Pr vateAccountsFollow ng,
      Publ cAccountsFollow ng).asJava
  )
  val NUM_MUTUAL_FOLLOW_EWMA = new Cont nuous(
    "realgraph.num_mutual_follow.ewma",
    Set(
      Follow,
      Pr vateAccountsFollo dBy,
      Publ cAccountsFollo dBy,
      Pr vateAccountsFollow ng,
      Publ cAccountsFollow ng).asJava
  )
  val NUM_MUTUAL_FOLLOW_VAR ANCE = new Cont nuous(
    "realgraph.num_mutual_follow.var ance",
    Set(
      Follow,
      Pr vateAccountsFollo dBy,
      Publ cAccountsFollo dBy,
      Pr vateAccountsFollow ng,
      Publ cAccountsFollow ng).asJava
  )
  val NUM_MUTUAL_FOLLOW_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_mutual_follow.non_zero_days",
    Set(
      Follow,
      Pr vateAccountsFollo dBy,
      Publ cAccountsFollo dBy,
      Pr vateAccountsFollow ng,
      Publ cAccountsFollow ng).asJava
  )
  val NUM_MUTUAL_FOLLOW_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_mutual_follow.elapsed_days",
    Set(
      Follow,
      Pr vateAccountsFollo dBy,
      Publ cAccountsFollo dBy,
      Pr vateAccountsFollow ng,
      Publ cAccountsFollow ng).asJava
  )
  val NUM_MUTUAL_FOLLOW_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_mutual_follow.days_s nce_last",
    Set(
      Follow,
      Pr vateAccountsFollo dBy,
      Publ cAccountsFollo dBy,
      Pr vateAccountsFollow ng,
      Publ cAccountsFollow ng).asJava
  )
  val NUM_MUTUAL_FOLLOW_ S_M SS NG = new B nary(
    "realgraph.num_mutual_follow. s_m ss ng",
    Set(
      Follow,
      Pr vateAccountsFollo dBy,
      Publ cAccountsFollo dBy,
      Pr vateAccountsFollow ng,
      Publ cAccountsFollow ng).asJava
  )

  val NUM_SMS_FOLLOW_MEAN = new Cont nuous(
    "realgraph.num_sms_follow. an",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)
  val NUM_SMS_FOLLOW_EWMA = new Cont nuous(
    "realgraph.num_sms_follow.ewma",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)
  val NUM_SMS_FOLLOW_VAR ANCE = new Cont nuous(
    "realgraph.num_sms_follow.var ance",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)
  val NUM_SMS_FOLLOW_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_sms_follow.non_zero_days",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)
  val NUM_SMS_FOLLOW_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_sms_follow.elapsed_days",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)
  val NUM_SMS_FOLLOW_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_sms_follow.days_s nce_last",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)
  val NUM_SMS_FOLLOW_ S_M SS NG = new B nary(
    "realgraph.num_sms_follow. s_m ss ng",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)

  val NUM_ADDRESS_BOOK_EMA L_MEAN =
    new Cont nuous("realgraph.num_address_book_ema l. an", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_EMA L_EWMA =
    new Cont nuous("realgraph.num_address_book_ema l.ewma", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_EMA L_VAR ANCE =
    new Cont nuous("realgraph.num_address_book_ema l.var ance", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_EMA L_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_address_book_ema l.non_zero_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_EMA L_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_address_book_ema l.elapsed_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_EMA L_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_address_book_ema l.days_s nce_last",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_EMA L_ S_M SS NG =
    new B nary("realgraph.num_address_book_ema l. s_m ss ng", Set(AddressBook).asJava)

  val NUM_ADDRESS_BOOK_ N_BOTH_MEAN =
    new Cont nuous("realgraph.num_address_book_ n_both. an", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_ N_BOTH_EWMA =
    new Cont nuous("realgraph.num_address_book_ n_both.ewma", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_ N_BOTH_VAR ANCE = new Cont nuous(
    "realgraph.num_address_book_ n_both.var ance",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_ N_BOTH_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_address_book_ n_both.non_zero_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_ N_BOTH_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_address_book_ n_both.elapsed_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_ N_BOTH_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_address_book_ n_both.days_s nce_last",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_ N_BOTH_ S_M SS NG = new B nary(
    "realgraph.num_address_book_ n_both. s_m ss ng",
    Set(AddressBook).asJava
  )

  val NUM_ADDRESS_BOOK_PHONE_MEAN =
    new Cont nuous("realgraph.num_address_book_phone. an", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_PHONE_EWMA =
    new Cont nuous("realgraph.num_address_book_phone.ewma", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_PHONE_VAR ANCE =
    new Cont nuous("realgraph.num_address_book_phone.var ance", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_PHONE_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_address_book_phone.non_zero_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_PHONE_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_address_book_phone.elapsed_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_PHONE_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_address_book_phone.days_s nce_last",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_PHONE_ S_M SS NG =
    new B nary("realgraph.num_address_book_phone. s_m ss ng", Set(AddressBook).asJava)

  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMA L_MEAN =
    new Cont nuous("realgraph.num_address_book_mutual_edge_ema l. an", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMA L_EWMA =
    new Cont nuous("realgraph.num_address_book_mutual_edge_ema l.ewma", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMA L_VAR ANCE =
    new Cont nuous("realgraph.num_address_book_mutual_edge_ema l.var ance", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMA L_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_address_book_mutual_edge_ema l.non_zero_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMA L_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_address_book_mutual_edge_ema l.elapsed_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMA L_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_address_book_mutual_edge_ema l.days_s nce_last",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMA L_ S_M SS NG =
    new B nary("realgraph.num_address_book_mutual_edge_ema l. s_m ss ng", Set(AddressBook).asJava)

  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_ N_BOTH_MEAN =
    new Cont nuous("realgraph.num_address_book_mutual_edge_ n_both. an", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_ N_BOTH_EWMA =
    new Cont nuous("realgraph.num_address_book_mutual_edge_ n_both.ewma", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_ N_BOTH_VAR ANCE = new Cont nuous(
    "realgraph.num_address_book_mutual_edge_ n_both.var ance",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_ N_BOTH_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_address_book_mutual_edge_ n_both.non_zero_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_ N_BOTH_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_address_book_mutual_edge_ n_both.elapsed_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_ N_BOTH_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_address_book_mutual_edge_ n_both.days_s nce_last",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_ N_BOTH_ S_M SS NG = new B nary(
    "realgraph.num_address_book_mutual_edge_ n_both. s_m ss ng",
    Set(AddressBook).asJava
  )

  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_MEAN =
    new Cont nuous("realgraph.num_address_book_mutual_edge_phone. an", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_EWMA =
    new Cont nuous("realgraph.num_address_book_mutual_edge_phone.ewma", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_VAR ANCE =
    new Cont nuous("realgraph.num_address_book_mutual_edge_phone.var ance", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_NON_ZERO_DAYS = new Cont nuous(
    "realgraph.num_address_book_mutual_edge_phone.non_zero_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_ELAPSED_DAYS = new Cont nuous(
    "realgraph.num_address_book_mutual_edge_phone.elapsed_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_DAYS_S NCE_LAST = new Cont nuous(
    "realgraph.num_address_book_mutual_edge_phone.days_s nce_last",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_ S_M SS NG =
    new B nary("realgraph.num_address_book_mutual_edge_phone. s_m ss ng", Set(AddressBook).asJava)
}

case class RealGraphEdgeDataRecordFeatures(
  edgeFeatureOpt: Opt on[RealGraphEdgeFeature],
   anFeature: Cont nuous,
  ewmaFeature: Cont nuous,
  var anceFeature: Cont nuous,
  nonZeroDaysFeature: Cont nuous,
  elapsedDaysFeature: Cont nuous,
  daysS nceLastFeature: Cont nuous,
   sM ss ngFeature: B nary)
