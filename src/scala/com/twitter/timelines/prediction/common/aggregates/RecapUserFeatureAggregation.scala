package com.tw ter.t  l nes.pred ct on.common.aggregates

 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport com.tw ter.t  l nes.pred ct on.features.engage nt_features.Engage ntDataRecordFeatures
 mport com.tw ter.t  l nes.pred ct on.features.real_graph.RealGraphDataRecordFeatures
 mport com.tw ter.t  l nes.pred ct on.features.recap.RecapFeatures
 mport com.tw ter.t  l nes.pred ct on.features.t  _features.T  DataRecordFeatures

object RecapUserFeatureAggregat on {
  val RecapFeaturesForAggregat on: Set[Feature[_]] =
    Set(
      RecapFeatures.HAS_ MAGE,
      RecapFeatures.HAS_V DEO,
      RecapFeatures.FROM_MUTUAL_FOLLOW,
      RecapFeatures.HAS_CARD,
      RecapFeatures.HAS_NEWS,
      RecapFeatures.REPLY_COUNT,
      RecapFeatures.FAV_COUNT,
      RecapFeatures.RETWEET_COUNT,
      RecapFeatures.BLENDER_SCORE,
      RecapFeatures.CONVERSAT ONAL_COUNT,
      RecapFeatures. S_BUS NESS_SCORE,
      RecapFeatures.CONTA NS_MED A,
      RecapFeatures.RETWEET_SEARCHER,
      RecapFeatures.REPLY_SEARCHER,
      RecapFeatures.MENT ON_SEARCHER,
      RecapFeatures.REPLY_OTHER,
      RecapFeatures.RETWEET_OTHER,
      RecapFeatures.MATCH_U _LANG,
      RecapFeatures.MATCH_SEARCHER_MA N_LANG,
      RecapFeatures.MATCH_SEARCHER_LANGS,
      RecapFeatures.TWEET_COUNT_FROM_USER_ N_SNAPSHOT,
      RecapFeatures.TEXT_SCORE,
      RealGraphDataRecordFeatures.NUM_RETWEETS_EWMA,
      RealGraphDataRecordFeatures.NUM_RETWEETS_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.NUM_RETWEETS_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.NUM_RETWEETS_DAYS_S NCE_LAST,
      RealGraphDataRecordFeatures.NUM_FAVOR TES_EWMA,
      RealGraphDataRecordFeatures.NUM_FAVOR TES_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.NUM_FAVOR TES_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.NUM_FAVOR TES_DAYS_S NCE_LAST,
      RealGraphDataRecordFeatures.NUM_MENT ONS_EWMA,
      RealGraphDataRecordFeatures.NUM_MENT ONS_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.NUM_MENT ONS_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.NUM_MENT ONS_DAYS_S NCE_LAST,
      RealGraphDataRecordFeatures.NUM_TWEET_CL CKS_EWMA,
      RealGraphDataRecordFeatures.NUM_TWEET_CL CKS_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.NUM_TWEET_CL CKS_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.NUM_TWEET_CL CKS_DAYS_S NCE_LAST,
      RealGraphDataRecordFeatures.NUM_PROF LE_V EWS_EWMA,
      RealGraphDataRecordFeatures.NUM_PROF LE_V EWS_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.NUM_PROF LE_V EWS_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.NUM_PROF LE_V EWS_DAYS_S NCE_LAST,
      RealGraphDataRecordFeatures.TOTAL_DWELL_T ME_EWMA,
      RealGraphDataRecordFeatures.TOTAL_DWELL_T ME_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.TOTAL_DWELL_T ME_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.TOTAL_DWELL_T ME_DAYS_S NCE_LAST,
      RealGraphDataRecordFeatures.NUM_ NSPECTED_TWEETS_EWMA,
      RealGraphDataRecordFeatures.NUM_ NSPECTED_TWEETS_NON_ZERO_DAYS,
      RealGraphDataRecordFeatures.NUM_ NSPECTED_TWEETS_ELAPSED_DAYS,
      RealGraphDataRecordFeatures.NUM_ NSPECTED_TWEETS_DAYS_S NCE_LAST
    )

  val RecapLabelsForAggregat on: Set[Feature.B nary] =
    Set(
      RecapFeatures. S_FAVOR TED,
      RecapFeatures. S_RETWEETED,
      RecapFeatures. S_CL CKED,
      RecapFeatures. S_PROF LE_CL CKED,
      RecapFeatures. S_OPEN_L NKED
    )

  val D llDurat on: Set[Feature[_]] =
    Set(
      T  l nesSharedFeatures.DWELL_T ME_MS,
    )

  val UserFeaturesV2: Set[Feature[_]] = RecapFeaturesForAggregat on ++ Set(
    RecapFeatures.HAS_V NE,
    RecapFeatures.HAS_PER SCOPE,
    RecapFeatures.HAS_PRO_V DEO,
    RecapFeatures.HAS_V S BLE_L NK,
    RecapFeatures.B D RECT ONAL_FAV_COUNT,
    RecapFeatures.UN D RECT ONAL_FAV_COUNT,
    RecapFeatures.B D RECT ONAL_REPLY_COUNT,
    RecapFeatures.UN D RECT ONAL_REPLY_COUNT,
    RecapFeatures.B D RECT ONAL_RETWEET_COUNT,
    RecapFeatures.UN D RECT ONAL_RETWEET_COUNT,
    RecapFeatures.EMBEDS_URL_COUNT,
    RecapFeatures.EMBEDS_ MPRESS ON_COUNT,
    RecapFeatures.V DEO_V EW_COUNT,
    RecapFeatures. S_RETWEET,
    RecapFeatures. S_REPLY,
    RecapFeatures. S_EXTENDED_REPLY,
    RecapFeatures.HAS_L NK,
    RecapFeatures.HAS_TREND,
    RecapFeatures.L NK_LANGUAGE,
    RecapFeatures.NUM_HASHTAGS,
    RecapFeatures.NUM_MENT ONS,
    RecapFeatures. S_SENS T VE,
    RecapFeatures.HAS_MULT PLE_MED A,
    RecapFeatures.USER_REP,
    RecapFeatures.FAV_COUNT_V2,
    RecapFeatures.RETWEET_COUNT_V2,
    RecapFeatures.REPLY_COUNT_V2,
    RecapFeatures.L NK_COUNT,
    Engage ntDataRecordFeatures. nNetworkFavor esCount,
    Engage ntDataRecordFeatures. nNetworkRet etsCount,
    Engage ntDataRecordFeatures. nNetworkRepl esCount
  )

  val UserAuthorFeaturesV2: Set[Feature[_]] = Set(
    RecapFeatures.HAS_ MAGE,
    RecapFeatures.HAS_V NE,
    RecapFeatures.HAS_PER SCOPE,
    RecapFeatures.HAS_PRO_V DEO,
    RecapFeatures.HAS_V DEO,
    RecapFeatures.HAS_CARD,
    RecapFeatures.HAS_NEWS,
    RecapFeatures.HAS_V S BLE_L NK,
    RecapFeatures.REPLY_COUNT,
    RecapFeatures.FAV_COUNT,
    RecapFeatures.RETWEET_COUNT,
    RecapFeatures.BLENDER_SCORE,
    RecapFeatures.CONVERSAT ONAL_COUNT,
    RecapFeatures. S_BUS NESS_SCORE,
    RecapFeatures.CONTA NS_MED A,
    RecapFeatures.RETWEET_SEARCHER,
    RecapFeatures.REPLY_SEARCHER,
    RecapFeatures.MENT ON_SEARCHER,
    RecapFeatures.REPLY_OTHER,
    RecapFeatures.RETWEET_OTHER,
    RecapFeatures.MATCH_U _LANG,
    RecapFeatures.MATCH_SEARCHER_MA N_LANG,
    RecapFeatures.MATCH_SEARCHER_LANGS,
    RecapFeatures.TWEET_COUNT_FROM_USER_ N_SNAPSHOT,
    RecapFeatures.TEXT_SCORE,
    RecapFeatures.B D RECT ONAL_FAV_COUNT,
    RecapFeatures.UN D RECT ONAL_FAV_COUNT,
    RecapFeatures.B D RECT ONAL_REPLY_COUNT,
    RecapFeatures.UN D RECT ONAL_REPLY_COUNT,
    RecapFeatures.B D RECT ONAL_RETWEET_COUNT,
    RecapFeatures.UN D RECT ONAL_RETWEET_COUNT,
    RecapFeatures.EMBEDS_URL_COUNT,
    RecapFeatures.EMBEDS_ MPRESS ON_COUNT,
    RecapFeatures.V DEO_V EW_COUNT,
    RecapFeatures. S_RETWEET,
    RecapFeatures. S_REPLY,
    RecapFeatures.HAS_L NK,
    RecapFeatures.HAS_TREND,
    RecapFeatures.L NK_LANGUAGE,
    RecapFeatures.NUM_HASHTAGS,
    RecapFeatures.NUM_MENT ONS,
    RecapFeatures. S_SENS T VE,
    RecapFeatures.HAS_MULT PLE_MED A,
    RecapFeatures.FAV_COUNT_V2,
    RecapFeatures.RETWEET_COUNT_V2,
    RecapFeatures.REPLY_COUNT_V2,
    RecapFeatures.L NK_COUNT,
    Engage ntDataRecordFeatures. nNetworkFavor esCount,
    Engage ntDataRecordFeatures. nNetworkRet etsCount,
    Engage ntDataRecordFeatures. nNetworkRepl esCount
  )

  val UserAuthorFeaturesV2Count: Set[Feature[_]] = Set(
    RecapFeatures.HAS_ MAGE,
    RecapFeatures.HAS_V NE,
    RecapFeatures.HAS_PER SCOPE,
    RecapFeatures.HAS_PRO_V DEO,
    RecapFeatures.HAS_V DEO,
    RecapFeatures.HAS_CARD,
    RecapFeatures.HAS_NEWS,
    RecapFeatures.HAS_V S BLE_L NK,
    RecapFeatures.FAV_COUNT,
    RecapFeatures.CONTA NS_MED A,
    RecapFeatures.RETWEET_SEARCHER,
    RecapFeatures.REPLY_SEARCHER,
    RecapFeatures.MENT ON_SEARCHER,
    RecapFeatures.REPLY_OTHER,
    RecapFeatures.RETWEET_OTHER,
    RecapFeatures.MATCH_U _LANG,
    RecapFeatures.MATCH_SEARCHER_MA N_LANG,
    RecapFeatures.MATCH_SEARCHER_LANGS,
    RecapFeatures. S_RETWEET,
    RecapFeatures. S_REPLY,
    RecapFeatures.HAS_L NK,
    RecapFeatures.HAS_TREND,
    RecapFeatures. S_SENS T VE,
    RecapFeatures.HAS_MULT PLE_MED A,
    Engage ntDataRecordFeatures. nNetworkFavor esCount
  )

  val UserTop cFeaturesV2Count: Set[Feature[_]] = Set(
    RecapFeatures.HAS_ MAGE,
    RecapFeatures.HAS_V DEO,
    RecapFeatures.HAS_CARD,
    RecapFeatures.HAS_NEWS,
    RecapFeatures.FAV_COUNT,
    RecapFeatures.CONTA NS_MED A,
    RecapFeatures.RETWEET_SEARCHER,
    RecapFeatures.REPLY_SEARCHER,
    RecapFeatures.MENT ON_SEARCHER,
    RecapFeatures.REPLY_OTHER,
    RecapFeatures.RETWEET_OTHER,
    RecapFeatures.MATCH_U _LANG,
    RecapFeatures.MATCH_SEARCHER_MA N_LANG,
    RecapFeatures.MATCH_SEARCHER_LANGS,
    RecapFeatures. S_RETWEET,
    RecapFeatures. S_REPLY,
    RecapFeatures.HAS_L NK,
    RecapFeatures.HAS_TREND,
    RecapFeatures. S_SENS T VE,
    Engage ntDataRecordFeatures. nNetworkFavor esCount,
    Engage ntDataRecordFeatures. nNetworkRet etsCount,
    T  l nesSharedFeatures.NUM_CAPS,
    T  l nesSharedFeatures.ASPECT_RAT O_DEN,
    T  l nesSharedFeatures.NUM_NEWL NES,
    T  l nesSharedFeatures. S_360,
    T  l nesSharedFeatures. S_MANAGED,
    T  l nesSharedFeatures. S_MONET ZABLE,
    T  l nesSharedFeatures.HAS_SELECTED_PREV EW_ MAGE,
    T  l nesSharedFeatures.HAS_T TLE,
    T  l nesSharedFeatures.HAS_DESCR PT ON,
    T  l nesSharedFeatures.HAS_V S T_S TE_CALL_TO_ACT ON,
    T  l nesSharedFeatures.HAS_WATCH_NOW_CALL_TO_ACT ON
  )

  val UserFeaturesV5Cont nuous: Set[Feature[_]] = Set(
    T  l nesSharedFeatures.QUOTE_COUNT,
    T  l nesSharedFeatures.V S BLE_TOKEN_RAT O,
    T  l nesSharedFeatures.WE GHTED_FAV_COUNT,
    T  l nesSharedFeatures.WE GHTED_RETWEET_COUNT,
    T  l nesSharedFeatures.WE GHTED_REPLY_COUNT,
    T  l nesSharedFeatures.WE GHTED_QUOTE_COUNT,
    T  l nesSharedFeatures.EMBEDS_ MPRESS ON_COUNT_V2,
    T  l nesSharedFeatures.EMBEDS_URL_COUNT_V2,
    T  l nesSharedFeatures.DECAYED_FAVOR TE_COUNT,
    T  l nesSharedFeatures.DECAYED_RETWEET_COUNT,
    T  l nesSharedFeatures.DECAYED_REPLY_COUNT,
    T  l nesSharedFeatures.DECAYED_QUOTE_COUNT,
    T  l nesSharedFeatures.FAKE_FAVOR TE_COUNT,
    T  l nesSharedFeatures.FAKE_RETWEET_COUNT,
    T  l nesSharedFeatures.FAKE_REPLY_COUNT,
    T  l nesSharedFeatures.FAKE_QUOTE_COUNT,
    T  DataRecordFeatures.LAST_FAVOR TE_S NCE_CREAT ON_HRS,
    T  DataRecordFeatures.LAST_RETWEET_S NCE_CREAT ON_HRS,
    T  DataRecordFeatures.LAST_REPLY_S NCE_CREAT ON_HRS,
    T  DataRecordFeatures.LAST_QUOTE_S NCE_CREAT ON_HRS,
    T  DataRecordFeatures.T ME_S NCE_LAST_FAVOR TE_HRS,
    T  DataRecordFeatures.T ME_S NCE_LAST_RETWEET_HRS,
    T  DataRecordFeatures.T ME_S NCE_LAST_REPLY_HRS,
    T  DataRecordFeatures.T ME_S NCE_LAST_QUOTE_HRS
  )

  val UserFeaturesV5Boolean: Set[Feature[_]] = Set(
    T  l nesSharedFeatures.LABEL_ABUS VE_FLAG,
    T  l nesSharedFeatures.LABEL_ABUS VE_H _RCL_FLAG,
    T  l nesSharedFeatures.LABEL_DUP_CONTENT_FLAG,
    T  l nesSharedFeatures.LABEL_NSFW_H _PRC_FLAG,
    T  l nesSharedFeatures.LABEL_NSFW_H _RCL_FLAG,
    T  l nesSharedFeatures.LABEL_SPAM_FLAG,
    T  l nesSharedFeatures.LABEL_SPAM_H _RCL_FLAG,
    T  l nesSharedFeatures.PER SCOPE_EX STS,
    T  l nesSharedFeatures.PER SCOPE_ S_L VE,
    T  l nesSharedFeatures.PER SCOPE_HAS_BEEN_FEATURED,
    T  l nesSharedFeatures.PER SCOPE_ S_CURRENTLY_FEATURED,
    T  l nesSharedFeatures.PER SCOPE_ S_FROM_QUAL TY_SOURCE,
    T  l nesSharedFeatures.HAS_QUOTE
  )

  val UserAuthorFeaturesV5: Set[Feature[_]] = Set(
    T  l nesSharedFeatures.HAS_QUOTE,
    T  l nesSharedFeatures.LABEL_ABUS VE_FLAG,
    T  l nesSharedFeatures.LABEL_ABUS VE_H _RCL_FLAG,
    T  l nesSharedFeatures.LABEL_DUP_CONTENT_FLAG,
    T  l nesSharedFeatures.LABEL_NSFW_H _PRC_FLAG,
    T  l nesSharedFeatures.LABEL_NSFW_H _RCL_FLAG,
    T  l nesSharedFeatures.LABEL_SPAM_FLAG,
    T  l nesSharedFeatures.LABEL_SPAM_H _RCL_FLAG
  )

  val UserT etS ceFeaturesV1Cont nuous: Set[Feature[_]] = Set(
    T  l nesSharedFeatures.NUM_CAPS,
    T  l nesSharedFeatures.NUM_WH TESPACES,
    T  l nesSharedFeatures.TWEET_LENGTH,
    T  l nesSharedFeatures.ASPECT_RAT O_DEN,
    T  l nesSharedFeatures.ASPECT_RAT O_NUM,
    T  l nesSharedFeatures.B T_RATE,
    T  l nesSharedFeatures.HE GHT_1,
    T  l nesSharedFeatures.HE GHT_2,
    T  l nesSharedFeatures.HE GHT_3,
    T  l nesSharedFeatures.HE GHT_4,
    T  l nesSharedFeatures.V DEO_DURAT ON,
    T  l nesSharedFeatures.W DTH_1,
    T  l nesSharedFeatures.W DTH_2,
    T  l nesSharedFeatures.W DTH_3,
    T  l nesSharedFeatures.W DTH_4,
    T  l nesSharedFeatures.NUM_MED A_TAGS
  )

  val UserT etS ceFeaturesV1Boolean: Set[Feature[_]] = Set(
    T  l nesSharedFeatures.HAS_QUEST ON,
    T  l nesSharedFeatures.RES ZE_METHOD_1,
    T  l nesSharedFeatures.RES ZE_METHOD_2,
    T  l nesSharedFeatures.RES ZE_METHOD_3,
    T  l nesSharedFeatures.RES ZE_METHOD_4
  )

  val UserT etS ceFeaturesV2Cont nuous: Set[Feature[_]] = Set(
    T  l nesSharedFeatures.NUM_EMOJ S,
    T  l nesSharedFeatures.NUM_EMOT CONS,
    T  l nesSharedFeatures.NUM_NEWL NES,
    T  l nesSharedFeatures.NUM_ST CKERS,
    T  l nesSharedFeatures.NUM_FACES,
    T  l nesSharedFeatures.NUM_COLOR_PALLETTE_ TEMS,
    T  l nesSharedFeatures.V EW_COUNT,
    T  l nesSharedFeatures.TWEET_LENGTH_TYPE
  )

  val UserT etS ceFeaturesV2Boolean: Set[Feature[_]] = Set(
    T  l nesSharedFeatures. S_360,
    T  l nesSharedFeatures. S_MANAGED,
    T  l nesSharedFeatures. S_MONET ZABLE,
    T  l nesSharedFeatures. S_EMBEDDABLE,
    T  l nesSharedFeatures.HAS_SELECTED_PREV EW_ MAGE,
    T  l nesSharedFeatures.HAS_T TLE,
    T  l nesSharedFeatures.HAS_DESCR PT ON,
    T  l nesSharedFeatures.HAS_V S T_S TE_CALL_TO_ACT ON,
    T  l nesSharedFeatures.HAS_WATCH_NOW_CALL_TO_ACT ON
  )

  val UserAuthorT etS ceFeaturesV1: Set[Feature[_]] = Set(
    T  l nesSharedFeatures.HAS_QUEST ON,
    T  l nesSharedFeatures.TWEET_LENGTH,
    T  l nesSharedFeatures.V DEO_DURAT ON,
    T  l nesSharedFeatures.NUM_MED A_TAGS
  )

  val UserAuthorT etS ceFeaturesV2: Set[Feature[_]] = Set(
    T  l nesSharedFeatures.NUM_CAPS,
    T  l nesSharedFeatures.NUM_WH TESPACES,
    T  l nesSharedFeatures.ASPECT_RAT O_DEN,
    T  l nesSharedFeatures.ASPECT_RAT O_NUM,
    T  l nesSharedFeatures.B T_RATE,
    T  l nesSharedFeatures.TWEET_LENGTH_TYPE,
    T  l nesSharedFeatures.NUM_EMOJ S,
    T  l nesSharedFeatures.NUM_EMOT CONS,
    T  l nesSharedFeatures.NUM_NEWL NES,
    T  l nesSharedFeatures.NUM_ST CKERS,
    T  l nesSharedFeatures.NUM_FACES,
    T  l nesSharedFeatures. S_360,
    T  l nesSharedFeatures. S_MANAGED,
    T  l nesSharedFeatures. S_MONET ZABLE,
    T  l nesSharedFeatures.HAS_SELECTED_PREV EW_ MAGE,
    T  l nesSharedFeatures.HAS_T TLE,
    T  l nesSharedFeatures.HAS_DESCR PT ON,
    T  l nesSharedFeatures.HAS_V S T_S TE_CALL_TO_ACT ON,
    T  l nesSharedFeatures.HAS_WATCH_NOW_CALL_TO_ACT ON
  )

  val UserAuthorT etS ceFeaturesV2Count: Set[Feature[_]] = Set(
    T  l nesSharedFeatures.NUM_CAPS,
    T  l nesSharedFeatures.ASPECT_RAT O_DEN,
    T  l nesSharedFeatures.NUM_NEWL NES,
    T  l nesSharedFeatures. S_360,
    T  l nesSharedFeatures. S_MANAGED,
    T  l nesSharedFeatures. S_MONET ZABLE,
    T  l nesSharedFeatures.HAS_SELECTED_PREV EW_ MAGE,
    T  l nesSharedFeatures.HAS_T TLE,
    T  l nesSharedFeatures.HAS_DESCR PT ON,
    T  l nesSharedFeatures.HAS_V S T_S TE_CALL_TO_ACT ON,
    T  l nesSharedFeatures.HAS_WATCH_NOW_CALL_TO_ACT ON
  )

  val LabelsV2: Set[Feature.B nary] = RecapLabelsForAggregat on ++ Set(
    RecapFeatures. S_REPL ED,
    RecapFeatures. S_PHOTO_EXPANDED,
    RecapFeatures. S_V DEO_PLAYBACK_50
  )

  val Tw terW deFeatures: Set[Feature[_]] = Set(
    RecapFeatures. S_REPLY,
    T  l nesSharedFeatures.HAS_QUOTE,
    RecapFeatures.HAS_MENT ON,
    RecapFeatures.HAS_HASHTAG,
    RecapFeatures.HAS_L NK,
    RecapFeatures.HAS_CARD,
    RecapFeatures.CONTA NS_MED A
  )

  val Tw terW deLabels: Set[Feature.B nary] = Set(
    RecapFeatures. S_FAVOR TED,
    RecapFeatures. S_RETWEETED,
    RecapFeatures. S_REPL ED
  )

  val Rec procalLabels: Set[Feature.B nary] = Set(
    RecapFeatures. S_REPL ED_REPLY_ MPRESSED_BY_AUTHOR,
    RecapFeatures. S_REPL ED_REPLY_REPL ED_BY_AUTHOR,
    RecapFeatures. S_REPL ED_REPLY_FAVOR TED_BY_AUTHOR
  )

  val Negat veEngage ntLabels: Set[Feature.B nary] = Set(
    RecapFeatures. S_REPORT_TWEET_CL CKED,
    RecapFeatures. S_BLOCK_CL CKED,
    RecapFeatures. S_MUTE_CL CKED,
    RecapFeatures. S_DONT_L KE
  )

  val GoodCl ckLabels: Set[Feature.B nary] = Set(
    RecapFeatures. S_GOOD_CL CKED_CONVO_DESC_V1,
    RecapFeatures. S_GOOD_CL CKED_CONVO_DESC_V2,
  )
}
