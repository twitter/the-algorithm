package com.tw ter.t  l nes.pred ct on.features.common

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport com.tw ter.ml.ap .Feature.B nary
 mport com.tw ter.ml.ap .Feature.Cont nuous
 mport com.tw ter.ml.ap .Feature.D screte
 mport com.tw ter.ml.ap .Feature.SparseB nary
 mport com.tw ter.ml.ap .Feature.SparseCont nuous
 mport com.tw ter.ml.ap .Feature.Text
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.TypedAggregateGroup
 mport scala.collect on.JavaConverters._

object T  l nesSharedFeatures extends T  l nesSharedFeatures("")
object  nReplyToT etT  l nesSharedFeatures extends T  l nesSharedFeatures(" n_reply_to_t et")

/**
 * Def nes shared features
 */
class T  l nesSharedFeatures(pref x: Str ng) {
  pr vate def na (featureNa : Str ng): Str ng = {
     f (pref x.nonEmpty) {
      s"$pref x.$featureNa "
    } else {
      featureNa 
    }
  }

  //  ta
  val EXPER MENT_META = new SparseB nary(
    na ("t  l nes. ta.exper  nt_ ta"),
    Set(Exper  nt d, Exper  ntNa ).asJava)

  //  tor cally used  n t  "comb ned models" to d st ngu sh  n-network and out of network t ets.
  // now t  feature denotes wh ch adapter (recap or rect et) was used to generate t  datarecords.
  // and  s used by t  data collect on p pel ne to spl  t  tra n ng data.
  val  NJECT ON_TYPE = new D screte(na ("t  l nes. ta. nject on_type"))

  // Used to  nd cate wh ch  nject on module  s t 
  val  NJECT ON_MODULE_NAME = new Text(na ("t  l nes. ta. nject on_module_na "))

  val L ST_ D = new D screte(na ("t  l nes. ta.l st_ d"))
  val L ST_ S_P NNED = new B nary(na ("t  l nes. ta.l st_ s_p nned"))

  //  nternal  d per each PS request. ma nly to jo n back commomn features and cand date features later
  val PRED CT ON_REQUEST_ D = new D screte(na ("t  l nes. ta.pred ct on_request_ d"))
  //  nternal  d per each TLM request. ma nly to dedupl cate re-served cac d t ets  n logg ng
  val SERVED_REQUEST_ D = new D screte(na ("t  l nes. ta.served_request_ d"))
  //  nternal  d used for jo n key  n kafka logg ng, equal to servedRequest d  f t et  s cac d,
  // else equal to pred ct onRequest d
  val SERVED_ D = new D screte(na ("t  l nes. ta.served_ d"))
  val REQUEST_JO N_ D = new D screte(na ("t  l nes. ta.request_jo n_ d"))

  //  nternal boolean flag per t et, w t r t  t et  s served from RankedT etsCac : TQ-14050
  // t  feature should not be tra ned on, blackl sted  n feature_conf g: D838346
  val  S_READ_FROM_CACHE = new B nary(na ("t  l nes. ta. s_read_from_cac "))

  // model score d scounts
  val PHOTO_D SCOUNT = new Cont nuous(na ("t  l nes.score_d scounts.photo"))
  val V DEO_D SCOUNT = new Cont nuous(na ("t  l nes.score_d scounts.v deo"))
  val TWEET_HE GHT_D SCOUNT = new Cont nuous(na ("t  l nes.score_d scounts.t et_  ght"))
  val TOX C TY_D SCOUNT = new Cont nuous(na ("t  l nes.score_d scounts.tox c y"))

  // engage nts
  val ENGAGEMENT_TYPE = new D screte(na ("t  l nes.engage nt.type"))
  val PRED CTED_ S_FAVOR TED =
    new Cont nuous(na ("t  l nes.engage nt_pred cted. s_favor ed"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_RETWEETED =
    new Cont nuous(na ("t  l nes.engage nt_pred cted. s_ret eted"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_QUOTED =
    new Cont nuous(na ("t  l nes.engage nt_pred cted. s_quoted"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_REPL ED =
    new Cont nuous(na ("t  l nes.engage nt_pred cted. s_repl ed"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_OPEN_L NKED = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_open_l nked"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_GOOD_OPEN_L NK = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_good_open_l nk"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_PROF LE_CL CKED = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_prof le_cl cked"),
    Set(Engage ntScore).asJava
  )
  val PRED CTED_ S_PROF LE_CL CKED_AND_PROF LE_ENGAGED = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_prof le_cl cked_and_prof le_engaged"),
    Set(Engage ntScore).asJava
  )
  val PRED CTED_ S_CL CKED =
    new Cont nuous(na ("t  l nes.engage nt_pred cted. s_cl cked"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_PHOTO_EXPANDED = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_photo_expanded"),
    Set(Engage ntScore).asJava
  )
  val PRED CTED_ S_FOLLOWED =
    new Cont nuous(na ("t  l nes.engage nt_pred cted. s_follo d"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_DONT_L KE =
    new Cont nuous(na ("t  l nes.engage nt_pred cted. s_dont_l ke"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_V DEO_PLAYBACK_50 = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_v deo_playback_50"),
    Set(Engage ntScore).asJava
  )
  val PRED CTED_ S_V DEO_QUAL TY_V EWED = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_v deo_qual y_v e d"),
    Set(Engage ntScore).asJava
  )
  val PRED CTED_ S_GOOD_CL CKED_V1 = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_good_cl cked_convo_desc_favor ed_or_repl ed"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_GOOD_CL CKED_V2 = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_good_cl cked_convo_desc_v2"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_TWEET_DETA L_DWELLED_8_SEC = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_t et_deta l_d lled_8_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_TWEET_DETA L_DWELLED_15_SEC = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_t et_deta l_d lled_15_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_TWEET_DETA L_DWELLED_25_SEC = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_t et_deta l_d lled_25_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_TWEET_DETA L_DWELLED_30_SEC = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_t et_deta l_d lled_30_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_GOOD_CL CKED_W TH_DWELL_SUM_GTE_60S = new Cont nuous(
    na (
      "t  l nes.engage nt_pred cted. s_good_cl cked_convo_desc_favor ed_or_repl ed_or_d ll_sum_gte_60_secs"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_FAVOR TED_FAV_ENGAGED_BY_AUTHOR = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_favor ed_fav_engaged_by_author"),
    Set(Engage ntScore).asJava)

  val PRED CTED_ S_REPORT_TWEET_CL CKED =
    new Cont nuous(
      na ("t  l nes.engage nt_pred cted. s_report_t et_cl cked"),
      Set(Engage ntScore).asJava)
  val PRED CTED_ S_NEGAT VE_FEEDBACK = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_negat ve_feedback"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_NEGAT VE_FEEDBACK_V2 = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_negat ve_feedback_v2"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_WEAK_NEGAT VE_FEEDBACK = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_ ak_negat ve_feedback"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_STRONG_NEGAT VE_FEEDBACK = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_strong_negat ve_feedback"),
    Set(Engage ntScore).asJava)

  val PRED CTED_ S_DWELLED_ N_BOUNDS_V1 = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_d lled_ n_bounds_v1"),
    Set(Engage ntScore).asJava)
  val PRED CTED_DWELL_NORMAL ZED_OVERALL = new Cont nuous(
    na ("t  l nes.engage nt_pred cted.d ll_normal zed_overall"),
    Set(Engage ntScore).asJava)
  val PRED CTED_DWELL_CDF =
    new Cont nuous(na ("t  l nes.engage nt_pred cted.d ll_cdf"), Set(Engage ntScore).asJava)
  val PRED CTED_DWELL_CDF_OVERALL = new Cont nuous(
    na ("t  l nes.engage nt_pred cted.d ll_cdf_overall"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_DWELLED =
    new Cont nuous(na ("t  l nes.engage nt_pred cted. s_d lled"), Set(Engage ntScore).asJava)

  val PRED CTED_ S_HOME_LATEST_V S TED = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_ho _latest_v s ed"),
    Set(Engage ntScore).asJava)

  val PRED CTED_ S_BOOKMARKED = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_bookmarked"),
    Set(Engage ntScore).asJava)

  val PRED CTED_ S_SHARED =
    new Cont nuous(na ("t  l nes.engage nt_pred cted. s_shared"), Set(Engage ntScore).asJava)
  val PRED CTED_ S_SHARE_MENU_CL CKED = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_share_ nu_cl cked"),
    Set(Engage ntScore).asJava)

  val PRED CTED_ S_PROF LE_DWELLED_20_SEC = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_prof le_d lled_20_sec"),
    Set(Engage ntScore).asJava)

  val PRED CTED_ S_FULLSCREEN_V DEO_DWELLED_5_SEC = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_fullscreen_v deo_d lled_5_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_FULLSCREEN_V DEO_DWELLED_10_SEC = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_fullscreen_v deo_d lled_10_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_FULLSCREEN_V DEO_DWELLED_20_SEC = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_fullscreen_v deo_d lled_20_sec"),
    Set(Engage ntScore).asJava)
  val PRED CTED_ S_FULLSCREEN_V DEO_DWELLED_30_SEC = new Cont nuous(
    na ("t  l nes.engage nt_pred cted. s_fullscreen_v deo_d lled_30_sec"),
    Set(Engage ntScore).asJava)

  // Please use t  t  stamp, not t  ` ta.t  stamp`, for t  actual served t  stamp.
  val SERVED_T MESTAMP =
    new D screte("t  l nes. ta.t  stamp.served", Set(Pr vateT  stamp).asJava)

  // t  stamp w n t  engage nt has occurred. do not tra n on t se features
  val T MESTAMP_FAVOR TED =
    new D screte("t  l nes. ta.t  stamp.engage nt.favor ed", Set(Publ cT  stamp).asJava)
  val T MESTAMP_RETWEETED =
    new D screte("t  l nes. ta.t  stamp.engage nt.ret eted", Set(Publ cT  stamp).asJava)
  val T MESTAMP_REPL ED =
    new D screte("t  l nes. ta.t  stamp.engage nt.repl ed", Set(Publ cT  stamp).asJava)
  val T MESTAMP_PROF LE_CL CKED = new D screte(
    "t  l nes. ta.t  stamp.engage nt.prof le_cl cked",
    Set(Pr vateT  stamp).asJava)
  val T MESTAMP_CL CKED =
    new D screte("t  l nes. ta.t  stamp.engage nt.cl cked", Set(Pr vateT  stamp).asJava)
  val T MESTAMP_PHOTO_EXPANDED =
    new D screte("t  l nes. ta.t  stamp.engage nt.photo_expanded", Set(Pr vateT  stamp).asJava)
  val T MESTAMP_DWELLED =
    new D screte("t  l nes. ta.t  stamp.engage nt.d lled", Set(Pr vateT  stamp).asJava)
  val T MESTAMP_V DEO_PLAYBACK_50 = new D screte(
    "t  l nes. ta.t  stamp.engage nt.v deo_playback_50",
    Set(Pr vateT  stamp).asJava)
  // reply engaged by author
  val T MESTAMP_REPLY_FAVOR TED_BY_AUTHOR = new D screte(
    "t  l nes. ta.t  stamp.engage nt.reply_favor ed_by_author",
    Set(Publ cT  stamp).asJava)
  val T MESTAMP_REPLY_REPL ED_BY_AUTHOR = new D screte(
    "t  l nes. ta.t  stamp.engage nt.reply_repl ed_by_author",
    Set(Publ cT  stamp).asJava)
  val T MESTAMP_REPLY_RETWEETED_BY_AUTHOR = new D screte(
    "t  l nes. ta.t  stamp.engage nt.reply_ret eted_by_author",
    Set(Publ cT  stamp).asJava)
  // fav engaged by author
  val T MESTAMP_FAV_FAVOR TED_BY_AUTHOR = new D screte(
    "t  l nes. ta.t  stamp.engage nt.fav_favor ed_by_author",
    Set(Publ cT  stamp).asJava)
  val T MESTAMP_FAV_REPL ED_BY_AUTHOR = new D screte(
    "t  l nes. ta.t  stamp.engage nt.fav_repl ed_by_author",
    Set(Publ cT  stamp).asJava)
  val T MESTAMP_FAV_RETWEETED_BY_AUTHOR = new D screte(
    "t  l nes. ta.t  stamp.engage nt.fav_ret eted_by_author",
    Set(Publ cT  stamp).asJava)
  val T MESTAMP_FAV_FOLLOWED_BY_AUTHOR = new D screte(
    "t  l nes. ta.t  stamp.engage nt.fav_follo d_by_author",
    Set(Publ cT  stamp).asJava)
  // good cl ck
  val T MESTAMP_GOOD_CL CK_CONVO_DESC_FAVOR TED = new D screte(
    "t  l nes. ta.t  stamp.engage nt.good_cl ck_convo_desc_favor ed",
    Set(Pr vateT  stamp).asJava)
  val T MESTAMP_GOOD_CL CK_CONVO_DESC_REPL  ED = new D screte(
    "t  l nes. ta.t  stamp.engage nt.good_cl ck_convo_desc_repl ed",
    Set(Pr vateT  stamp).asJava)
  val T MESTAMP_GOOD_CL CK_CONVO_DESC_PROF LE_CL CKED = new D screte(
    "t  l nes. ta.t  stamp.engage nt.good_cl ck_convo_desc_prof  le_cl cked",
    Set(Pr vateT  stamp).asJava)
  val T MESTAMP_NEGAT VE_FEEDBACK = new D screte(
    "t  l nes. ta.t  stamp.engage nt.negat ve_feedback",
    Set(Pr vateT  stamp).asJava)
  val T MESTAMP_REPORT_TWEET_CL CK =
    new D screte(
      "t  l nes. ta.t  stamp.engage nt.report_t et_cl ck",
      Set(Pr vateT  stamp).asJava)
  val T MESTAMP_ MPRESSED =
    new D screte("t  l nes. ta.t  stamp.engage nt. mpressed", Set(Publ cT  stamp).asJava)
  val T MESTAMP_TWEET_DETA L_DWELLED =
    new D screte(
      "t  l nes. ta.t  stamp.engage nt.t et_deta l_d lled",
      Set(Publ cT  stamp).asJava)
  val T MESTAMP_PROF LE_DWELLED =
    new D screte("t  l nes. ta.t  stamp.engage nt.prof le_d lled", Set(Publ cT  stamp).asJava)
  val T MESTAMP_FULLSCREEN_V DEO_DWELLED =
    new D screte(
      "t  l nes. ta.t  stamp.engage nt.fullscreen_v deo_d lled",
      Set(Publ cT  stamp).asJava)
  val T MESTAMP_L NK_DWELLED =
    new D screte("t  l nes. ta.t  stamp.engage nt.l nk_d lled", Set(Publ cT  stamp).asJava)

  // t se are used to dup and spl  t  negat ve  nstances dur ng stream ng process ng (kafka)
  val TRA N NG_FOR_FAVOR TED =
    new B nary("t  l nes. ta.tra n ng_data.for_favor ed", Set(Engage nt d).asJava)
  val TRA N NG_FOR_RETWEETED =
    new B nary("t  l nes. ta.tra n ng_data.for_ret eted", Set(Engage nt d).asJava)
  val TRA N NG_FOR_REPL ED =
    new B nary("t  l nes. ta.tra n ng_data.for_repl ed", Set(Engage nt d).asJava)
  val TRA N NG_FOR_PROF LE_CL CKED =
    new B nary("t  l nes. ta.tra n ng_data.for_prof le_cl cked", Set(Engage nt d).asJava)
  val TRA N NG_FOR_CL CKED =
    new B nary("t  l nes. ta.tra n ng_data.for_cl cked", Set(Engage nt d).asJava)
  val TRA N NG_FOR_PHOTO_EXPANDED =
    new B nary("t  l nes. ta.tra n ng_data.for_photo_expanded", Set(Engage nt d).asJava)
  val TRA N NG_FOR_V DEO_PLAYBACK_50 =
    new B nary("t  l nes. ta.tra n ng_data.for_v deo_playback_50", Set(Engage nt d).asJava)
  val TRA N NG_FOR_NEGAT VE_FEEDBACK =
    new B nary("t  l nes. ta.tra n ng_data.for_negat ve_feedback", Set(Engage nt d).asJava)
  val TRA N NG_FOR_REPORTED =
    new B nary("t  l nes. ta.tra n ng_data.for_reported", Set(Engage nt d).asJava)
  val TRA N NG_FOR_DWELLED =
    new B nary("t  l nes. ta.tra n ng_data.for_d lled", Set(Engage nt d).asJava)
  val TRA N NG_FOR_SHARED =
    new B nary("t  l nes. ta.tra n ng_data.for_shared", Set(Engage nt d).asJava)
  val TRA N NG_FOR_SHARE_MENU_CL CKED =
    new B nary("t  l nes. ta.tra n ng_data.for_share_ nu_cl cked", Set(Engage nt d).asJava)

  // Warn ng: do not tra n on t se features
  val PRED CTED_SCORE = new Cont nuous(na ("t  l nes.score"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_FAV = new Cont nuous(na ("t  l nes.score.fav"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_RETWEET =
    new Cont nuous(na ("t  l nes.score.ret et"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_REPLY =
    new Cont nuous(na ("t  l nes.score.reply"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_OPEN_L NK =
    new Cont nuous(na ("t  l nes.score.open_l nk"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_GOOD_OPEN_L NK =
    new Cont nuous(na ("t  l nes.score.good_open_l nk"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_PROF LE_CL CK =
    new Cont nuous(na ("t  l nes.score.prof le_cl ck"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_DETA L_EXPAND =
    new Cont nuous(na ("t  l nes.score.deta l_expand"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_PHOTO_EXPAND =
    new Cont nuous(na ("t  l nes.score.photo_expand"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_PLAYBACK_50 =
    new Cont nuous(na ("t  l nes.score.playback_50"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_V DEO_QUAL TY_V EW =
    new Cont nuous(na ("t  l nes.score.v deo_qual y_v ew"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_DONT_L KE =
    new Cont nuous(na ("t  l nes.score.dont_l ke"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_PROF LE_CL CKED_AND_PROF LE_ENGAGED =
    new Cont nuous(
      na ("t  l nes.score.prof le_cl cked_and_prof le_engaged"),
      Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_GOOD_CL CKED_V1 =
    new Cont nuous(na ("t  l nes.score.good_cl cked_v1"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_GOOD_CL CKED_V2 =
    new Cont nuous(na ("t  l nes.score.good_cl cked_v2"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_DWELL =
    new Cont nuous(na ("t  l nes.score.d ll"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_DWELL_CDF =
    new Cont nuous(na ("t  l nes.score.d ll_cfd"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_DWELL_CDF_OVERALL =
    new Cont nuous(na ("t  l nes.score.d ll_cfd_overall"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_DWELL_NORMAL ZED_OVERALL =
    new Cont nuous(na ("t  l nes.score.d ll_normal zed_overall"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_NEGAT VE_FEEDBACK =
    new Cont nuous(na ("t  l nes.score.negat ve_feedback"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_NEGAT VE_FEEDBACK_V2 =
    new Cont nuous(na ("t  l nes.score.negat ve_feedback_v2"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_WEAK_NEGAT VE_FEEDBACK =
    new Cont nuous(na ("t  l nes.score. ak_negat ve_feedback"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_STRONG_NEGAT VE_FEEDBACK =
    new Cont nuous(na ("t  l nes.score.strong_negat ve_feedback"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_REPORT_TWEET_CL CKED =
    new Cont nuous(na ("t  l nes.score.report_t et_cl cked"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_UNFOLLOW_TOP C =
    new Cont nuous(na ("t  l nes.score.unfollow_top c"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_FOLLOW =
    new Cont nuous(na ("t  l nes.score.follow"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_RELEVANCE_PROMPT_YES_CL CKED =
    new Cont nuous(
      na ("t  l nes.score.relevance_prompt_yes_cl cked"),
      Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_BOOKMARK =
    new Cont nuous(na ("t  l nes.score.bookmark"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_SHARE =
    new Cont nuous(na ("t  l nes.score.share"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_SHARE_MENU_CL CK =
    new Cont nuous(na ("t  l nes.score.share_ nu_cl ck"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_PROF LE_DWELLED =
    new Cont nuous(na ("t  l nes.score.good_prof le_d lled"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_TWEET_DETA L_DWELLED =
    new Cont nuous(na ("t  l nes.score.t et_deta l_d lled"), Set(Engage ntScore).asJava)
  val PRED CTED_SCORE_FULLSCREEN_V DEO_DWELL =
    new Cont nuous(na ("t  l nes.score.fullscreen_v deo_d ll"), Set(Engage ntScore).asJava)

  // hydrated  n T  l nesSharedFeaturesAdapter that recap adapter calls
  val OR G NAL_AUTHOR_ D = new D screte(na ("ent  es.or g nal_author_ d"), Set(User d).asJava)
  val SOURCE_AUTHOR_ D = new D screte(na ("ent  es.s ce_author_ d"), Set(User d).asJava)
  val SOURCE_TWEET_ D = new D screte(na ("ent  es.s ce_t et_ d"), Set(T et d).asJava)
  val TOP C_ D = new D screte(na ("ent  es.top c_ d"), Set(Semant ccoreClass f cat on).asJava)
  val  NFERRED_TOP C_ DS =
    new SparseB nary(na ("ent  es. nferred_top c_ ds"), Set(Semant ccoreClass f cat on).asJava)
  val  NFERRED_TOP C_ D = TypedAggregateGroup.sparseFeature( NFERRED_TOP C_ DS)

  val WE GHTED_FAV_COUNT = new Cont nuous(
    na ("t  l nes.earlyb rd.  ghted_fav_count"),
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava)
  val WE GHTED_RETWEET_COUNT = new Cont nuous(
    na ("t  l nes.earlyb rd.  ghted_ret et_count"),
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava)
  val WE GHTED_REPLY_COUNT = new Cont nuous(
    na ("t  l nes.earlyb rd.  ghted_reply_count"),
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava)
  val WE GHTED_QUOTE_COUNT = new Cont nuous(
    na ("t  l nes.earlyb rd.  ghted_quote_count"),
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava)
  val EMBEDS_ MPRESS ON_COUNT_V2 = new Cont nuous(
    na ("t  l nes.earlyb rd.embeds_ mpress on_count_v2"),
    Set(CountOf mpress on).asJava)
  val EMBEDS_URL_COUNT_V2 = new Cont nuous(
    na ("t  l nes.earlyb rd.embeds_url_count_v2"),
    Set(CountOfPr vateT etEnt  esAnd tadata, CountOfPubl cT etEnt  esAnd tadata).asJava)
  val DECAYED_FAVOR TE_COUNT = new Cont nuous(
    na ("t  l nes.earlyb rd.decayed_favor e_count"),
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava)
  val DECAYED_RETWEET_COUNT = new Cont nuous(
    na ("t  l nes.earlyb rd.decayed_ret et_count"),
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava)
  val DECAYED_REPLY_COUNT = new Cont nuous(
    na ("t  l nes.earlyb rd.decayed_reply_count"),
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava)
  val DECAYED_QUOTE_COUNT = new Cont nuous(
    na ("t  l nes.earlyb rd.decayed_quote_count"),
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava)
  val FAKE_FAVOR TE_COUNT = new Cont nuous(
    na ("t  l nes.earlyb rd.fake_favor e_count"),
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava)
  val FAKE_RETWEET_COUNT = new Cont nuous(
    na ("t  l nes.earlyb rd.fake_ret et_count"),
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava)
  val FAKE_REPLY_COUNT = new Cont nuous(
    na ("t  l nes.earlyb rd.fake_reply_count"),
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava)
  val FAKE_QUOTE_COUNT = new Cont nuous(
    na ("t  l nes.earlyb rd.fake_quote_count"),
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava)
  val QUOTE_COUNT = new Cont nuous(
    na ("t  l nes.earlyb rd.quote_count"),
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava)

  // Safety features
  val LABEL_ABUS VE_FLAG =
    new B nary(na ("t  l nes.earlyb rd.label_abus ve_flag"), Set(T etSafetyLabels).asJava)
  val LABEL_ABUS VE_H _RCL_FLAG =
    new B nary(na ("t  l nes.earlyb rd.label_abus ve_h _rcl_flag"), Set(T etSafetyLabels).asJava)
  val LABEL_DUP_CONTENT_FLAG =
    new B nary(na ("t  l nes.earlyb rd.label_dup_content_flag"), Set(T etSafetyLabels).asJava)
  val LABEL_NSFW_H _PRC_FLAG =
    new B nary(na ("t  l nes.earlyb rd.label_nsfw_h _prc_flag"), Set(T etSafetyLabels).asJava)
  val LABEL_NSFW_H _RCL_FLAG =
    new B nary(na ("t  l nes.earlyb rd.label_nsfw_h _rcl_flag"), Set(T etSafetyLabels).asJava)
  val LABEL_SPAM_FLAG =
    new B nary(na ("t  l nes.earlyb rd.label_spam_flag"), Set(T etSafetyLabels).asJava)
  val LABEL_SPAM_H _RCL_FLAG =
    new B nary(na ("t  l nes.earlyb rd.label_spam_h _rcl_flag"), Set(T etSafetyLabels).asJava)

  // Per scope features
  val PER SCOPE_EX STS = new B nary(
    na ("t  l nes.earlyb rd.per scope_ex sts"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val PER SCOPE_ S_L VE = new B nary(
    na ("t  l nes.earlyb rd.per scope_ s_l ve"),
    Set(Pr vateBroadcast tr cs, Publ cBroadcast tr cs).asJava)
  val PER SCOPE_HAS_BEEN_FEATURED = new B nary(
    na ("t  l nes.earlyb rd.per scope_has_been_featured"),
    Set(Pr vateBroadcast tr cs, Publ cBroadcast tr cs).asJava)
  val PER SCOPE_ S_CURRENTLY_FEATURED = new B nary(
    na ("t  l nes.earlyb rd.per scope_ s_currently_featured"),
    Set(Pr vateBroadcast tr cs, Publ cBroadcast tr cs).asJava
  )
  val PER SCOPE_ S_FROM_QUAL TY_SOURCE = new B nary(
    na ("t  l nes.earlyb rd.per scope_ s_from_qual y_s ce"),
    Set(Pr vateBroadcast tr cs, Publ cBroadcast tr cs).asJava
  )

  val V S BLE_TOKEN_RAT O = new Cont nuous(na ("t  l nes.earlyb rd.v s ble_token_rat o"))
  val HAS_QUOTE = new B nary(
    na ("t  l nes.earlyb rd.has_quote"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val  S_COMPOSER_SOURCE_CAMERA = new B nary(
    na ("t  l nes.earlyb rd. s_composer_s ce_ca ra"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)

  val EARLYB RD_SCORE = new Cont nuous(
    na ("t  l nes.earlyb rd_score"),
    Set(Engage ntScore).asJava
  ) // separat ng from t  rest of "t  l nes.earlyb rd." na space

  val DWELL_T ME_MS = new Cont nuous(
    na ("t  l nes.engage nt.d ll_t  _ms"),
    Set(Engage ntDurat onAndT  stamp,  mpress on tadata, Pr vateT  stamp).asJava)

  val TWEET_DETA L_DWELL_T ME_MS = new Cont nuous(
    na ("t  l nes.engage nt.t et_deta l_d ll_t  _ms"),
    Set(Engage ntDurat onAndT  stamp,  mpress on tadata, Pr vateT  stamp).asJava)

  val PROF LE_DWELL_T ME_MS = new Cont nuous(
    na ("t  l nes.engage nt.prof le_d ll_t  _ms"),
    Set(Engage ntDurat onAndT  stamp,  mpress on tadata, Pr vateT  stamp).asJava)

  val FULLSCREEN_V DEO_DWELL_T ME_MS = new Cont nuous(
    na ("t  l nes.engage nt.fullscreen_v deo_d ll_t  _ms"),
    Set(Engage ntDurat onAndT  stamp,  mpress on tadata, Pr vateT  stamp).asJava)

  val L NK_DWELL_T ME_MS = new Cont nuous(
    na ("t  l nes.engage nt.l nk_d ll_t  _ms"),
    Set(Engage ntDurat onAndT  stamp,  mpress on tadata, Pr vateT  stamp).asJava)

  val ASPECT_RAT O_DEN = new Cont nuous(
    na ("t ets ce.t et. d a.aspect_rat o_den"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val ASPECT_RAT O_NUM = new Cont nuous(
    na ("t ets ce.t et. d a.aspect_rat o_num"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val B T_RATE = new Cont nuous(
    na ("t ets ce.t et. d a.b _rate"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val HE GHT_2 = new Cont nuous(
    na ("t ets ce.t et. d a.  ght_2"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val HE GHT_1 = new Cont nuous(
    na ("t ets ce.t et. d a.  ght_1"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val HE GHT_3 = new Cont nuous(
    na ("t ets ce.t et. d a.  ght_3"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val HE GHT_4 = new Cont nuous(
    na ("t ets ce.t et. d a.  ght_4"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val RES ZE_METHOD_1 = new D screte(
    na ("t ets ce.t et. d a.res ze_ thod_1"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val RES ZE_METHOD_2 = new D screte(
    na ("t ets ce.t et. d a.res ze_ thod_2"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val RES ZE_METHOD_3 = new D screte(
    na ("t ets ce.t et. d a.res ze_ thod_3"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val RES ZE_METHOD_4 = new D screte(
    na ("t ets ce.t et. d a.res ze_ thod_4"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val V DEO_DURAT ON = new Cont nuous(
    na ("t ets ce.t et. d a.v deo_durat on"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val W DTH_1 = new Cont nuous(
    na ("t ets ce.t et. d a.w dth_1"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val W DTH_2 = new Cont nuous(
    na ("t ets ce.t et. d a.w dth_2"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val W DTH_3 = new Cont nuous(
    na ("t ets ce.t et. d a.w dth_3"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val W DTH_4 = new Cont nuous(
    na ("t ets ce.t et. d a.w dth_4"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val NUM_MED A_TAGS = new Cont nuous(
    na ("t ets ce.t et. d a.num_tags"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val MED A_TAG_SCREEN_NAMES = new SparseB nary(
    na ("t ets ce.t et. d a.tag_screen_na s"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val ST CKER_ DS = new SparseB nary(
    na ("t ets ce.t et. d a.st cker_ ds"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)

  val NUM_COLOR_PALLETTE_ TEMS = new Cont nuous(
    na ("t ets ce.v2.t et. d a.num_color_pallette_ ems"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val COLOR_1_RED = new Cont nuous(
    na ("t ets ce.v2.t et. d a.color_1_red"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val COLOR_1_BLUE = new Cont nuous(
    na ("t ets ce.v2.t et. d a.color_1_blue"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val COLOR_1_GREEN = new Cont nuous(
    na ("t ets ce.v2.t et. d a.color_1_green"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val COLOR_1_PERCENTAGE = new Cont nuous(
    na ("t ets ce.v2.t et. d a.color_1_percentage"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val MED A_PROV DERS = new SparseB nary(
    na ("t ets ce.v2.t et. d a.prov ders"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val  S_360 = new B nary(
    na ("t ets ce.v2.t et. d a. s_360"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val V EW_COUNT =
    new Cont nuous(na ("t ets ce.v2.t et. d a.v ew_count"), Set( d aContent tr cs).asJava)
  val  S_MANAGED = new B nary(
    na ("t ets ce.v2.t et. d a. s_managed"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val  S_MONET ZABLE = new B nary(
    na ("t ets ce.v2.t et. d a. s_monet zable"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val  S_EMBEDDABLE = new B nary(
    na ("t ets ce.v2.t et. d a. s_embeddable"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val CLASS F CAT ON_LABELS = new SparseCont nuous(
    na ("t ets ce.v2.t et. d a.class f cat on_labels"),
    Set( d aF le,  d aProcess ng nformat on).asJava)

  val NUM_ST CKERS = new Cont nuous(
    na ("t ets ce.v2.t et. d a.num_st ckers"),
    Set(Publ cT etEnt  esAnd tadata, Pr vateT etEnt  esAnd tadata).asJava)
  val NUM_FACES = new Cont nuous(
    na ("t ets ce.v2.t et. d a.num_faces"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val FACE_AREAS = new Cont nuous(
    na ("t ets ce.v2.t et. d a.face_areas"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val HAS_SELECTED_PREV EW_ MAGE = new B nary(
    na ("t ets ce.v2.t et. d a.has_selected_prev ew_ mage"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val HAS_T TLE = new B nary(
    na ("t ets ce.v2.t et. d a.has_t le"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val HAS_DESCR PT ON = new B nary(
    na ("t ets ce.v2.t et. d a.has_descr pt on"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val HAS_V S T_S TE_CALL_TO_ACT ON = new B nary(
    na ("t ets ce.v2.t et. d a.has_v s _s e_call_to_act on"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val HAS_APP_ NSTALL_CALL_TO_ACT ON = new B nary(
    na ("t ets ce.v2.t et. d a.has_app_ nstall_call_to_act on"),
    Set( d aF le,  d aProcess ng nformat on).asJava)
  val HAS_WATCH_NOW_CALL_TO_ACT ON = new B nary(
    na ("t ets ce.v2.t et. d a.has_watch_now_call_to_act on"),
    Set( d aF le,  d aProcess ng nformat on).asJava)

  val NUM_CAPS =
    new Cont nuous(na ("t ets ce.t et.text.num_caps"), Set(Publ cT ets, Pr vateT ets).asJava)
  val TWEET_LENGTH =
    new Cont nuous(na ("t ets ce.t et.text.length"), Set(Publ cT ets, Pr vateT ets).asJava)
  val TWEET_LENGTH_TYPE = new D screte(
    na ("t ets ce.t et.text.length_type"),
    Set(Publ cT ets, Pr vateT ets).asJava)
  val NUM_WH TESPACES = new Cont nuous(
    na ("t ets ce.t et.text.num_wh espaces"),
    Set(Publ cT ets, Pr vateT ets).asJava)
  val HAS_QUEST ON =
    new B nary(na ("t ets ce.t et.text.has_quest on"), Set(Publ cT ets, Pr vateT ets).asJava)
  val NUM_NEWL NES = new Cont nuous(
    na ("t ets ce.t et.text.num_newl nes"),
    Set(Publ cT ets, Pr vateT ets).asJava)
  val EMOJ _TOKENS = new SparseB nary(
    na ("t ets ce.v3.t et.text.emoj _tokens"),
    Set(Publ cT ets, Pr vateT ets).asJava)
  val EMOT CON_TOKENS = new SparseB nary(
    na ("t ets ce.v3.t et.text.emot con_tokens"),
    Set(Publ cT ets, Pr vateT ets).asJava)
  val NUM_EMOJ S = new Cont nuous(
    na ("t ets ce.v3.t et.text.num_emoj s"),
    Set(Publ cT ets, Pr vateT ets).asJava)
  val NUM_EMOT CONS = new Cont nuous(
    na ("t ets ce.v3.t et.text.num_emot cons"),
    Set(Publ cT ets, Pr vateT ets).asJava)
  val POS_UN GRAMS = new SparseB nary(
    na ("t ets ce.v3.t et.text.pos_un grams"),
    Set(Publ cT ets, Pr vateT ets).asJava)
  val POS_B GRAMS = new SparseB nary(
    na ("t ets ce.v3.t et.text.pos_b grams"),
    Set(Publ cT ets, Pr vateT ets).asJava)
  val TEXT_TOKENS = new SparseB nary(
    na ("t ets ce.v4.t et.text.tokens"),
    Set(Publ cT ets, Pr vateT ets).asJava)

  //  alth features model scores (see go/tox c y, go/pblock, go/pspam t et)
  val PBLOCK_SCORE =
    new Cont nuous(na ("t  l nes.earlyb rd.pblock_score"), Set(T etSafetyScores).asJava)
  val TOX C TY_SCORE =
    new Cont nuous(na ("t  l nes.earlyb rd.tox c y_score"), Set(T etSafetyScores).asJava)
  val EXPER MENTAL_HEALTH_MODEL_SCORE_1 =
    new Cont nuous(
      na ("t  l nes.earlyb rd.exper  ntal_ alth_model_score_1"),
      Set(T etSafetyScores).asJava)
  val EXPER MENTAL_HEALTH_MODEL_SCORE_2 =
    new Cont nuous(
      na ("t  l nes.earlyb rd.exper  ntal_ alth_model_score_2"),
      Set(T etSafetyScores).asJava)
  val EXPER MENTAL_HEALTH_MODEL_SCORE_3 =
    new Cont nuous(
      na ("t  l nes.earlyb rd.exper  ntal_ alth_model_score_3"),
      Set(T etSafetyScores).asJava)
  val EXPER MENTAL_HEALTH_MODEL_SCORE_4 =
    new Cont nuous(
      na ("t  l nes.earlyb rd.exper  ntal_ alth_model_score_4"),
      Set(T etSafetyScores).asJava)
  val PSPAMMY_TWEET_SCORE =
    new Cont nuous(na ("t  l nes.earlyb rd.pspam _t et_score"), Set(T etSafetyScores).asJava)
  val PREPORTED_TWEET_SCORE =
    new Cont nuous(na ("t  l nes.earlyb rd.preported_t et_score"), Set(T etSafetyScores).asJava)

  // w re record was d splayed e.g. recap vs ranked t  l ne vs recycled
  // (do NOT use for tra n ng  n pred ct on, s nce t   s set post-scor ng)
  // T  d ffers from T  l nesSharedFeatures. NJECT ON_TYPE, wh ch  s only
  // set to Recap or Rect et, and  s ava lable pre-scor ng.
  // T  also d ffers from T  Features. S_TWEET_RECYCLED, wh ch  s set
  // pre-scor ng and  nd cates  f a t et  s be ng cons dered for recycl ng.
  //  n contrast, D SPLAY_SUGGEST_TYPE == RecycledT et  ans t  t et
  // was actually served  n a recycled t et module. T  two should currently
  // have t  sa  value, but need not  n future, so please only use
  //  S_TWEET_RECYCLED/CAND DATE_TWEET_SOURCE_ D for tra n ng models and
  // only use D SPLAY_SUGGEST_TYPE for offl ne analys s of t ets actually
  // served  n recycled modules.
  val D SPLAY_SUGGEST_TYPE = new D screte(na ("recap.d splay.suggest_type"))

  // Cand date t et s ce  d - related to D SPLAY_SUGGEST_TYPE above, but t   s a
  // property of t  cand date rat r than d splay locat on so  s safe to use
  //  n model tra n ng, unl ke D SPLAY_SUGGEST_TYPE.
  val CAND DATE_TWEET_SOURCE_ D =
    new D screte(na ("t  l nes. ta.cand date_t et_s ce_ d"), Set(T et d).asJava)

  // Was at least 50% of t  t et  n t  user's v ewport for at least 500 ms,
  // OR d d t  user engage w h t  t et publ cly or pr vately
  val  S_L NGER_ MPRESS ON =
    new B nary(na ("t  l nes.engage nt. s_l nger_ mpress on"), Set(Engage ntsPr vate).asJava)

  // Features to create rollups
  val LANGUAGE_GROUP = new D screte(na ("t  l nes.t et.text.language_group"))

  // T  f nal pos  on  ndex of t  t et be ng tra ned on  n t  t  l ne
  // served from TLM (could st ll change later  n TLS-AP ), as recorded by
  // Pos  on ndexLogg ngEnvelopeTransform.
  val F NAL_POS T ON_ NDEX = new D screte(na ("t  l nes.d splay.f nal_pos  on_ ndex"))

  // T  trace d of t  t  l ne request, can be used to group t ets  n t  sa  response.
  val TRACE_ D = new D screte(na ("t  l nes.d splay.trace_ d"), Set(TfeTransact on d).asJava)

  // W t r t  t et was randomly  njected  nto t  t  l ne or not, for explorat on purposes
  val  S_RANDOM_TWEET = new B nary(na ("t  l nes.d splay. s_random_t et"))

  //  W t r t  t et was reordered w h softmax rank ng for explore/explo , and needs to
  //  be excluded from explo  only holdback
  val  S_SOFTMAX_RANK NG_TWEET = new B nary(na ("t  l nes.d splay. s_softmax_rank ng_t et"))

  // W t r t  user v ew ng t  t et has d sabled ranked t  l ne.
  val  S_RANKED_T MEL NE_D SABLER = new B nary(
    na ("t  l nes.user_features. s_ranked_t  l ne_d sabler"),
    Set(Annotat onValue, GeneralSett ngs).asJava)

  // W t r t  user v ew ng t  t et was one of those released from DDG 4205 control
  // as part of http://go/shr nk-4205 process to shr nk t  qual y features holdback.
  val  S_USER_RELEASED_FROM_QUAL TY_HOLDBACK = new B nary(
    na ("t  l nes.user_features. s_released_from_qual y_holdback"),
    Set(Exper  nt d, Exper  ntNa ).asJava)

  val  N T AL_PRED CT ON_FAV =
    new Cont nuous(na ("t  l nes. n  al_pred ct on.fav"), Set(Engage ntScore).asJava)
  val  N T AL_PRED CT ON_RETWEET =
    new Cont nuous(na ("t  l nes. n  al_pred ct on.ret et"), Set(Engage ntScore).asJava)
  val  N T AL_PRED CT ON_REPLY =
    new Cont nuous(na ("t  l nes. n  al_pred ct on.reply"), Set(Engage ntScore).asJava)
  val  N T AL_PRED CT ON_OPEN_L NK =
    new Cont nuous(na ("t  l nes. n  al_pred ct on.open_l nk"), Set(Engage ntScore).asJava)
  val  N T AL_PRED CT ON_PROF LE_CL CK =
    new Cont nuous(na ("t  l nes. n  al_pred ct on.prof le_cl ck"), Set(Engage ntScore).asJava)
  val  N T AL_PRED CT ON_V DEO_PLAYBACK_50 = new Cont nuous(
    na ("t  l nes. n  al_pred ct on.v deo_playback_50"),
    Set(Engage ntScore).asJava)
  val  N T AL_PRED CT ON_DETA L_EXPAND =
    new Cont nuous(na ("t  l nes. n  al_pred ct on.deta l_expand"), Set(Engage ntScore).asJava)
  val  N T AL_PRED CT ON_PHOTO_EXPAND =
    new Cont nuous(na ("t  l nes. n  al_pred ct on.photo_expand"), Set(Engage ntScore).asJava)

  val V EWER_FOLLOWS_OR G NAL_AUTHOR =
    new B nary(na ("t  l nes.v e r_follows_or g nal_author"), Set(Follow).asJava)

  val  S_TOP_ONE = new B nary(na ("t  l nes.pos  on. s_top_one"))
  val  S_TOP_F VE =
    new B nary(na (featureNa  = "t  l nes.pos  on. s_top_f ve"))
  val  S_TOP_TEN =
    new B nary(na (featureNa  = "t  l nes.pos  on. s_top_ten"))

  val LOG_POS T ON =
    new Cont nuous(na (featureNa  = "t  l nes.pos  on.log_10"))

}
