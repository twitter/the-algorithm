package com.tw ter.t  l nes.pred ct on.features. l

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport com.tw ter.ml.ap .Feature.B nary
 mport com.tw ter.ml.ap .Feature.Cont nuous
 mport com.tw ter.ml.ap .Feature.D screte
 mport com.tw ter.ml.ap .Feature.SparseB nary
 mport scala.collect on.JavaConverters._

object  TLFeatures {
  // engage nt
  val  S_RETWEETED =
    new B nary(" l.engage nt. s_ret eted", Set(Publ cRet ets, Pr vateRet ets).asJava)
  val  S_FAVOR TED =
    new B nary(" l.engage nt. s_favor ed", Set(Publ cL kes, Pr vateL kes).asJava)
  val  S_REPL ED =
    new B nary(" l.engage nt. s_repl ed", Set(Publ cRepl es, Pr vateRepl es).asJava)
  // v1: post cl ck engage nts: fav, reply
  val  S_GOOD_CL CKED_CONVO_DESC_V1 = new B nary(
    " l.engage nt. s_good_cl cked_convo_desc_favor ed_or_repl ed",
    Set(
      Publ cL kes,
      Pr vateL kes,
      Publ cRepl es,
      Pr vateRepl es,
      Engage ntsPr vate,
      Engage ntsPubl c).asJava)
  // v2: post cl ck engage nts: cl ck
  val  S_GOOD_CL CKED_CONVO_DESC_V2 = new B nary(
    " l.engage nt. s_good_cl cked_convo_desc_v2",
    Set(T etsCl cked, Engage ntsPr vate).asJava)

  val  S_GOOD_CL CKED_CONVO_DESC_FAVOR TED = new B nary(
    " l.engage nt. s_good_cl cked_convo_desc_favor ed",
    Set(Publ cL kes, Pr vateL kes).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_REPL ED = new B nary(
    " l.engage nt. s_good_cl cked_convo_desc_repl ed",
    Set(Publ cRepl es, Pr vateRepl es).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_RETWEETED = new B nary(
    " l.engage nt. s_good_cl cked_convo_desc_ret eted",
    Set(Publ cRet ets, Pr vateRet ets, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_CL CKED = new B nary(
    " l.engage nt. s_good_cl cked_convo_desc_cl cked",
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_FOLLOWED =
    new B nary(" l.engage nt. s_good_cl cked_convo_desc_follo d", Set(Engage ntsPr vate).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_SHARE_DM_CL CKED = new B nary(
    " l.engage nt. s_good_cl cked_convo_desc_share_dm_cl cked",
    Set(Engage ntsPr vate).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_PROF LE_CL CKED = new B nary(
    " l.engage nt. s_good_cl cked_convo_desc_prof le_cl cked",
    Set(Engage ntsPr vate).asJava)

  val  S_GOOD_CL CKED_CONVO_DESC_UAM_GT_0 = new B nary(
    " l.engage nt. s_good_cl cked_convo_desc_uam_gt_0",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_UAM_GT_1 = new B nary(
    " l.engage nt. s_good_cl cked_convo_desc_uam_gt_1",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_UAM_GT_2 = new B nary(
    " l.engage nt. s_good_cl cked_convo_desc_uam_gt_2",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_UAM_GT_3 = new B nary(
    " l.engage nt. s_good_cl cked_convo_desc_uam_gt_3",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)

  val  S_TWEET_DETA L_DWELLED = new B nary(
    " l.engage nt. s_t et_deta l_d lled",
    Set(T etsCl cked, Engage ntsPr vate).asJava)

  val  S_TWEET_DETA L_DWELLED_8_SEC = new B nary(
    " l.engage nt. s_t et_deta l_d lled_8_sec",
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_TWEET_DETA L_DWELLED_15_SEC = new B nary(
    " l.engage nt. s_t et_deta l_d lled_15_sec",
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_TWEET_DETA L_DWELLED_25_SEC = new B nary(
    " l.engage nt. s_t et_deta l_d lled_25_sec",
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_TWEET_DETA L_DWELLED_30_SEC = new B nary(
    " l.engage nt. s_t et_deta l_d lled_30_sec",
    Set(T etsCl cked, Engage ntsPr vate).asJava)

  val  S_PROF LE_DWELLED = new B nary(
    " l.engage nt. s_prof le_d lled",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_DWELLED_10_SEC = new B nary(
    " l.engage nt. s_prof le_d lled_10_sec",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_DWELLED_20_SEC = new B nary(
    " l.engage nt. s_prof le_d lled_20_sec",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_DWELLED_30_SEC = new B nary(
    " l.engage nt. s_prof le_d lled_30_sec",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED = new B nary(
    " l.engage nt. s_fullscreen_v deo_d lled",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED_5_SEC = new B nary(
    " l.engage nt. s_fullscreen_v deo_d lled_5_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED_10_SEC = new B nary(
    " l.engage nt. s_fullscreen_v deo_d lled_10_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED_20_SEC = new B nary(
    " l.engage nt. s_fullscreen_v deo_d lled_20_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED_30_SEC = new B nary(
    " l.engage nt. s_fullscreen_v deo_d lled_30_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_L NK_DWELLED_15_SEC = new B nary(
    " l.engage nt. s_l nk_d lled_15_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_L NK_DWELLED_30_SEC = new B nary(
    " l.engage nt. s_l nk_d lled_30_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_L NK_DWELLED_60_SEC = new B nary(
    " l.engage nt. s_l nk_d lled_60_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_QUOTED =
    new B nary(" l.engage nt. s_quoted", Set(Publ cRet ets, Pr vateRet ets).asJava)
  val  S_RETWEETED_W THOUT_QUOTE = new B nary(
    " l.engage nt. s_ret eted_w hout_quote",
    Set(Publ cRet ets, Pr vateRet ets).asJava)
  val  S_CL CKED = new B nary(
    " l.engage nt. s_cl cked",
    Set(Engage ntsPr vate, T etsCl cked, L nksCl ckedOn).asJava)
  val  S_PROF LE_CL CKED = new B nary(
    " l.engage nt. s_prof le_cl cked",
    Set(Engage ntsPr vate, T etsCl cked, Prof lesV e d, Prof lesCl cked).asJava)
  val  S_DWELLED = new B nary(" l.engage nt. s_d lled", Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_ N_BOUNDS_V1 =
    new B nary(" l.engage nt. s_d lled_ n_bounds_v1", Set(Engage ntsPr vate).asJava)
  val DWELL_NORMAL ZED_OVERALL =
    new Cont nuous(" l.engage nt.d ll_normal zed_overall", Set(Engage ntsPr vate).asJava)
  val DWELL_CDF_OVERALL =
    new Cont nuous(" l.engage nt.d ll_cdf_overall", Set(Engage ntsPr vate).asJava)
  val DWELL_CDF = new Cont nuous(" l.engage nt.d ll_cdf", Set(Engage ntsPr vate).asJava)

  val  S_DWELLED_1S = new B nary(" l.engage nt. s_d lled_1s", Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_2S = new B nary(" l.engage nt. s_d lled_2s", Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_3S = new B nary(" l.engage nt. s_d lled_3s", Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_4S = new B nary(" l.engage nt. s_d lled_4s", Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_5S = new B nary(" l.engage nt. s_d lled_5s", Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_6S = new B nary(" l.engage nt. s_d lled_6s", Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_7S = new B nary(" l.engage nt. s_d lled_7s", Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_8S = new B nary(" l.engage nt. s_d lled_8s", Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_9S = new B nary(" l.engage nt. s_d lled_9s", Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_10S = new B nary(" l.engage nt. s_d lled_10s", Set(Engage ntsPr vate).asJava)

  val  S_SK PPED_1S = new B nary(" l.engage nt. s_sk pped_1s", Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_2S = new B nary(" l.engage nt. s_sk pped_2s", Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_3S = new B nary(" l.engage nt. s_sk pped_3s", Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_4S = new B nary(" l.engage nt. s_sk pped_4s", Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_5S = new B nary(" l.engage nt. s_sk pped_5s", Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_6S = new B nary(" l.engage nt. s_sk pped_6s", Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_7S = new B nary(" l.engage nt. s_sk pped_7s", Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_8S = new B nary(" l.engage nt. s_sk pped_8s", Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_9S = new B nary(" l.engage nt. s_sk pped_9s", Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_10S = new B nary(" l.engage nt. s_sk pped_10s", Set(Engage ntsPr vate).asJava)

  val  S_FOLLOWED =
    new B nary(" l.engage nt. s_follo d", Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_ MPRESSED = new B nary(" l.engage nt. s_ mpressed", Set(Engage ntsPr vate).asJava)
  val  S_OPEN_L NKED =
    new B nary(" l.engage nt. s_open_l nked", Set(Engage ntsPr vate, L nksCl ckedOn).asJava)
  val  S_PHOTO_EXPANDED = new B nary(
    " l.engage nt. s_photo_expanded",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_V DEO_V EWED =
    new B nary(" l.engage nt. s_v deo_v e d", Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_V DEO_PLAYBACK_50 = new B nary(
    " l.engage nt. s_v deo_playback_50",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_V DEO_QUAL TY_V EWED = new B nary(
    " l.engage nt. s_v deo_qual y_v e d",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava
  ) 
  val  S_BOOKMARKED =
    new B nary(" l.engage nt. s_bookmarked", Set(Engage ntsPr vate).asJava)
  val  S_SHARED =
    new B nary(" l.engage nt. s_shared", Set(Engage ntsPr vate).asJava)
  val  S_SHARE_MENU_CL CKED =
    new B nary(" l.engage nt. s_share_ nu_cl cked", Set(Engage ntsPr vate).asJava)

  // Negat ve engage nts
  val  S_DONT_L KE =
    new B nary(" l.engage nt. s_dont_l ke", Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_BLOCK_CL CKED = new B nary(
    " l.engage nt. s_block_cl cked",
    Set(T etsCl cked, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_BLOCK_D ALOG_BLOCKED = new B nary(
    " l.engage nt. s_block_d alog_blocked",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_MUTE_CL CKED =
    new B nary(" l.engage nt. s_mute_cl cked", Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_MUTE_D ALOG_MUTED =
    new B nary(" l.engage nt. s_mute_d alog_muted", Set(Engage ntsPr vate).asJava)
  val  S_REPORT_TWEET_CL CKED = new B nary(
    " l.engage nt. s_report_t et_cl cked",
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_CARET_CL CKED =
    new B nary(" l.engage nt. s_caret_cl cked", Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_NOT_ABOUT_TOP C =
    new B nary(" l.engage nt. s_not_about_top c", Set(Engage ntsPr vate).asJava)
  val  S_NOT_RECENT =
    new B nary(" l.engage nt. s_not_recent", Set(Engage ntsPr vate).asJava)
  val  S_NOT_RELEVANT =
    new B nary(" l.engage nt. s_not_relevant", Set(Engage ntsPr vate).asJava)
  val  S_SEE_FEWER =
    new B nary(" l.engage nt. s_see_fe r", Set(Engage ntsPr vate).asJava)
  val  S_UNFOLLOW_TOP C =
    new B nary(" l.engage nt. s_unfollow_top c", Set(Engage ntsPr vate).asJava)
  val  S_FOLLOW_TOP C =
    new B nary(" l.engage nt. s_follow_top c", Set(Engage ntsPr vate).asJava)
  val  S_NOT_ NTERESTED_ N_TOP C =
    new B nary(" l.engage nt. s_not_ nterested_ n_top c", Set(Engage ntsPr vate).asJava)
  val  S_HOME_LATEST_V S TED =
    new B nary(" l.engage nt. s_ho _latest_v s ed", Set(Engage ntsPr vate).asJava)

  // T  der ved label  s t  log cal OR of  S_DONT_L KE,  S_BLOCK_CL CKED,  S_MUTE_CL CKED and  S_REPORT_TWEET_CL CKED
  val  S_NEGAT VE_FEEDBACK =
    new B nary(" l.engage nt. s_negat ve_feedback", Set(Engage ntsPr vate).asJava)

  // Rec procal engage nts for reply forward engage nt
  val  S_REPL ED_REPLY_ MPRESSED_BY_AUTHOR = new B nary(
    " l.engage nt. s_repl ed_reply_ mpressed_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED_REPLY_FAVOR TED_BY_AUTHOR = new B nary(
    " l.engage nt. s_repl ed_reply_favor ed_by_author",
    Set(Publ cL kes, Pr vateL kes, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED_REPLY_QUOTED_BY_AUTHOR = new B nary(
    " l.engage nt. s_repl ed_reply_quoted_by_author",
    Set(Publ cRet ets, Pr vateRet ets, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED_REPLY_REPL ED_BY_AUTHOR = new B nary(
    " l.engage nt. s_repl ed_reply_repl ed_by_author",
    Set(Publ cRepl es, Pr vateRepl es, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED_REPLY_RETWEETED_BY_AUTHOR = new B nary(
    " l.engage nt. s_repl ed_reply_ret eted_by_author",
    Set(Publ cRet ets, Pr vateRet ets, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED_REPLY_BLOCKED_BY_AUTHOR = new B nary(
    " l.engage nt. s_repl ed_reply_blocked_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED_REPLY_FOLLOWED_BY_AUTHOR = new B nary(
    " l.engage nt. s_repl ed_reply_follo d_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED_REPLY_UNFOLLOWED_BY_AUTHOR = new B nary(
    " l.engage nt. s_repl ed_reply_unfollo d_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED_REPLY_MUTED_BY_AUTHOR = new B nary(
    " l.engage nt. s_repl ed_reply_muted_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED_REPLY_REPORTED_BY_AUTHOR = new B nary(
    " l.engage nt. s_repl ed_reply_reported_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)

  // T  der ved label  s t  log cal OR of REPLY_REPL ED, REPLY_FAVOR TED, REPLY_RETWEETED
  val  S_REPL ED_REPLY_ENGAGED_BY_AUTHOR = new B nary(
    " l.engage nt. s_repl ed_reply_engaged_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)

  // Rec procal engage nts for fav forward engage nt
  val  S_FAVOR TED_FAV_FAVOR TED_BY_AUTHOR = new B nary(
    " l.engage nt. s_favor ed_fav_favor ed_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateL kes, Publ cL kes).asJava
  )
  val  S_FAVOR TED_FAV_REPL ED_BY_AUTHOR = new B nary(
    " l.engage nt. s_favor ed_fav_repl ed_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateRepl es, Publ cRepl es).asJava
  )
  val  S_FAVOR TED_FAV_RETWEETED_BY_AUTHOR = new B nary(
    " l.engage nt. s_favor ed_fav_ret eted_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateRet ets, Publ cRet ets).asJava
  )
  val  S_FAVOR TED_FAV_FOLLOWED_BY_AUTHOR = new B nary(
    " l.engage nt. s_favor ed_fav_follo d_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava
  )
  // T  der ved label  s t  log cal OR of FAV_REPL ED, FAV_FAVOR TED, FAV_RETWEETED, FAV_FOLLOWED
  val  S_FAVOR TED_FAV_ENGAGED_BY_AUTHOR = new B nary(
    " l.engage nt. s_favor ed_fav_engaged_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava
  )

  // def ne good prof le cl ck by cons der ng follow ng engage nts (follow, fav, reply, ret et, etc.) at prof le page
  val  S_PROF LE_CL CKED_AND_PROF LE_FOLLOW = new B nary(
    " l.engage nt. s_prof le_cl cked_and_prof le_follow",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, Follow).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_FAV = new B nary(
    " l.engage nt. s_prof le_cl cked_and_prof le_fav",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, Pr vateL kes, Publ cL kes).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_REPLY = new B nary(
    " l.engage nt. s_prof le_cl cked_and_prof le_reply",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, Pr vateRepl es, Publ cRepl es).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_RETWEET = new B nary(
    " l.engage nt. s_prof le_cl cked_and_prof le_ret et",
    Set(
      Prof lesV e d,
      Prof lesCl cked,
      Engage ntsPr vate,
      Pr vateRet ets,
      Publ cRet ets).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_TWEET_CL CK = new B nary(
    " l.engage nt. s_prof le_cl cked_and_prof le_t et_cl ck",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, T etsCl cked).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_SHARE_DM_CL CK = new B nary(
    " l.engage nt. s_prof le_cl cked_and_prof le_share_dm_cl ck",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  // T  der ved label  s t  un on of all b nary features above
  val  S_PROF LE_CL CKED_AND_PROF LE_ENGAGED = new B nary(
    " l.engage nt. s_prof le_cl cked_and_prof le_engaged",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, Engage ntsPubl c).asJava)

  // def ne bad prof le cl ck by cons der ng follow ng engage nts (user report, t et report, mute, block, etc) at prof le page
  val  S_PROF LE_CL CKED_AND_PROF LE_USER_REPORT_CL CK = new B nary(
    " l.engage nt. s_prof le_cl cked_and_prof le_user_report_cl ck",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_TWEET_REPORT_CL CK = new B nary(
    " l.engage nt. s_prof le_cl cked_and_prof le_t et_report_cl ck",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_MUTE = new B nary(
    " l.engage nt. s_prof le_cl cked_and_prof le_mute",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_BLOCK = new B nary(
    " l.engage nt. s_prof le_cl cked_and_prof le_block",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  // T  der ved label  s t  un on of bad prof le cl ck engage nts and ex st ng negat ve feedback
  val  S_NEGAT VE_FEEDBACK_V2 = new B nary(
    " l.engage nt. s_negat ve_feedback_v2",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  // engage nt for follow ng user from any surface area
  val  S_FOLLOWED_FROM_ANY_SURFACE_AREA = new B nary(
    " l.engage nt. s_follo d_from_any_surface_area",
    Set(Engage ntsPubl c, Engage ntsPr vate).asJava)

  // Relevance prompt t et engage nts
  val  S_RELEVANCE_PROMPT_YES_CL CKED =
    new B nary(" l.engage nt. s_relevance_prompt_yes_cl cked", Set(Engage ntsPr vate).asJava)

  // Reply downvote engage nts
  val  S_REPLY_DOWNVOTED =
    new B nary(" l.engage nt. s_reply_downvoted", Set(Engage ntsPr vate).asJava)
  val  S_REPLY_DOWNVOTE_REMOVED =
    new B nary(" l.engage nt. s_reply_downvote_removed", Set(Engage ntsPr vate).asJava)

  // features from Recom ndedT et
  val RECTWEET_SCORE = new Cont nuous(" l.recom nded_t et_features.rect et_score")
  val NUM_FAVOR T NG_USERS = new Cont nuous(" l.recom nded_t et_features.num_favor  ng_users")
  val NUM_FOLLOW NG_USERS = new Cont nuous(" l.recom nded_t et_features.num_follow ng_users")
  val CONTENT_SOURCE_TYPE = new D screte(" l.recom nded_t et_features.content_s ce_type")

  val RECOS_SCORE = new Cont nuous(
    " l.recom nded_t et_features.recos_score",
    Set(Engage ntScore, UsersRealGraphScore, UsersSalsaScore).asJava)
  val AUTHOR_REALGRAPH_SCORE = new Cont nuous(
    " l.recom nded_t et_features.realgraph_score",
    Set(UsersRealGraphScore).asJava)
  val AUTHOR_SARUS_SCORE = new Cont nuous(
    " l.recom nded_t et_features.sarus_score",
    Set(Engage ntScore, UsersSalsaScore).asJava)

  val NUM_ NTERACT NG_USERS = new Cont nuous(
    " l.recom nded_t et_features.num_ nteract ng_users",
    Set(Engage ntScore).asJava
  )
  val MAX_REALGRAPH_SCORE_OF_ NTERACT NG_USERS = new Cont nuous(
    " l.recom nded_t et_features.max_realgraph_score_of_ nteract ng_users",
    Set(UsersRealGraphScore, Engage ntScore).asJava
  )
  val SUM_REALGRAPH_SCORE_OF_ NTERACT NG_USERS = new Cont nuous(
    " l.recom nded_t et_features.sum_realgraph_score_of_ nteract ng_users",
    Set(UsersRealGraphScore, Engage ntScore).asJava
  )
  val AVG_REALGRAPH_SCORE_OF_ NTERACT NG_USERS = new Cont nuous(
    " l.recom nded_t et_features.avg_realgraph_score_of_ nteract ng_users",
    Set(UsersRealGraphScore, Engage ntScore).asJava
  )
  val MAX_SARUS_SCORE_OF_ NTERACT NG_USERS = new Cont nuous(
    " l.recom nded_t et_features.max_sarus_score_of_ nteract ng_users",
    Set(Engage ntScore, UsersSalsaScore).asJava
  )
  val SUM_SARUS_SCORE_OF_ NTERACT NG_USERS = new Cont nuous(
    " l.recom nded_t et_features.sum_sarus_score_of_ nteract ng_users",
    Set(Engage ntScore, UsersSalsaScore).asJava
  )
  val AVG_SARUS_SCORE_OF_ NTERACT NG_USERS = new Cont nuous(
    " l.recom nded_t et_features.avg_sarus_score_of_ nteract ng_users",
    Set(Engage ntScore, UsersSalsaScore).asJava
  )

  val NUM_ NTERACT NG_FOLLOW NGS = new Cont nuous(
    " l.recom nded_t et_features.num_ nteract ng_follow ngs",
    Set(Engage ntScore).asJava
  )

  // features from HydratedT etFeatures
  val REAL_GRAPH_WE GHT =
    new Cont nuous(" l.hydrated_t et_features.real_graph_  ght", Set(UsersRealGraphScore).asJava)
  val SARUS_GRAPH_WE GHT = new Cont nuous(" l.hydrated_t et_features.sarus_graph_  ght")
  val FROM_TOP_ENGAGED_USER = new B nary(" l.hydrated_t et_features.from_top_engaged_user")
  val FROM_TOP_ NFLUENCER = new B nary(" l.hydrated_t et_features.from_top_ nfluencer")
  val TOP C_S M_SEARCHER_ NTERSTED_ N_AUTHOR_KNOWN_FOR = new Cont nuous(
    " l.hydrated_t et_features.top c_s m_searc r_ nterested_ n_author_known_for"
  )
  val TOP C_S M_SEARCHER_AUTHOR_BOTH_ NTERESTED_ N = new Cont nuous(
    " l.hydrated_t et_features.top c_s m_searc r_author_both_ nterested_ n"
  )
  val TOP C_S M_SEARCHER_AUTHOR_BOTH_KNOWN_FOR = new Cont nuous(
    " l.hydrated_t et_features.top c_s m_searc r_author_both_known_for"
  )
  val USER_REP = new Cont nuous(" l.hydrated_t et_features.user_rep")
  val NORMAL ZED_PARUS_SCORE = new Cont nuous(" l.hydrated_t et_features.normal zed_parus_score")
  val CONTA NS_MED A = new B nary(" l.hydrated_t et_features.conta ns_ d a")
  val FROM_NEARBY = new B nary(" l.hydrated_t et_features.from_nearby")
  val TOP C_S M_SEARCHER_ NTERESTED_ N_TWEET = new Cont nuous(
    " l.hydrated_t et_features.top c_s m_searc r_ nterested_ n_t et"
  )
  val MATCHES_U _LANG = new B nary(
    " l.hydrated_t et_features.matc s_u _lang",
    Set(Prov dedLanguage,  nferredLanguage).asJava)
  val MATCHES_SEARCHER_MA N_LANG = new B nary(
    " l.hydrated_t et_features.matc s_searc r_ma n_lang",
    Set(Prov dedLanguage,  nferredLanguage).asJava
  )
  val MATCHES_SEARCHER_LANGS = new B nary(
    " l.hydrated_t et_features.matc s_searc r_langs",
    Set(Prov dedLanguage,  nferredLanguage).asJava)
  val HAS_CARD = new B nary(
    " l.hydrated_t et_features.has_card",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_ MAGE = new B nary(
    " l.hydrated_t et_features.has_ mage",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_NAT VE_ MAGE = new B nary(
    " l.hydrated_t et_features.has_nat ve_ mage",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_V DEO = new B nary(" l.hydrated_t et_features.has_v deo")
  val HAS_CONSUMER_V DEO = new B nary(
    " l.hydrated_t et_features.has_consu r_v deo",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_PRO_V DEO = new B nary(
    " l.hydrated_t et_features.has_pro_v deo",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_PER SCOPE = new B nary(
    " l.hydrated_t et_features.has_per scope",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_V NE = new B nary(
    " l.hydrated_t et_features.has_v ne",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_NAT VE_V DEO = new B nary(
    " l.hydrated_t et_features.has_nat ve_v deo",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_L NK = new B nary(
    " l.hydrated_t et_features.has_l nk",
    Set(UrlFoundFlag, Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val L NK_COUNT = new Cont nuous(
    " l.hydrated_t et_features.l nk_count",
    Set(CountOfPr vateT etEnt  esAnd tadata, CountOfPubl cT etEnt  esAnd tadata).asJava)
  val URL_DOMA NS = new SparseB nary(
    " l.hydrated_t et_features.url_doma ns",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_V S BLE_L NK = new B nary(
    " l.hydrated_t et_features.has_v s ble_l nk",
    Set(UrlFoundFlag, Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_NEWS = new B nary(
    " l.hydrated_t et_features.has_news",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_TREND = new B nary(
    " l.hydrated_t et_features.has_trend",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val BLENDER_SCORE =
    new Cont nuous(" l.hydrated_t et_features.blender_score", Set(Engage ntScore).asJava)
  val PARUS_SCORE =
    new Cont nuous(" l.hydrated_t et_features.parus_score", Set(Engage ntScore).asJava)
  val TEXT_SCORE =
    new Cont nuous(" l.hydrated_t et_features.text_score", Set(Engage ntScore).asJava)
  val B D RECT ONAL_REPLY_COUNT = new Cont nuous(
    " l.hydrated_t et_features.b d rect onal_reply_count",
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava
  )
  val UN D RECT ONAL_REPLY_COUNT = new Cont nuous(
    " l.hydrated_t et_features.un d rect onal_reply_count",
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava
  )
  val B D RECT ONAL_RETWEET_COUNT = new Cont nuous(
    " l.hydrated_t et_features.b d rect onal_ret et_count",
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava
  )
  val UN D RECT ONAL_RETWEET_COUNT = new Cont nuous(
    " l.hydrated_t et_features.un d rect onal_ret et_count",
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava
  )
  val B D RECT ONAL_FAV_COUNT = new Cont nuous(
    " l.hydrated_t et_features.b d rect onal_fav_count",
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava
  )
  val UN D RECT ONAL_FAV_COUNT = new Cont nuous(
    " l.hydrated_t et_features.un d rect onal_fav_count",
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava
  )
  val CONVERSAT ON_COUNT = new Cont nuous(" l.hydrated_t et_features.conversat on_count")
  val FAV_COUNT = new Cont nuous(
    " l.hydrated_t et_features.fav_count",
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava)
  val REPLY_COUNT = new Cont nuous(
    " l.hydrated_t et_features.reply_count",
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava)
  val RETWEET_COUNT = new Cont nuous(
    " l.hydrated_t et_features.ret et_count",
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava)
  val PREV_USER_TWEET_ENGAGEMENT = new Cont nuous(
    " l.hydrated_t et_features.prev_user_t et_enagage nt",
    Set(Engage ntScore, Engage ntsPr vate, Engage ntsPubl c).asJava
  )
  val  S_SENS T VE = new B nary(" l.hydrated_t et_features. s_sens  ve")
  val HAS_MULT PLE_MED A = new B nary(
    " l.hydrated_t et_features.has_mult ple_ d a",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_MULT PLE_HASHTAGS_OR_TRENDS = new B nary(
    " l.hydrated_t et_features.has_mult ple_hashtag_or_trend",
    Set(
      UserV s bleFlag,
      CountOfPr vateT etEnt  esAnd tadata,
      CountOfPubl cT etEnt  esAnd tadata).asJava)
  val  S_AUTHOR_PROF LE_EGG =
    new B nary(" l.hydrated_t et_features. s_author_prof le_egg", Set(Prof le mage).asJava)
  val  S_AUTHOR_NEW =
    new B nary(" l.hydrated_t et_features. s_author_new", Set(UserType, UserState).asJava)
  val NUM_MENT ONS = new Cont nuous(
    " l.hydrated_t et_features.num_ nt ons",
    Set(
      UserV s bleFlag,
      CountOfPr vateT etEnt  esAnd tadata,
      CountOfPubl cT etEnt  esAnd tadata).asJava)
  val NUM_HASHTAGS = new Cont nuous(
    " l.hydrated_t et_features.num_hashtags",
    Set(CountOfPr vateT etEnt  esAnd tadata, CountOfPubl cT etEnt  esAnd tadata).asJava)
  val LANGUAGE = new D screte(
    " l.hydrated_t et_features.language",
    Set(Prov dedLanguage,  nferredLanguage).asJava)
  val L NK_LANGUAGE = new Cont nuous(
    " l.hydrated_t et_features.l nk_language",
    Set(Prov dedLanguage,  nferredLanguage).asJava)
  val  S_AUTHOR_NSFW =
    new B nary(" l.hydrated_t et_features. s_author_nsfw", Set(UserType).asJava)
  val  S_AUTHOR_SPAM =
    new B nary(" l.hydrated_t et_features. s_author_spam", Set(UserType).asJava)
  val  S_AUTHOR_BOT = new B nary(" l.hydrated_t et_features. s_author_bot", Set(UserType).asJava)
  val  S_OFFENS VE = new B nary(" l.hydrated_t et_features. s_offens ve")
  val FROM_VER F ED_ACCOUNT =
    new B nary(" l.hydrated_t et_features.from_ver f ed_account", Set(UserVer f edFlag).asJava)
  val EMBEDS_ MPRESS ON_COUNT = new Cont nuous(
    " l.hydrated_t et_features.embeds_ mpress on_count",
    Set(CountOf mpress on).asJava)
  val EMBEDS_URL_COUNT =
    new Cont nuous(" l.hydrated_t et_features.embeds_url_count", Set(UrlFoundFlag).asJava)
  val FAV_COUNT_V2 = new Cont nuous(
    "recap.earlyb rd.fav_count_v2",
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava)
  val RETWEET_COUNT_V2 = new Cont nuous(
    "recap.earlyb rd.ret et_count_v2",
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava)
  val REPLY_COUNT_V2 = new Cont nuous(
    "recap.earlyb rd.reply_count_v2",
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava)
}
