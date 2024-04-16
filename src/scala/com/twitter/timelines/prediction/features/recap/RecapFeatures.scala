package com.tw ter.t  l nes.pred ct on.features.recap

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport com.tw ter.ml.ap .Feature.B nary
 mport com.tw ter.ml.ap .Feature.Cont nuous
 mport com.tw ter.ml.ap .Feature.D screte
 mport com.tw ter.ml.ap .Feature.SparseB nary
 mport com.tw ter.ml.ap .Feature.Text
 mport scala.collect on.JavaConverters._

object RecapFeatures extends RecapFeatures("")
object  nReplyToRecapFeatures extends RecapFeatures(" n_reply_to_t et")

class RecapFeatures(pref x: Str ng) {
  pr vate def na (featureNa : Str ng): Str ng = {
     f (pref x.nonEmpty) {
      s"$pref x.$featureNa "
    } else {
      featureNa 
    }
  }

  val  S_ PAD_CL ENT = new B nary(na ("recap.cl ent. s_ pad"), Set(Cl entType).asJava)
  val  S_WEB_CL ENT = new B nary(na ("recap.cl ent. s_ b"), Set(Cl entType).asJava)
  val  S_ PHONE_CL ENT = new B nary(na ("recap.cl ent. s_phone"), Set(Cl entType).asJava)
  val  S_ANDRO D_CL ENT = new B nary(na ("recap.cl ent. s_andro d"), Set(Cl entType).asJava)
  val  S_ANDRO D_TABLET_CL ENT =
    new B nary(na ("recap.cl ent. s_andro d_tablet"), Set(Cl entType).asJava)

  // features from userAgent
  val CL ENT_NAME = new Text(na ("recap.user_agent.cl ent_na "), Set(Cl entType).asJava)
  val CL ENT_SOURCE = new D screte(na ("recap.user_agent.cl ent_s ce"), Set(Cl entType).asJava)
  val CL ENT_VERS ON = new Text(na ("recap.user_agent.cl ent_vers on"), Set(Cl entVers on).asJava)
  val CL ENT_VERS ON_CODE =
    new Text(na ("recap.user_agent.cl ent_vers on_code"), Set(Cl entVers on).asJava)
  val DEV CE = new Text(na ("recap.user_agent.dev ce"), Set(Dev ceType).asJava)
  val FROM_DOG_FOOD = new B nary(na ("recap. ta.from_dog_food"), Set(UserAgent).asJava)
  val FROM_TW TTER_CL ENT =
    new B nary(na ("recap.user_agent.from_tw ter_cl ent"), Set(UserAgent).asJava)
  val MANUFACTURER = new Text(na ("recap.user_agent.manufacturer"), Set(UserAgent).asJava)
  val MODEL = new Text(na ("recap.user_agent.model"), Set(UserAgent).asJava)
  val NETWORK_CONNECT ON =
    new D screte(na ("recap.user_agent.network_connect on"), Set(UserAgent).asJava)
  val SDK_VERS ON = new Text(na ("recap.user_agent.sdk_vers on"), Set(App d, UserAgent).asJava)

  // engage nt
  val  S_RETWEETED = new B nary(
    na ("recap.engage nt. s_ret eted"),
    Set(Publ cRet ets, Pr vateRet ets, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_FAVOR TED = new B nary(
    na ("recap.engage nt. s_favor ed"),
    Set(Publ cL kes, Pr vateL kes, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED = new B nary(
    na ("recap.engage nt. s_repl ed"),
    Set(Publ cRepl es, Pr vateRepl es, Engage ntsPr vate, Engage ntsPubl c).asJava)
  // v1: post cl ck engage nts: fav, reply
  val  S_GOOD_CL CKED_CONVO_DESC_V1 = new B nary(
    na ("recap.engage nt. s_good_cl cked_convo_desc_favor ed_or_repl ed"),
    Set(
      Publ cL kes,
      Pr vateL kes,
      Publ cRepl es,
      Pr vateRepl es,
      Engage ntsPr vate,
      Engage ntsPubl c).asJava)
  // v2: post cl ck engage nts: cl ck
  val  S_GOOD_CL CKED_CONVO_DESC_V2 = new B nary(
    na ("recap.engage nt. s_good_cl cked_convo_desc_v2"),
    Set(T etsCl cked, Engage ntsPr vate).asJava)

  val  S_GOOD_CL CKED_CONVO_DESC_FAVOR TED = new B nary(
    na ("recap.engage nt. s_good_cl cked_convo_desc_favor ed"),
    Set(Publ cL kes, Pr vateL kes, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_REPL ED = new B nary(
    na ("recap.engage nt. s_good_cl cked_convo_desc_repl ed"),
    Set(Publ cRepl es, Pr vateRepl es, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_RETWEETED = new B nary(
    na ("recap.engage nt. s_good_cl cked_convo_desc_ret eted"),
    Set(Publ cRet ets, Pr vateRet ets, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_CL CKED = new B nary(
    na ("recap.engage nt. s_good_cl cked_convo_desc_cl cked"),
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_FOLLOWED = new B nary(
    na ("recap.engage nt. s_good_cl cked_convo_desc_follo d"),
    Set(Engage ntsPr vate).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_SHARE_DM_CL CKED = new B nary(
    na ("recap.engage nt. s_good_cl cked_convo_desc_share_dm_cl cked"),
    Set(Engage ntsPr vate).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_PROF LE_CL CKED = new B nary(
    na ("recap.engage nt. s_good_cl cked_convo_desc_prof le_cl cked"),
    Set(Engage ntsPr vate).asJava)

  val  S_GOOD_CL CKED_CONVO_DESC_UAM_GT_0 = new B nary(
    na ("recap.engage nt. s_good_cl cked_convo_desc_uam_gt_0"),
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_UAM_GT_1 = new B nary(
    na ("recap.engage nt. s_good_cl cked_convo_desc_uam_gt_1"),
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_UAM_GT_2 = new B nary(
    na ("recap.engage nt. s_good_cl cked_convo_desc_uam_gt_2"),
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_GOOD_CL CKED_CONVO_DESC_UAM_GT_3 = new B nary(
    na ("recap.engage nt. s_good_cl cked_convo_desc_uam_gt_3"),
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)

  val  S_TWEET_DETA L_DWELLED = new B nary(
    na ("recap.engage nt. s_t et_deta l_d lled"),
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_TWEET_DETA L_DWELLED_8_SEC = new B nary(
    na ("recap.engage nt. s_t et_deta l_d lled_8_sec"),
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_TWEET_DETA L_DWELLED_15_SEC = new B nary(
    na ("recap.engage nt. s_t et_deta l_d lled_15_sec"),
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_TWEET_DETA L_DWELLED_25_SEC = new B nary(
    na ("recap.engage nt. s_t et_deta l_d lled_25_sec"),
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_TWEET_DETA L_DWELLED_30_SEC = new B nary(
    na ("recap.engage nt. s_t et_deta l_d lled_30_sec"),
    Set(T etsCl cked, Engage ntsPr vate).asJava)

  val  S_PROF LE_DWELLED = new B nary(
    "recap.engage nt. s_prof le_d lled",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_DWELLED_10_SEC = new B nary(
    "recap.engage nt. s_prof le_d lled_10_sec",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_DWELLED_20_SEC = new B nary(
    "recap.engage nt. s_prof le_d lled_20_sec",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_DWELLED_30_SEC = new B nary(
    "recap.engage nt. s_prof le_d lled_30_sec",
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED = new B nary(
    "recap.engage nt. s_fullscreen_v deo_d lled",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED_5_SEC = new B nary(
    "recap.engage nt. s_fullscreen_v deo_d lled_5_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED_10_SEC = new B nary(
    "recap.engage nt. s_fullscreen_v deo_d lled_10_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED_20_SEC = new B nary(
    "recap.engage nt. s_fullscreen_v deo_d lled_20_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_FULLSCREEN_V DEO_DWELLED_30_SEC = new B nary(
    "recap.engage nt. s_fullscreen_v deo_d lled_30_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_L NK_DWELLED_15_SEC = new B nary(
    "recap.engage nt. s_l nk_d lled_15_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_L NK_DWELLED_30_SEC = new B nary(
    "recap.engage nt. s_l nk_d lled_30_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_L NK_DWELLED_60_SEC = new B nary(
    "recap.engage nt. s_l nk_d lled_60_sec",
    Set( d aEngage ntAct v  es, Engage ntTypePr vate, Engage ntsPr vate).asJava)

  val  S_QUOTED = new B nary(
    na ("recap.engage nt. s_quoted"),
    Set(Publ cRet ets, Pr vateRet ets, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_RETWEETED_W THOUT_QUOTE = new B nary(
    na ("recap.engage nt. s_ret eted_w hout_quote"),
    Set(Publ cRet ets, Pr vateRet ets, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_CL CKED =
    new B nary(na ("recap.engage nt. s_cl cked"), Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_DWELLED = new B nary(na ("recap.engage nt. s_d lled"), Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_ N_BOUNDS_V1 =
    new B nary(na ("recap.engage nt. s_d lled_ n_bounds_v1"), Set(Engage ntsPr vate).asJava)
  val DWELL_NORMAL ZED_OVERALL = new Cont nuous(
    na ("recap.engage nt.d ll_normal zed_overall"),
    Set(Engage ntsPr vate).asJava)
  val DWELL_CDF_OVERALL =
    new Cont nuous(na ("recap.engage nt.d ll_cdf_overall"), Set(Engage ntsPr vate).asJava)
  val DWELL_CDF = new Cont nuous(na ("recap.engage nt.d ll_cdf"), Set(Engage ntsPr vate).asJava)

  val  S_DWELLED_1S =
    new B nary(na ("recap.engage nt. s_d lled_1s"), Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_2S =
    new B nary(na ("recap.engage nt. s_d lled_2s"), Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_3S =
    new B nary(na ("recap.engage nt. s_d lled_3s"), Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_4S =
    new B nary(na ("recap.engage nt. s_d lled_4s"), Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_5S =
    new B nary(na ("recap.engage nt. s_d lled_5s"), Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_6S =
    new B nary(na ("recap.engage nt. s_d lled_6s"), Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_7S =
    new B nary(na ("recap.engage nt. s_d lled_7s"), Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_8S =
    new B nary(na ("recap.engage nt. s_d lled_8s"), Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_9S =
    new B nary(na ("recap.engage nt. s_d lled_9s"), Set(Engage ntsPr vate).asJava)
  val  S_DWELLED_10S =
    new B nary(na ("recap.engage nt. s_d lled_10s"), Set(Engage ntsPr vate).asJava)

  val  S_SK PPED_1S =
    new B nary(na ("recap.engage nt. s_sk pped_1s"), Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_2S =
    new B nary(na ("recap.engage nt. s_sk pped_2s"), Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_3S =
    new B nary(na ("recap.engage nt. s_sk pped_3s"), Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_4S =
    new B nary(na ("recap.engage nt. s_sk pped_4s"), Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_5S =
    new B nary(na ("recap.engage nt. s_sk pped_5s"), Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_6S =
    new B nary(na ("recap.engage nt. s_sk pped_6s"), Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_7S =
    new B nary(na ("recap.engage nt. s_sk pped_7s"), Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_8S =
    new B nary(na ("recap.engage nt. s_sk pped_8s"), Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_9S =
    new B nary(na ("recap.engage nt. s_sk pped_9s"), Set(Engage ntsPr vate).asJava)
  val  S_SK PPED_10S =
    new B nary(na ("recap.engage nt. s_sk pped_10s"), Set(Engage ntsPr vate).asJava)

  val  S_ MPRESSED =
    new B nary(na ("recap.engage nt. s_ mpressed"), Set(Engage ntsPr vate).asJava)
  val  S_FOLLOWED =
    new B nary("recap.engage nt. s_follo d", Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_PROF LE_CL CKED = new B nary(
    na ("recap.engage nt. s_prof le_cl cked"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_OPEN_L NKED = new B nary(
    na ("recap.engage nt. s_open_l nked"),
    Set(Engage ntsPr vate, L nksCl ckedOn).asJava)
  val  S_PHOTO_EXPANDED =
    new B nary(na ("recap.engage nt. s_photo_expanded"), Set(Engage ntsPr vate).asJava)
  val  S_V DEO_V EWED =
    new B nary(na ("recap.engage nt. s_v deo_v e d"), Set(Engage ntsPr vate).asJava)
  val  S_V DEO_PLAYBACK_START =
    new B nary(na ("recap.engage nt. s_v deo_playback_start"), Set(Engage ntsPr vate).asJava)
  val  S_V DEO_PLAYBACK_25 =
    new B nary(na ("recap.engage nt. s_v deo_playback_25"), Set(Engage ntsPr vate).asJava)
  val  S_V DEO_PLAYBACK_50 =
    new B nary(na ("recap.engage nt. s_v deo_playback_50"), Set(Engage ntsPr vate).asJava)
  val  S_V DEO_PLAYBACK_75 =
    new B nary(na ("recap.engage nt. s_v deo_playback_75"), Set(Engage ntsPr vate).asJava)
  val  S_V DEO_PLAYBACK_95 =
    new B nary(na ("recap.engage nt. s_v deo_playback_95"), Set(Engage ntsPr vate).asJava)
  val  S_V DEO_PLAYBACK_COMPLETE =
    new B nary(na ("recap.engage nt. s_v deo_playback_complete"), Set(Engage ntsPr vate).asJava)
  val  S_V DEO_V EWED_AND_PLAYBACK_50 = new B nary(
    na ("recap.engage nt. s_v deo_v e d_and_playback_50"),
    Set(Engage ntsPr vate).asJava)
  val  S_V DEO_QUAL TY_V EWED = new B nary(
    na ("recap.engage nt. s_v deo_qual y_v e d"),
    Set(Engage ntsPr vate).asJava
  ) 
  val  S_TWEET_SHARE_DM_CL CKED =
    new B nary(na ("recap.engage nt. s_t et_share_dm_cl cked"), Set(Engage ntsPr vate).asJava)
  val  S_TWEET_SHARE_DM_SENT =
    new B nary(na ("recap.engage nt. s_t et_share_dm_sent"), Set(Engage ntsPr vate).asJava)
  val  S_BOOKMARKED =
    new B nary(na ("recap.engage nt. s_bookmarked"), Set(Engage ntsPr vate).asJava)
  val  S_SHARED =
    new B nary(na ("recap.engage nt. s_shared"), Set(Engage ntsPr vate).asJava)
  val  S_SHARE_MENU_CL CKED =
    new B nary(na ("recap.engage nt. s_share_ nu_cl cked"), Set(Engage ntsPr vate).asJava)

  // Negat ve engage nts
  val  S_DONT_L KE =
    new B nary(na ("recap.engage nt. s_dont_l ke"), Set(Engage ntsPr vate).asJava)
  val  S_BLOCK_CL CKED = new B nary(
    na ("recap.engage nt. s_block_cl cked"),
    Set(T etsCl cked, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_BLOCK_D ALOG_BLOCKED = new B nary(
    na ("recap.engage nt. s_block_d alog_blocked"),
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_MUTE_CL CKED = new B nary(
    na ("recap.engage nt. s_mute_cl cked"),
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_MUTE_D ALOG_MUTED =
    new B nary(na ("recap.engage nt. s_mute_d alog_muted"), Set(Engage ntsPr vate).asJava)
  val  S_REPORT_TWEET_CL CKED = new B nary(
    na ("recap.engage nt. s_report_t et_cl cked"),
    Set(T etsCl cked, Engage ntsPr vate).asJava)
  val  S_NEGAT VE_FEEDBACK =
    new B nary("recap.engage nt. s_negat ve_feedback", Set(Engage ntsPr vate).asJava)
  val  S_NOT_ABOUT_TOP C =
    new B nary(na ("recap.engage nt. s_not_about_top c"), Set(Engage ntsPr vate).asJava)
  val  S_NOT_RECENT =
    new B nary(na ("recap.engage nt. s_not_recent"), Set(Engage ntsPr vate).asJava)
  val  S_NOT_RELEVANT =
    new B nary(na ("recap.engage nt. s_not_relevant"), Set(Engage ntsPr vate).asJava)
  val  S_SEE_FEWER =
    new B nary(na ("recap.engage nt. s_see_fe r"), Set(Engage ntsPr vate).asJava)
  val  S_TOP C_SPEC_NEG_ENGAGEMENT =
    new B nary("recap.engage nt. s_top c_spec_neg_engage nt", Set(Engage ntsPr vate).asJava)
  val  S_UNFOLLOW_TOP C =
    new B nary("recap.engage nt. s_unfollow_top c", Set(Engage ntsPr vate).asJava)
  val  S_UNFOLLOW_TOP C_EXPL C T_POS T VE_LABEL =
    new B nary(
      "recap.engage nt. s_unfollow_top c_expl c _pos  ve_label",
      Set(Engage ntsPr vate).asJava)
  val  S_UNFOLLOW_TOP C_ MPL C T_POS T VE_LABEL =
    new B nary(
      "recap.engage nt. s_unfollow_top c_ mpl c _pos  ve_label",
      Set(Engage ntsPr vate).asJava)
  val  S_UNFOLLOW_TOP C_STRONG_EXPL C T_NEGAT VE_LABEL =
    new B nary(
      "recap.engage nt. s_unfollow_top c_strong_expl c _negat ve_label",
      Set(Engage ntsPr vate).asJava)
  val  S_UNFOLLOW_TOP C_EXPL C T_NEGAT VE_LABEL =
    new B nary(
      "recap.engage nt. s_unfollow_top c_expl c _negat ve_label",
      Set(Engage ntsPr vate).asJava)
  val  S_NOT_ NTERESTED_ N =
    new B nary("recap.engage nt. s_not_ nterested_ n", Set(Engage ntsPr vate).asJava)
  val  S_NOT_ NTERESTED_ N_EXPL C T_POS T VE_LABEL =
    new B nary(
      "recap.engage nt. s_not_ nterested_ n_expl c _pos  ve_label",
      Set(Engage ntsPr vate).asJava)
  val  S_NOT_ NTERESTED_ N_EXPL C T_NEGAT VE_LABEL =
    new B nary(
      "recap.engage nt. s_not_ nterested_ n_expl c _negat ve_label",
      Set(Engage ntsPr vate).asJava)
  val  S_CARET_CL CKED =
    new B nary(na ("recap.engage nt. s_caret_cl cked"), Set(Engage ntsPr vate).asJava)
  val  S_FOLLOW_TOP C =
    new B nary("recap.engage nt. s_follow_top c", Set(Engage ntsPr vate).asJava)
  val  S_NOT_ NTERESTED_ N_TOP C =
    new B nary("recap.engage nt. s_not_ nterested_ n_top c", Set(Engage ntsPr vate).asJava)
  val  S_HOME_LATEST_V S TED =
    new B nary(na ("recap.engage nt. s_ho _latest_v s ed"), Set(Engage ntsPr vate).asJava)

  // Relevance prompt t et engage nts
  val  S_RELEVANCE_PROMPT_YES_CL CKED = new B nary(
    na ("recap.engage nt. s_relevance_prompt_yes_cl cked"),
    Set(Engage ntsPr vate).asJava)
  val  S_RELEVANCE_PROMPT_NO_CL CKED = new B nary(
    na ("recap.engage nt. s_relevance_prompt_no_cl cked"),
    Set(Engage ntsPr vate).asJava)
  val  S_RELEVANCE_PROMPT_ MPRESSED = new B nary(
    na ("recap.engage nt. s_relevance_prompt_ mpressed"),
    Set(Engage ntsPr vate).asJava)

  // Rec procal engage nts for reply forward engage nt
  val  S_REPL ED_REPLY_ MPRESSED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_repl ed_reply_ mpressed_by_author"),
    Set(Engage ntsPr vate).asJava)
  val  S_REPL ED_REPLY_FAVOR TED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_repl ed_reply_favor ed_by_author"),
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateL kes, Publ cL kes).asJava)
  val  S_REPL ED_REPLY_QUOTED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_repl ed_reply_quoted_by_author"),
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateRet ets, Publ cRet ets).asJava)
  val  S_REPL ED_REPLY_REPL ED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_repl ed_reply_repl ed_by_author"),
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateRepl es, Publ cRepl es).asJava)
  val  S_REPL ED_REPLY_RETWEETED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_repl ed_reply_ret eted_by_author"),
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateRet ets, Publ cRet ets).asJava)
  val  S_REPL ED_REPLY_BLOCKED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_repl ed_reply_blocked_by_author"),
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED_REPLY_FOLLOWED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_repl ed_reply_follo d_by_author"),
    Set(Engage ntsPr vate, Engage ntsPubl c, Follow).asJava)
  val  S_REPL ED_REPLY_UNFOLLOWED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_repl ed_reply_unfollo d_by_author"),
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_REPL ED_REPLY_MUTED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_repl ed_reply_muted_by_author"),
    Set(Engage ntsPr vate).asJava)
  val  S_REPL ED_REPLY_REPORTED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_repl ed_reply_reported_by_author"),
    Set(Engage ntsPr vate).asJava)

  // T  der ved label  s t  log cal OR of REPLY_REPL ED, REPLY_FAVOR TED, REPLY_RETWEETED
  val  S_REPL ED_REPLY_ENGAGED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_repl ed_reply_engaged_by_author"),
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)

  // Rec procal engage nts for fav forward engage nt
  val  S_FAVOR TED_FAV_FAVOR TED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_favor ed_fav_favor ed_by_author"),
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateL kes, Publ cL kes).asJava
  )
  val  S_FAVOR TED_FAV_REPL ED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_favor ed_fav_repl ed_by_author"),
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateRepl es, Publ cRepl es).asJava
  )
  val  S_FAVOR TED_FAV_RETWEETED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_favor ed_fav_ret eted_by_author"),
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateRet ets, Publ cRet ets).asJava
  )
  val  S_FAVOR TED_FAV_FOLLOWED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_favor ed_fav_follo d_by_author"),
    Set(Engage ntsPr vate, Engage ntsPubl c, Pr vateRet ets, Publ cRet ets).asJava
  )
  // T  der ved label  s t  log cal OR of FAV_REPL ED, FAV_FAVOR TED, FAV_RETWEETED, FAV_FOLLOWED
  val  S_FAVOR TED_FAV_ENGAGED_BY_AUTHOR = new B nary(
    na ("recap.engage nt. s_favor ed_fav_engaged_by_author"),
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava)

  // def ne good prof le cl ck by cons der ng follow ng engage nts (follow, fav, reply, ret et, etc.) at prof le page
  val  S_PROF LE_CL CKED_AND_PROF LE_FOLLOW = new B nary(
    na ("recap.engage nt. s_prof le_cl cked_and_prof le_follow"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, Follow).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_FAV = new B nary(
    na ("recap.engage nt. s_prof le_cl cked_and_prof le_fav"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, Pr vateL kes, Publ cL kes).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_REPLY = new B nary(
    na ("recap.engage nt. s_prof le_cl cked_and_prof le_reply"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, Pr vateRepl es, Publ cRepl es).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_RETWEET = new B nary(
    na ("recap.engage nt. s_prof le_cl cked_and_prof le_ret et"),
    Set(
      Prof lesV e d,
      Prof lesCl cked,
      Engage ntsPr vate,
      Pr vateRet ets,
      Publ cRet ets).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_TWEET_CL CK = new B nary(
    na ("recap.engage nt. s_prof le_cl cked_and_prof le_t et_cl ck"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, T etsCl cked).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_SHARE_DM_CL CK = new B nary(
    na ("recap.engage nt. s_prof le_cl cked_and_prof le_share_dm_cl ck"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  // T  der ved label  s t  un on of all b nary features above
  val  S_PROF LE_CL CKED_AND_PROF LE_ENGAGED = new B nary(
    na ("recap.engage nt. s_prof le_cl cked_and_prof le_engaged"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate, Engage ntsPubl c).asJava)

  // def ne bad prof le cl ck by cons der ng follow ng engage nts (user report, t et report, mute, block, etc) at prof le page
  val  S_PROF LE_CL CKED_AND_PROF LE_USER_REPORT_CL CK = new B nary(
    na ("recap.engage nt. s_prof le_cl cked_and_prof le_user_report_cl ck"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_TWEET_REPORT_CL CK = new B nary(
    na ("recap.engage nt. s_prof le_cl cked_and_prof le_t et_report_cl ck"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_MUTE = new B nary(
    na ("recap.engage nt. s_prof le_cl cked_and_prof le_mute"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_PROF LE_CL CKED_AND_PROF LE_BLOCK = new B nary(
    na ("recap.engage nt. s_prof le_cl cked_and_prof le_block"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  // T  der ved label  s t  un on of bad prof le cl ck engage nts and ex st ng negat ve feedback
  val  S_NEGAT VE_FEEDBACK_V2 = new B nary(
    na ("recap.engage nt. s_negat ve_feedback_v2"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_STRONG_NEGAT VE_FEEDBACK = new B nary(
    na ("recap.engage nt. s_strong_negat ve_feedback"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  val  S_WEAK_NEGAT VE_FEEDBACK = new B nary(
    na ("recap.engage nt. s_ ak_negat ve_feedback"),
    Set(Prof lesV e d, Prof lesCl cked, Engage ntsPr vate).asJava)
  // engage nt for follow ng user from any surface area
  val  S_FOLLOWED_FROM_ANY_SURFACE_AREA = new B nary(
    "recap.engage nt. s_follo d_from_any_surface_area",
    Set(Engage ntsPubl c, Engage ntsPr vate).asJava)

  // Reply downvote engage nts
  val  S_REPLY_DOWNVOTED =
    new B nary(na ("recap.engage nt. s_reply_downvoted"), Set(Engage ntsPr vate).asJava)
  val  S_REPLY_DOWNVOTE_REMOVED =
    new B nary(na ("recap.engage nt. s_reply_downvote_removed"), Set(Engage ntsPr vate).asJava)

  // Ot r engage nts
  val  S_GOOD_OPEN_L NK = new B nary(
    na ("recap.engage nt. s_good_open_l nk"),
    Set(Engage ntsPr vate, L nksCl ckedOn).asJava)
  val  S_ENGAGED = new B nary(
    na ("recap.engage nt.any"),
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava
  ) // Deprecated - to be removed shortly
  val  S_EARLYB RD_UN F ED_ENGAGEMENT = new B nary(
    na ("recap.engage nt. s_un f ed_engage nt"),
    Set(Engage ntsPr vate, Engage ntsPubl c).asJava
  ) // A subset of  S_ENGAGED spec f cally  ntended for use  n earlyb rd models

  // features from Thr ftT etFeatures
  val PREV_USER_TWEET_ENGAGEMENT = new Cont nuous(
    na ("recap.t etfeature.prev_user_t et_enagage nt"),
    Set(Engage ntScore, Engage ntsPr vate, Engage ntsPubl c).asJava)
  val  S_SENS T VE = new B nary(na ("recap.t etfeature. s_sens  ve"))
  val HAS_MULT PLE_MED A = new B nary(
    na ("recap.t etfeature.has_mult ple_ d a"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val  S_AUTHOR_PROF LE_EGG = new B nary(na ("recap.t etfeature. s_author_prof le_egg"))
  val  S_AUTHOR_NEW =
    new B nary(na ("recap.t etfeature. s_author_new"), Set(UserState, UserType).asJava)
  val NUM_MENT ONS = new Cont nuous(
    na ("recap.t etfeature.num_ nt ons"),
    Set(CountOfPr vateT etEnt  esAnd tadata, CountOfPubl cT etEnt  esAnd tadata).asJava)
  val HAS_MENT ON = new B nary(na ("recap.t etfeature.has_ nt on"), Set(UserV s bleFlag).asJava)
  val NUM_HASHTAGS = new Cont nuous(
    na ("recap.t etfeature.num_hashtags"),
    Set(CountOfPr vateT etEnt  esAnd tadata, CountOfPubl cT etEnt  esAnd tadata).asJava)
  val HAS_HASHTAG = new B nary(
    na ("recap.t etfeature.has_hashtag"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val L NK_LANGUAGE = new Cont nuous(
    na ("recap.t etfeature.l nk_language"),
    Set(Prov dedLanguage,  nferredLanguage).asJava)
  val  S_AUTHOR_NSFW =
    new B nary(na ("recap.t etfeature. s_author_nsfw"), Set(UserSafetyLabels, UserType).asJava)
  val  S_AUTHOR_SPAM =
    new B nary(na ("recap.t etfeature. s_author_spam"), Set(UserSafetyLabels, UserType).asJava)
  val  S_AUTHOR_BOT =
    new B nary(na ("recap.t etfeature. s_author_bot"), Set(UserSafetyLabels, UserType).asJava)
  val S GNATURE =
    new D screte(na ("recap.t etfeature.s gnature"), Set(D g alS gnatureNonrepud at on).asJava)
  val LANGUAGE = new D screte(
    na ("recap.t etfeature.language"),
    Set(Prov dedLanguage,  nferredLanguage).asJava)
  val FROM_ NACT VE_USER =
    new B nary(na ("recap.t etfeature.from_ nact ve_user"), Set(UserAct veFlag).asJava)
  val PROBABLY_FROM_FOLLOWED_AUTHOR = new B nary(na ("recap.v3.t etfeature.probably_from_follow"))
  val FROM_MUTUAL_FOLLOW = new B nary(na ("recap.t etfeature.from_mutual_follow"))
  val USER_REP = new Cont nuous(na ("recap.t etfeature.user_rep"))
  val FROM_VER F ED_ACCOUNT =
    new B nary(na ("recap.t etfeature.from_ver f ed_account"), Set(UserVer f edFlag).asJava)
  val  S_BUS NESS_SCORE = new Cont nuous(na ("recap.t etfeature. s_bus ness_score"))
  val HAS_CONSUMER_V DEO = new B nary(
    na ("recap.t etfeature.has_consu r_v deo"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_PRO_V DEO = new B nary(
    na ("recap.t etfeature.has_pro_v deo"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_V NE = new B nary(
    na ("recap.t etfeature.has_v ne"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_PER SCOPE = new B nary(
    na ("recap.t etfeature.has_per scope"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_NAT VE_V DEO = new B nary(
    na ("recap.t etfeature.has_nat ve_v deo"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_NAT VE_ MAGE = new B nary(
    na ("recap.t etfeature.has_nat ve_ mage"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_CARD = new B nary(
    na ("recap.t etfeature.has_card"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_ MAGE = new B nary(
    na ("recap.t etfeature.has_ mage"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_NEWS = new B nary(
    na ("recap.t etfeature.has_news"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_V DEO = new B nary(
    na ("recap.t etfeature.has_v deo"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_V S BLE_L NK = new B nary(
    na ("recap.t etfeature.has_v s ble_l nk"),
    Set(UrlFoundFlag, Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val L NK_COUNT = new Cont nuous(
    na ("recap.t etfeature.l nk_count"),
    Set(CountOfPr vateT etEnt  esAnd tadata, CountOfPubl cT etEnt  esAnd tadata).asJava)
  val HAS_L NK = new B nary(
    na ("recap.t etfeature.has_l nk"),
    Set(UrlFoundFlag, Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val  S_OFFENS VE = new B nary(na ("recap.t etfeature. s_offens ve"))
  val HAS_TREND = new B nary(
    na ("recap.t etfeature.has_trend"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val HAS_MULT PLE_HASHTAGS_OR_TRENDS = new B nary(
    na ("recap.t etfeature.has_mult ple_hashtag_or_trend"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val URL_DOMA NS = new SparseB nary(
    na ("recap.t etfeature.url_doma ns"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val CONTA NS_MED A = new B nary(
    na ("recap.t etfeature.conta ns_ d a"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val RETWEET_SEARCHER = new B nary(na ("recap.t etfeature.ret et_searc r"))
  val REPLY_SEARCHER = new B nary(na ("recap.t etfeature.reply_searc r"))
  val MENT ON_SEARCHER =
    new B nary(na ("recap.t etfeature. nt on_searc r"), Set(UserV s bleFlag).asJava)
  val REPLY_OTHER =
    new B nary(na ("recap.t etfeature.reply_ot r"), Set(Publ cRepl es, Pr vateRepl es).asJava)
  val RETWEET_OTHER = new B nary(
    na ("recap.t etfeature.ret et_ot r"),
    Set(Publ cRet ets, Pr vateRet ets).asJava)
  val  S_REPLY =
    new B nary(na ("recap.t etfeature. s_reply"), Set(Publ cRepl es, Pr vateRepl es).asJava)
  val  S_RETWEET =
    new B nary(na ("recap.t etfeature. s_ret et"), Set(Publ cRet ets, Pr vateRet ets).asJava)
  val  S_EXTENDED_REPLY = new B nary(
    na ("recap.t etfeature. s_extended_reply"),
    Set(Publ cRepl es, Pr vateRepl es).asJava)
  val MATCH_U _LANG = new B nary(
    na ("recap.t etfeature.match_u _lang"),
    Set(Prov dedLanguage,  nferredLanguage).asJava)
  val MATCH_SEARCHER_MA N_LANG = new B nary(
    na ("recap.t etfeature.match_searc r_ma n_lang"),
    Set(Prov dedLanguage,  nferredLanguage).asJava)
  val MATCH_SEARCHER_LANGS = new B nary(
    na ("recap.t etfeature.match_searc r_langs"),
    Set(Prov dedLanguage,  nferredLanguage).asJava)
  val B D RECT ONAL_REPLY_COUNT = new Cont nuous(
    na ("recap.t etfeature.b d rect onal_reply_count"),
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava)
  val UN D RECT ONAL_REPLY_COUNT = new Cont nuous(
    na ("recap.t etfeature.un d rect onal_reply_count"),
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava)
  val B D RECT ONAL_RETWEET_COUNT = new Cont nuous(
    na ("recap.t etfeature.b d rect onal_ret et_count"),
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava)
  val UN D RECT ONAL_RETWEET_COUNT = new Cont nuous(
    na ("recap.t etfeature.un d rect onal_ret et_count"),
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava)
  val B D RECT ONAL_FAV_COUNT = new Cont nuous(
    na ("recap.t etfeature.b d rect onal_fav_count"),
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava)
  val UN D RECT ONAL_FAV_COUNT = new Cont nuous(
    na ("recap.t etfeature.un d rect ona_fav_count"),
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava)
  val CONVERSAT ONAL_COUNT = new Cont nuous(
    na ("recap.t etfeature.conversat onal_count"),
    Set(CountOfPr vateT ets, CountOfPubl cT ets).asJava)
  // t et  mpress ons on an embedded t et
  val EMBEDS_ MPRESS ON_COUNT = new Cont nuous(
    na ("recap.t etfeature.embeds_ mpress on_count"),
    Set(CountOf mpress on).asJava)
  // number of URLs that embed t  t et
  val EMBEDS_URL_COUNT = new Cont nuous(
    na ("recap.t etfeature.embeds_url_count"),
    Set(CountOfPr vateT etEnt  esAnd tadata, CountOfPubl cT etEnt  esAnd tadata).asJava)
  // currently only counts v ews on Snappy and Ampl fy pro v deos. Counts for ot r v deos forthcom ng
  val V DEO_V EW_COUNT = new Cont nuous(
    na ("recap.t etfeature.v deo_v ew_count"),
    Set(
      CountOfT etEnt  esCl cked,
      CountOfPr vateT etEnt  esAnd tadata,
      CountOfPubl cT etEnt  esAnd tadata,
      Engage ntsPr vate,
      Engage ntsPubl c).asJava
  )
  val TWEET_COUNT_FROM_USER_ N_SNAPSHOT = new Cont nuous(
    na ("recap.t etfeature.t et_count_from_user_ n_snapshot"),
    Set(CountOfPr vateT ets, CountOfPubl cT ets).asJava)
  val NORMAL ZED_PARUS_SCORE =
    new Cont nuous("recap.t etfeature.normal zed_parus_score", Set(Engage ntScore).asJava)
  val PARUS_SCORE = new Cont nuous("recap.t etfeature.parus_score", Set(Engage ntScore).asJava)
  val REAL_GRAPH_WE GHT =
    new Cont nuous("recap.t etfeature.real_graph_  ght", Set(UsersRealGraphScore).asJava)
  val SARUS_GRAPH_WE GHT = new Cont nuous("recap.t etfeature.sarus_graph_  ght")
  val TOP C_S M_SEARCHER_ NTERSTED_ N_AUTHOR_KNOWN_FOR = new Cont nuous(
    "recap.t etfeature.top c_s m_searc r_ nterested_ n_author_known_for")
  val TOP C_S M_SEARCHER_AUTHOR_BOTH_ NTERESTED_ N = new Cont nuous(
    "recap.t etfeature.top c_s m_searc r_author_both_ nterested_ n")
  val TOP C_S M_SEARCHER_AUTHOR_BOTH_KNOWN_FOR = new Cont nuous(
    "recap.t etfeature.top c_s m_searc r_author_both_known_for")
  val TOP C_S M_SEARCHER_ NTERESTED_ N_TWEET = new Cont nuous(
    "recap.t etfeature.top c_s m_searc r_ nterested_ n_t et")
  val  S_RETWEETER_PROF LE_EGG =
    new B nary(na ("recap.v2.t etfeature. s_ret eter_prof le_egg"), Set(UserType).asJava)
  val  S_RETWEETER_NEW =
    new B nary(na ("recap.v2.t etfeature. s_ret eter_new"), Set(UserType, UserState).asJava)
  val  S_RETWEETER_BOT =
    new B nary(
      na ("recap.v2.t etfeature. s_ret eter_bot"),
      Set(UserType, UserSafetyLabels).asJava)
  val  S_RETWEETER_NSFW =
    new B nary(
      na ("recap.v2.t etfeature. s_ret eter_nsfw"),
      Set(UserType, UserSafetyLabels).asJava)
  val  S_RETWEETER_SPAM =
    new B nary(
      na ("recap.v2.t etfeature. s_ret eter_spam"),
      Set(UserType, UserSafetyLabels).asJava)
  val RETWEET_OF_MUTUAL_FOLLOW = new B nary(
    na ("recap.v2.t etfeature.ret et_of_mutual_follow"),
    Set(Publ cRet ets, Pr vateRet ets).asJava)
  val SOURCE_AUTHOR_REP = new Cont nuous(na ("recap.v2.t etfeature.s ce_author_rep"))
  val  S_RETWEET_OF_REPLY = new B nary(
    na ("recap.v2.t etfeature. s_ret et_of_reply"),
    Set(Publ cRet ets, Pr vateRet ets).asJava)
  val RETWEET_D RECTED_AT_USER_ N_F RST_DEGREE = new B nary(
    na ("recap.v2.t etfeature. s_ret et_d rected_at_user_ n_f rst_degree"),
    Set(Publ cRet ets, Pr vateRet ets, Follow).asJava)
  val MENT ONED_SCREEN_NAMES = new SparseB nary(
    "ent  es.users. nt oned_screen_na s",
    Set(D splayNa , UserV s bleFlag).asJava)
  val MENT ONED_SCREEN_NAME = new Text(
    "ent  es.users. nt oned_screen_na s. mber",
    Set(D splayNa , UserV s bleFlag).asJava)
  val HASHTAGS = new SparseB nary(
    "ent  es.hashtags",
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val URL_SLUGS = new SparseB nary(na ("recap.l nkfeature.url_slugs"), Set(UrlFoundFlag).asJava)

  // features from Thr ftSearchResult tadata
  val REPLY_COUNT = new Cont nuous(
    na ("recap.searchfeature.reply_count"),
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava)
  val RETWEET_COUNT = new Cont nuous(
    na ("recap.searchfeature.ret et_count"),
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava)
  val FAV_COUNT = new Cont nuous(
    na ("recap.searchfeature.fav_count"),
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava)
  val BLENDER_SCORE = new Cont nuous(na ("recap.searchfeature.blender_score"))
  val TEXT_SCORE = new Cont nuous(na ("recap.searchfeature.text_score"))

  // features related to content s ce
  val SOURCE_TYPE = new D screte(na ("recap.s ce.type"))

  // features from addressbook
  // t  author  s  n t  user's ema l addressbook
  val USER_TO_AUTHOR_EMA L_REACHABLE =
    new B nary(na ("recap.addressbook.user_to_author_ema l_reachable"), Set(AddressBook).asJava)
  // t  author  s  n t  user's phone addressbook
  val USER_TO_AUTHOR_PHONE_REACHABLE =
    new B nary(na ("recap.addressbook.user_to_author_phone_reachable"), Set(AddressBook).asJava)
  // t  user  s  n t  author's ema l addressbook
  val AUTHOR_TO_USER_EMA L_REACHABLE =
    new B nary(na ("recap.addressbook.author_to_user_ema l_reachable"), Set(AddressBook).asJava)
  // t  user  s  n t  user's phone addressbook
  val AUTHOR_TO_USER_PHONE_REACHABLE =
    new B nary(na ("recap.addressbook.author_to_user_phone_reachable"), Set(AddressBook).asJava)

  // pred cted engage nt (t se features are used by pred ct on serv ce to return t  pred cted engage nt probab l y)
  // t se should match t  na s  n engage nt_to_score_feature_mapp ng
  val PRED CTED_ S_FAVOR TED =
    new Cont nuous(na ("recap.engage nt_pred cted. s_favor ed"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_RETWEETED =
    new Cont nuous(na ("recap.engage nt_pred cted. s_ret eted"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_QUOTED =
    new Cont nuous(na ("recap.engage nt_pred cted. s_quoted"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_REPL ED =
    new Cont nuous(na ("recap.engage nt_pred cted. s_repl ed"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_GOOD_OPEN_L NK = new Cont nuous(
    na ("recap.engage nt_pred cted. s_good_open_l nk"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_PROF LE_CL CKED = new Cont nuous(
    na ("recap.engage nt_pred cted. s_prof le_cl cked"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_PROF LE_CL CKED_AND_PROF LE_ENGAGED = new Cont nuous(
    na ("recap.engage nt_pred cted. s_prof le_cl cked_and_prof le_engaged"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_CL CKED =
    new Cont nuous(na ("recap.engage nt_pred cted. s_cl cked"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_PHOTO_EXPANDED = new Cont nuous(
    na ("recap.engage nt_pred cted. s_photo_expanded"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_DONT_L KE =
    new Cont nuous(na ("recap.engage nt_pred cted. s_dont_l ke"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_V DEO_PLAYBACK_50 = new Cont nuous(
    na ("recap.engage nt_pred cted. s_v deo_playback_50"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_V DEO_QUAL TY_V EWED = new Cont nuous(
    na ("recap.engage nt_pred cted. s_v deo_qual y_v e d"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_BOOKMARKED =
    new Cont nuous(na ("recap.engage nt_pred cted. s_bookmarked"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_SHARED =
    new Cont nuous(na ("recap.engage nt_pred cted. s_shared"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_SHARE_MENU_CL CKED =
    new Cont nuous(
      na ("recap.engage nt_pred cted. s_share_ nu_cl cked"),
      Set(Engage ntScore).asJava)
  val PRED CTED_ S_PROF LE_DWELLED_20_SEC = new Cont nuous(
    na ("recap.engage nt_pred cted. s_prof le_d lled_20_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_FULLSCREEN_V DEO_DWELLED_5_SEC = new Cont nuous(
    na ("recap.engage nt_pred cted. s_fullscreen_v deo_d lled_5_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_FULLSCREEN_V DEO_DWELLED_10_SEC = new Cont nuous(
    na ("recap.engage nt_pred cted. s_fullscreen_v deo_d lled_10_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_FULLSCREEN_V DEO_DWELLED_20_SEC = new Cont nuous(
    na ("recap.engage nt_pred cted. s_fullscreen_v deo_d lled_20_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_FULLSCREEN_V DEO_DWELLED_30_SEC = new Cont nuous(
    na ("recap.engage nt_pred cted. s_fullscreen_v deo_d lled_30_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_UN F ED_ENGAGEMENT = new Cont nuous(
    na ("recap.engage nt_pred cted. s_un f ed_engage nt"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_COMPOSE_TR GGERED = new Cont nuous(
    na ("recap.engage nt_pred cted. s_compose_tr ggered"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_REPL ED_REPLY_ MPRESSED_BY_AUTHOR = new Cont nuous(
    na ("recap.engage nt_pred cted. s_repl ed_reply_ mpressed_by_author"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_REPL ED_REPLY_ENGAGED_BY_AUTHOR = new Cont nuous(
    na ("recap.engage nt_pred cted. s_repl ed_reply_engaged_by_author"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_GOOD_CL CKED_V1 = new Cont nuous(
    na ("recap.engage nt_pred cted. s_good_cl cked_convo_desc_favor ed_or_repl ed"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_GOOD_CL CKED_V2 = new Cont nuous(
    na ("recap.engage nt_pred cted. s_good_cl cked_convo_desc_v2"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_TWEET_DETA L_DWELLED_8_SEC = new Cont nuous(
    na ("recap.engage nt_pred cted. s_t et_deta l_d lled_8_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_TWEET_DETA L_DWELLED_15_SEC = new Cont nuous(
    na ("recap.engage nt_pred cted. s_t et_deta l_d lled_15_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_TWEET_DETA L_DWELLED_25_SEC = new Cont nuous(
    na ("recap.engage nt_pred cted. s_t et_deta l_d lled_25_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_TWEET_DETA L_DWELLED_30_SEC = new Cont nuous(
    na ("recap.engage nt_pred cted. s_t et_deta l_d lled_30_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_FAVOR TED_FAV_ENGAGED_BY_AUTHOR = new Cont nuous(
    na ("recap.engage nt_pred cted. s_favor ed_fav_engaged_by_author"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_GOOD_CL CKED_W TH_DWELL_SUM_GTE_60S = new Cont nuous(
    na (
      "recap.engage nt_pred cted. s_good_cl cked_convo_desc_favor ed_or_repl ed_or_d ll_sum_gte_60_secs"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_DWELLED_ N_BOUNDS_V1 = new Cont nuous(
    na ("recap.engage nt_pred cted. s_d lled_ n_bounds_v1"),
    Set(Engage ntScore).asJava)
  val PRED CTED_DWELL_NORMAL ZED_OVERALL = new Cont nuous(
    na ("recap.engage nt_pred cted.d ll_normal zed_overall"),
    Set(Engage ntScore).asJava)
  val PRED CTED_DWELL_CDF =
    new Cont nuous(na ("recap.engage nt_pred cted.d ll_cdf"), Set(Engage ntScore).asJava)
  val PRED CTED_DWELL_CDF_OVERALL = new Cont nuous(
    na ("recap.engage nt_pred cted.d ll_cdf_overall"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_DWELLED =
    new Cont nuous(na ("recap.engage nt_pred cted. s_d lled"), Set(Engage ntScore).asJava)

  val PRED CTED_ S_DWELLED_1S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_d lled_1s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_DWELLED_2S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_d lled_2s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_DWELLED_3S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_d lled_3s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_DWELLED_4S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_d lled_4s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_DWELLED_5S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_d lled_5s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_DWELLED_6S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_d lled_6s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_DWELLED_7S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_d lled_7s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_DWELLED_8S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_d lled_8s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_DWELLED_9S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_d lled_9s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_DWELLED_10S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_d lled_10s"), Set(Engage ntScore).asJava)

  val PRED CTED_ S_SK PPED_1S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_sk pped_1s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_SK PPED_2S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_sk pped_2s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_SK PPED_3S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_sk pped_3s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_SK PPED_4S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_sk pped_4s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_SK PPED_5S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_sk pped_5s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_SK PPED_6S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_sk pped_6s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_SK PPED_7S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_sk pped_7s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_SK PPED_8S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_sk pped_8s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_SK PPED_9S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_sk pped_9s"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_SK PPED_10S =
    new Cont nuous(na ("recap.engage nt_pred cted. s_sk pped_10s"), Set(Engage ntScore).asJava)

  val PRED CTED_ S_HOME_LATEST_V S TED = new Cont nuous(
    na ("recap.engage nt_pred cted. s_ho _latest_v s ed"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_NEGAT VE_FEEDBACK =
    new Cont nuous(
      na ("recap.engage nt_pred cted. s_negat ve_feedback"),
      Set(Engage ntScore).asJava)
  val PRED CTED_ S_NEGAT VE_FEEDBACK_V2 =
    new Cont nuous(
      na ("recap.engage nt_pred cted. s_negat ve_feedback_v2"),
      Set(Engage ntScore).asJava)
  val PRED CTED_ S_WEAK_NEGAT VE_FEEDBACK =
    new Cont nuous(
      na ("recap.engage nt_pred cted. s_ ak_negat ve_feedback"),
      Set(Engage ntScore).asJava)
  val PRED CTED_ S_STRONG_NEGAT VE_FEEDBACK =
    new Cont nuous(
      na ("recap.engage nt_pred cted. s_strong_negat ve_feedback"),
      Set(Engage ntScore).asJava)
  val PRED CTED_ S_REPORT_TWEET_CL CKED =
    new Cont nuous(
      na ("recap.engage nt_pred cted. s_report_t et_cl cked"),
      Set(Engage ntScore).asJava)
  val PRED CTED_ S_UNFOLLOW_TOP C =
    new Cont nuous(
      na ("recap.engage nt_pred cted. s_unfollow_top c"),
      Set(Engage ntScore).asJava)
  val PRED CTED_ S_RELEVANCE_PROMPT_YES_CL CKED = new Cont nuous(
    na ("recap.engage nt_pred cted. s_relevance_prompt_yes_cl cked"),
    Set(Engage ntScore).asJava)

  // engage nt for follow ng user from any surface area
  val PRED CTED_ S_FOLLOWED_FROM_ANY_SURFACE_AREA = new Cont nuous(
    "recap.engage nt_pred cted. s_follo d_from_any_surface_area",
    Set(Engage ntScore).asJava)

  
  // T se are global engage nt counts for t  T ets.
  val FAV_COUNT_V2 = new Cont nuous(
    na ("recap.earlyb rd.fav_count_v2"),
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava)
  val RETWEET_COUNT_V2 = new Cont nuous(
    na ("recap.earlyb rd.ret et_count_v2"),
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava)
  val REPLY_COUNT_V2 = new Cont nuous(
    na ("recap.earlyb rd.reply_count_v2"),
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava)

  val HAS_US_POL T CAL_ANNOTAT ON = new B nary(
    na ("recap.has_us_pol  cal_annotat on"),
    Set(Semant ccoreClass f cat on).asJava
  )

  val HAS_US_POL T CAL_ALL_GROUPS_ANNOTAT ON = new B nary(
    na ("recap.has_us_pol  cal_all_groups_annotat on"),
    Set(Semant ccoreClass f cat on).asJava
  )

  val HAS_US_POL T CAL_ANNOTAT ON_H GH_RECALL = new B nary(
    na ("recap.has_us_pol  cal_annotat on_h gh_recall"),
    Set(Semant ccoreClass f cat on).asJava
  )

  val HAS_US_POL T CAL_ANNOTAT ON_H GH_RECALL_V2 = new B nary(
    na ("recap.has_us_pol  cal_annotat on_h gh_recall_v2"),
    Set(Semant ccoreClass f cat on).asJava
  )

  val HAS_US_POL T CAL_ANNOTAT ON_H GH_PREC S ON_V0 = new B nary(
    na ("recap.has_us_pol  cal_annotat on_h gh_prec s on_v0"),
    Set(Semant ccoreClass f cat on).asJava
  )

  val HAS_US_POL T CAL_ANNOTAT ON_BALANCED_PREC S ON_RECALL_V0 = new B nary(
    na ("recap.has_us_pol  cal_annotat on_balanced_prec s on_recall_v0"),
    Set(Semant ccoreClass f cat on).asJava
  )

  val HAS_US_POL T CAL_ANNOTAT ON_H GH_RECALL_V3 = new B nary(
    na ("recap.has_us_pol  cal_annotat on_h gh_recall_v3"),
    Set(Semant ccoreClass f cat on).asJava
  )

  val HAS_US_POL T CAL_ANNOTAT ON_H GH_PREC S ON_V3 = new B nary(
    na ("recap.has_us_pol  cal_annotat on_h gh_prec s on_v3"),
    Set(Semant ccoreClass f cat on).asJava
  )

  val HAS_US_POL T CAL_ANNOTAT ON_BALANCED_V3 = new B nary(
    na ("recap.has_us_pol  cal_annotat on_balanced_v3"),
    Set(Semant ccoreClass f cat on).asJava
  )

}
