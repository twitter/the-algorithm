package com.tw ter.t  l nes.pred ct on.features.common

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureType
 mport com.tw ter.ml.ap .Feature.B nary
 mport java.lang.{Boolean => JBoolean}
 mport scala.collect on.JavaConverters._

object Comb nedFeatures {
  val  S_CL CKED =
    new B nary("t  l nes.engage nt. s_cl cked", Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_DWELLED =
    new B nary("t  l nes.engage nt. s_d lled", Set(T etsV e d, Engage ntsPr vate).asJava)
  val  S_DWELLED_ N_BOUNDS_V1 = new B nary(
    "t  l nes.engage nt. s_d lled_ n_bounds_v1",
    Set(T etsV e d, Engage ntsPr vate).asJava)
  val  S_FAVOR TED = new B nary(
    "t  l nes.engage nt. s_favor ed",
    Set(Publ cL kes, Pr vateL kes, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_FOLLOWED = new B nary(
    "t  l nes.engage nt. s_follo d",
    Set(Engage ntsPr vate, Engage ntsPubl c, Follow).asJava)
  val  S_ MPRESSED =
    new B nary("t  l nes.engage nt. s_ mpressed", Set(T etsV e d, Engage ntsPr vate).asJava)
  val  S_OPEN_L NKED = new B nary(
    "t  l nes.engage nt. s_open_l nked",
    Set(Engage ntsPr vate, L nksCl ckedOn).asJava)
  val  S_PHOTO_EXPANDED = new B nary(
    "t  l nes.engage nt. s_photo_expanded",
    Set( d aEngage ntAct v  es, Engage ntsPr vate).asJava)
  val  S_PROF LE_CL CKED = new B nary(
    "t  l nes.engage nt. s_prof le_cl cked",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_QUOTED = new B nary(
    "t  l nes.engage nt. s_quoted",
    Set(Publ cRet ets, Pr vateRet ets, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED = new B nary(
    "t  l nes.engage nt. s_repl ed",
    Set(Publ cRepl es, Pr vateRepl es, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_RETWEETED = new B nary(
    "t  l nes.engage nt. s_ret eted",
    Set(Publ cRet ets, Pr vateRet ets, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_RETWEETED_W THOUT_QUOTE = new B nary(
    "t  l nes.enagage nt. s_ret eted_w hout_quote",
    Set(Publ cRet ets, Pr vateRet ets, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_SHARE_DM_CL CKED =
    new B nary("t  l nes.engage nt. s_t et_share_dm_cl cked", Set(Engage ntsPr vate).asJava)
  val  S_SHARE_DM_SENT =
    new B nary("t  l nes.engage nt. s_t et_share_dm_sent", Set(Engage ntsPr vate).asJava)
  val  S_V DEO_PLAYBACK_25 = new B nary(
    "t  l nes.engage nt. s_v deo_playback_25",
    Set( d aEngage ntAct v  es, Engage ntsPr vate).asJava)
  val  S_V DEO_PLAYBACK_50 = new B nary(
    "t  l nes.engage nt. s_v deo_playback_50",
    Set( d aEngage ntAct v  es, Engage ntsPr vate).asJava)
  val  S_V DEO_PLAYBACK_75 = new B nary(
    "t  l nes.engage nt. s_v deo_playback_75",
    Set( d aEngage ntAct v  es, Engage ntsPr vate).asJava)
  val  S_V DEO_PLAYBACK_95 = new B nary(
    "t  l nes.engage nt. s_v deo_playback_95",
    Set( d aEngage ntAct v  es, Engage ntsPr vate).asJava)
  val  S_V DEO_PLAYBACK_COMPLETE = new B nary(
    "t  l nes.engage nt. s_v deo_playback_complete",
    Set( d aEngage ntAct v  es, Engage ntsPr vate).asJava)
  val  S_V DEO_PLAYBACK_START = new B nary(
    "t  l nes.engage nt. s_v deo_playback_start",
    Set( d aEngage ntAct v  es, Engage ntsPr vate).asJava)
  val  S_V DEO_V EWED = new B nary(
    "t  l nes.engage nt. s_v deo_v e d",
    Set( d aEngage ntAct v  es, Engage ntsPr vate).asJava)
  val  S_V DEO_QUAL TY_V EWED = new B nary(
    "t  l nes.engage nt. s_v deo_qual y_v e d",
    Set( d aEngage ntAct v  es, Engage ntsPr vate).asJava
  ) 
  // v1: post cl ck engage nts: fav, reply
  val  S_GOOD_CL CKED_CONVO_DESC_V1 = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_favor ed_or_repl ed",
    Set(
      T etsCl cked,
      Publ cL kes,
      Pr vateL kes,
      Publ cRepl es,
      Pr vateRepl es,
      Engage ntsPr vate,
      Engage ntsPubl c).asJava)
  // v2: post cl ck engage nts: cl ck
  val  S_GOOD_CL CKED_CONVO_DESC_V2 = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_v2",
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_GOOD_CL CKED_W TH_DWELL_SUM_GTE_60S = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_favor ed_or_repl ed_or_d ll_sum_gte_60_secs",
    Set(
      T etsCl cked,
      Publ cL kes,
      Pr vateL kes,
      Publ cRepl es,
      Pr vateRepl es,
      Engage ntsPr vate,
      Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_FAVOR TED = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_favor ed",
    Set(Publ cL kes, Pr vateL kes, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_REPL ED = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_repl ed",
    Set(Publ cRepl es, Pr vateRepl es, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_RETWEETED = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_ret eted",
    Set(Publ cRet ets, Pr vateRet ets, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_CL CKED = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_cl cked",
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_FOLLOWED = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_follo d",
    Set(Engage ntsPr vate).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_SHARE_DM_CL CKED = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_share_dm_cl cked",
    Set(Engage ntsPr vate).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_PROF LE_CL CKED = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_prof le_cl cked",
    Set(Engage ntsPr vate).asJava)

  val  S_GOOD_CL CKED_CONVO_DESC_UAM_GT_0 = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_uam_gt_0",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_UAM_GT_1 = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_uam_gt_1",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_UAM_GT_2 = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_uam_gt_2",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_UAM_GT_3 = new B nary(
    "t  l nes.engage nt. s_good_cl cked_convo_desc_uam_gt_3",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)

  val  S_TWEET_DETA L_DWELLED = new B nary(
    "t  l nes.engage nt. s_t et_deta l_d lled",
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_TWEET_DETA L_DWELLED_8_SEC = new B nary(
    "t  l nes.engage nt. s_t et_deta l_d lled_8_sec",
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_TWEET_DETA L_DWELLED_15_SEC = new B nary(
    "t  l nes.engage nt. s_t et_deta l_d lled_15_sec",
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_TWEET_DETA L_DWELLED_25_SEC = new B nary(
    "t  l nes.engage nt. s_t et_deta l_d lled_25_sec",
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_TWEET_DETA L_DWELLED_30_SEC = new B nary(
    "t  l nes.engage nt. s_t et_deta l_d lled_30_sec",
    Set(T etsCl cked, Engage ntsPr vate).asJava)

  val  S_PROF LE_DWELLED = new B nary(
    "t  l nes.engage nt. s_prof le_d lled",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_DWELLED_10_SEC = new B nary(
    "t  l nes.engage nt. s_prof le_d lled_10_sec",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_DWELLED_20_SEC = new B nary(
    "t  l nes.engage nt. s_prof le_d lled_20_sec",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_DWELLED_30_SEC = new B nary(
    "t  l nes.engage nt. s_prof le_d lled_30_sec",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED = new B nary(
    "t  l nes.engage nt. s_fullscreen_v deo_d lled",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED_5_SEC = new B nary(
    "t  l nes.engage nt. s_fullscreen_v deo_d lled_5_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED_10_SEC = new B nary(
    "t  l nes.engage nt. s_fullscreen_v deo_d lled_10_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED_20_SEC = new B nary(
    "t  l nes.engage nt. s_fullscreen_v deo_d lled_20_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED_30_SEC = new B nary(
    "t  l nes.engage nt. s_fullscreen_v deo_d lled_30_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_L NK_DWELLED_15_SEC = new B nary(
    "t  l nes.engage nt. s_l nk_d lled_15_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_L NK_DWELLED_30_SEC = new B nary(
    "t  l nes.engage nt. s_l nk_d lled_30_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_L NK_DWELLED_60_SEC = new B nary(
    "t  l nes.engage nt. s_l nk_d lled_60_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_HOME_LATEST_V S TED =
    new B nary("t  l nes.engage nt. s_ho _latest_v s ed", Set(Engage ntsPr vate).asJava)

  val  S_BOOKMARKED =
    new B nary("t  l nes.engage nt. s_bookmarked", Set(Engage ntsPr vate).asJava)
  val  S_SHARED =
    new B nary("t  l nes.engage nt. s_shared", Set(Engage ntsPr vate).asJava)
  val  S_SHARE_MENU_CL CKED =
    new B nary("t  l nes.engage nt. s_share_ nu_cl cked", Set(Engage ntsPr vate).asJava)

  // Negat ve engage nts
  val  S_DONT_L KE = new B nary("t  l nes.engage nt. s_dont_l ke", Set(Engage ntsPr vate).asJava)
  val  S_BLOCK_CL CKED = new B nary(
    "t  l nes.engage nt. s_block_cl cked",
    Set(Blocks, T etsCl cked, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_BLOCK_D ALOG_BLOCKED = new B nary(
    "t  l nes.engage nt. s_block_d alog_blocked",
    Set(Blocks, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_MUTE_CL CKED = new B nary(
    "t  l nes.engage nt. s_mute_cl cked",
    Set(Mutes, T etsCl cked, Engage ntsPr vate).asJava)
  val  S_MUTE_D ALOG_MUTED =
    new B nary("t  l nes.engage nt. s_mute_d alog_muted", Set(Mutes, Engage ntsPr vate).asJava)
  val  S_REPORT_TWEET_CL CKED = new B nary(
    "t  l nes.engage nt. s_report_t et_cl cked",
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_CARET_CL CKED =
    new B nary("t  l nes.engage nt. s_caret_cl cked", Set(Engage ntsPr vate).asJava)
  val  S_NOT_ABOUT_TOP C =
    new B nary("t  l nes.engage nt. s_not_about_top c", Set(Engage ntsPr vate).asJava)
  val  S_NOT_RECENT =
    new B nary("t  l nes.engage nt. s_not_recent", Set(Engage ntsPr vate).asJava)
  val  S_NOT_RELEVANT =
    new B nary("t  l nes.engage nt. s_not_relevant", Set(Engage ntsPr vate).asJava)
  val  S_SEE_FEWER =
    new B nary("t  l nes.engage nt. s_see_fe r", Set(Engage ntsPr vate).asJava)
  val  S_UNFOLLOW_TOP C =
    new B nary("t  l nes.engage nt. s_unfollow_top c", Set(Engage ntsPr vate).asJava)
  val  S_FOLLOW_TOP C =
    new B nary("t  l nes.engage nt. s_follow_top c", Set(Engage ntsPr vate).asJava)
  val  S_NOT_ NTERESTED_ N_TOP C =
    new B nary("t  l nes.engage nt. s_not_ nterested_ n_top c", Set(Engage ntsPr vate).asJava)
  val  S_NEGAT VE_FEEDBACK =
    new B nary("t  l nes.engage nt. s_negat ve_feedback", Set(Engage ntsPr vate).asJava)
  val  S_ MPL C T_POS T VE_FEEDBACK_UN ON =
    new B nary(
      "t  l nes.engage nt. s_ mpl c _pos  ve_feedback_un on",
      Set(Engage ntsPr vate).asJava)
  val  S_EXPL C T_POS T VE_FEEDBACK_UN ON =
    new B nary(
      "t  l nes.engage nt. s_expl c _pos  ve_feedback_un on",
      Set(Engage ntsPr vate).asJava)
  val  S_ALL_NEGAT VE_FEEDBACK_UN ON =
    new B nary(
      "t  l nes.engage nt. s_all_negat ve_feedback_un on",
      Set(Engage ntsPr vate).asJava)
  // Rec procal engage nts for reply forward engage nt
  val  S_REPL ED_REPLY_ MPRESSED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_repl ed_reply_ mpressed_by_author",
    Set(Engage ntsPr vate).asJava)
  val  S_REPL ED_REPLY_FAVOR TED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_repl ed_reply_favor ed_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateL kes, Publ cL kes).asJava)
  val  S_REPL ED_REPLY_QUOTED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_repl ed_reply_quoted_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateRet ets, Publ cRet ets).asJava)
  val  S_REPL ED_REPLY_REPL ED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_repl ed_reply_repl ed_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateRepl es, Publ cRepl es).asJava)
  val  S_REPL ED_REPLY_RETWEETED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_repl ed_reply_ret eted_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateRet ets, Publ cRet ets).asJava)
  val  S_REPL ED_REPLY_BLOCKED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_repl ed_reply_blocked_by_author",
    Set(Blocks, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED_REPLY_FOLLOWED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_repl ed_reply_follo d_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c, Follow).asJava)
  val  S_REPL ED_REPLY_UNFOLLOWED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_repl ed_reply_unfollo d_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED_REPLY_MUTED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_repl ed_reply_muted_by_author",
    Set(Mutes, Engage ntsPr vate).asJava)
  val  S_REPL ED_REPLY_REPORTED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_repl ed_reply_reported_by_author",
    Set(Engage ntsPr vate).asJava)

  // Rec procal engage nts for fav forward engage nt
  val  S_FAVOR TED_FAV_FAVOR TED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_favor ed_fav_favor ed_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateL kes, Publ cL kes).asJava
  )
  val  S_FAVOR TED_FAV_REPL ED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_favor ed_fav_repl ed_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateRepl es, Publ cRepl es).asJava
  )
  val  S_FAVOR TED_FAV_RETWEETED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_favor ed_fav_ret eted_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateRet ets, Publ cRet ets).asJava
  )
  val  S_FAVOR TED_FAV_FOLLOWED_BY_AUTHOR = new B nary(
    "t  l nes.engage nt. s_favor ed_fav_follo d_by_author",
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava
  )

  // def ne good prof le cl ck by cons der ng follow ng engage nts (follow, fav, reply, ret et, etc.) at prof le page
  val  S_PROF LE_CL CKED_AND_PROF LE_FOLLOW = new B nary(
    "t  l nes.engage nt. s_prof le_cl cked_and_prof le_follow",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, Follow).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_FAV = new B nary(
    "t  l nes.engage nt. s_prof le_cl cked_and_prof le_fav",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, Pr vateL kes, Publ cL kes).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_REPLY = new B nary(
    "t  l nes.engage nt. s_prof le_cl cked_and_prof le_reply",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, Pr vateRepl es, Publ cRepl es).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_RETWEET = new B nary(
    "t  l nes.engage nt. s_prof le_cl cked_and_prof le_ret et",
    Set(
      Prof lesV e d,
      Prof lesCl cked,
      Engage ntsPr vate,
      Pr vateRet ets,
      Publ cRet ets).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_TWEET_CL CK = new B nary(
    "t  l nes.engage nt. s_prof le_cl cked_and_prof le_t et_cl ck",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, T etsCl cked).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_SHARE_DM_CL CK = new B nary(
    "t  l nes.engage nt. s_prof le_cl cked_and_prof le_share_dm_cl ck",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  // T  der ved label  s t  un on of all b nary features above
  val  S_PROF LE_CL CKED_AND_PROF LE_ENGAGED = new B nary(
    "t  l nes.engage nt. s_prof le_cl cked_and_prof le_engaged",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, Engage ntsPubl c).asJava)

  // def ne bad prof le cl ck by cons der ng follow ng engage nts (user report, t et report, mute, block, etc) at prof le page
  val  S_PROF LE_CL CKED_AND_PROF LE_USER_REPORT_CL CK = new B nary(
    "t  l nes.engage nt. s_prof le_cl cked_and_prof le_user_report_cl ck",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_TWEET_REPORT_CL CK = new B nary(
    "t  l nes.engage nt. s_prof le_cl cked_and_prof le_t et_report_cl ck",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_MUTE = new B nary(
    "t  l nes.engage nt. s_prof le_cl cked_and_prof le_mute",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_BLOCK = new B nary(
    "t  l nes.engage nt. s_prof le_cl cked_and_prof le_block",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  // T  der ved label  s t  un on of bad prof le cl ck engage nts and ex st ng negat ve feedback
  val  S_NEGAT VE_FEEDBACK_V2 = new B nary(
    "t  l nes.engage nt. s_negat ve_feedback_v2",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_NEGAT VE_FEEDBACK_UN ON = new B nary(
    "t  l nes.engage nt. s_negat ve_feedback_un on",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  // don't l ke, mute or prof le page -> mute
  val  S_WEAK_NEGAT VE_FEEDBACK = new B nary(
    "t  l nes.engage nt. s_ ak_negat ve_feedback",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  // report, block or prof le page -> report, block
  val  S_STRONG_NEGAT VE_FEEDBACK = new B nary(
    "t  l nes.engage nt. s_strong_negat ve_feedback",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  // engage nt for follow ng user from any surface area
  val  S_FOLLOWED_FROM_ANY_SURFACE_AREA = new B nary(
    "t  l nes.engage nt. s_follo d_from_any_surface_area",
    Set(Engage ntsPubl c, Engage ntsPr vate).asJava)
  val  S_RELEVANCE_PROMPT_YES_CL CKED = new B nary(
    "t  l nes.engage nt. s_relevance_prompt_yes_cl cked",
    Set(Engage ntsPubl c, Engage ntsPr vate).asJava)

  // Reply downvote engage nts
  val  S_REPLY_DOWNVOTED =
    new B nary("t  l nes.engage nt. s_reply_downvoted", Set(Engage ntsPr vate).asJava)
  val  S_REPLY_DOWNVOTE_REMOVED =
    new B nary("t  l nes.engage nt. s_reply_downvote_removed", Set(Engage ntsPr vate).asJava)

  /**
   * Conta ns all engage nts that are used/consu d by real-t  
   * aggregates summ ngb rd jobs. T se engage nts need to be
   * extractable from [[Cl entEvent]].
   */
  val Engage ntsRealT  : Set[Feature[JBoolean]] = Set(
     S_CL CKED,
     S_DWELLED,
     S_FAVOR TED,
     S_FOLLOWED,
     S_OPEN_L NKED,
     S_PHOTO_EXPANDED,
     S_PROF LE_CL CKED,
     S_QUOTED,
     S_REPL ED,
     S_RETWEETED,
     S_RETWEETED_W THOUT_QUOTE,
     S_SHARE_DM_CL CKED,
     S_SHARE_DM_SENT,
     S_V DEO_PLAYBACK_50,
     S_V DEO_V EWED,
     S_V DEO_QUAL TY_V EWED
  )

  val Negat veEngage ntsRealT  : Set[Feature[JBoolean]] = Set(
     S_REPORT_TWEET_CL CKED,
     S_BLOCK_CL CKED,
     S_MUTE_CL CKED
  )

  val Negat veEngage ntsRealT  DontL ke: Set[Feature[JBoolean]] = Set(
     S_DONT_L KE
  )

  val Negat veEngage ntsSecondary: Set[Feature[JBoolean]] = Set(
     S_NOT_ NTERESTED_ N_TOP C,
     S_NOT_ABOUT_TOP C,
     S_NOT_RECENT,
     S_NOT_RELEVANT,
     S_SEE_FEWER,
     S_UNFOLLOW_TOP C
  )

  val Pr vateEngage nts: Set[Feature[JBoolean]] = Set(
     S_CL CKED,
     S_DWELLED,
     S_OPEN_L NKED,
     S_PHOTO_EXPANDED,
     S_PROF LE_CL CKED,
     S_QUOTED,
     S_V DEO_PLAYBACK_50,
     S_V DEO_QUAL TY_V EWED
  )

  val  mpressedEngage nts: Set[Feature[JBoolean]] = Set(
     S_ MPRESSED
  )

  val Pr vateEngage ntsV2: Set[Feature[JBoolean]] = Set(
     S_CL CKED,
     S_OPEN_L NKED,
     S_PHOTO_EXPANDED,
     S_PROF LE_CL CKED,
     S_V DEO_PLAYBACK_50,
     S_V DEO_QUAL TY_V EWED
  ) ++  mpressedEngage nts

  val CoreEngage nts: Set[Feature[JBoolean]] = Set(
     S_FAVOR TED,
     S_REPL ED,
     S_RETWEETED
  )

  val D llEngage nts: Set[Feature[JBoolean]] = Set(
     S_DWELLED
  )

  val Pr vateCoreEngage nts: Set[Feature[JBoolean]] = Set(
     S_CL CKED,
     S_OPEN_L NKED,
     S_PHOTO_EXPANDED,
     S_V DEO_PLAYBACK_50,
     S_V DEO_QUAL TY_V EWED
  )

  val Cond  onalEngage nts: Set[Feature[JBoolean]] = Set(
     S_GOOD_CL CKED_CONVO_DESC_V1,
     S_GOOD_CL CKED_CONVO_DESC_V2,
     S_GOOD_CL CKED_W TH_DWELL_SUM_GTE_60S
  )

  val ShareEngage nts: Set[Feature[JBoolean]] = Set(
     S_SHARED,
     S_SHARE_MENU_CL CKED
  )

  val BookmarkEngage nts: Set[Feature[JBoolean]] = Set(
     S_BOOKMARKED
  )

  val T etDeta lD llEngage nts: Set[Feature[JBoolean]] = Set(
     S_TWEET_DETA L_DWELLED,
     S_TWEET_DETA L_DWELLED_8_SEC,
     S_TWEET_DETA L_DWELLED_15_SEC,
     S_TWEET_DETA L_DWELLED_25_SEC,
     S_TWEET_DETA L_DWELLED_30_SEC
  )

  val Prof leD llEngage nts: Set[Feature[JBoolean]] = Set(
     S_PROF LE_DWELLED,
     S_PROF LE_DWELLED_10_SEC,
     S_PROF LE_DWELLED_20_SEC,
     S_PROF LE_DWELLED_30_SEC
  )

  val FullscreenV deoD llEngage nts: Set[Feature[JBoolean]] = Set(
     S_FULLSCREEN_V DEO_DWELLED,
     S_FULLSCREEN_V DEO_DWELLED_5_SEC,
     S_FULLSCREEN_V DEO_DWELLED_10_SEC,
     S_FULLSCREEN_V DEO_DWELLED_20_SEC,
     S_FULLSCREEN_V DEO_DWELLED_30_SEC
  )

  // Please do not add new engage nts  re unt l hav ng est mated t   mpact
  // to capac y requ re nts. User-author real-t   aggregates have a very
  // large key space.
  val UserAuthorEngage nts: Set[Feature[JBoolean]] = CoreEngage nts ++ D llEngage nts ++ Set(
     S_CL CKED,
     S_PROF LE_CL CKED,
     S_PHOTO_EXPANDED,
     S_V DEO_PLAYBACK_50,
     S_NEGAT VE_FEEDBACK_UN ON
  )

  val  mpl c Pos  veEngage nts: Set[Feature[JBoolean]] = Set(
     S_CL CKED,
     S_DWELLED,
     S_OPEN_L NKED,
     S_PROF LE_CL CKED,
     S_QUOTED,
     S_V DEO_PLAYBACK_50,
     S_V DEO_QUAL TY_V EWED,
     S_TWEET_DETA L_DWELLED,
     S_GOOD_CL CKED_CONVO_DESC_V1,
     S_GOOD_CL CKED_CONVO_DESC_V2,
     S_SHARED,
     S_SHARE_MENU_CL CKED,
     S_SHARE_DM_SENT,
     S_SHARE_DM_CL CKED
  )

  val Expl c Pos  veEngage nts: Set[Feature[JBoolean]] = CoreEngage nts ++ Set(
     S_FOLLOWED,
     S_QUOTED
  )

  val AllNegat veEngage nts: Set[Feature[JBoolean]] =
    Negat veEngage ntsRealT   ++ Negat veEngage ntsRealT  DontL ke ++ Set(
       S_NOT_RECENT,
       S_NOT_RELEVANT,
       S_SEE_FEWER
    )
}
