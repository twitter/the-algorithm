package com.tw ter.t  l nes.pred ct on.common.aggregates

 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.t  l nes.pred ct on.features.engage nt_features.Engage ntDataRecordFeatures
 mport com.tw ter.t  l nes.pred ct on.features. l. TLFeatures

object Rect etUserFeatureAggregat on {
  val Rect etLabelsForAggregat on: Set[Feature.B nary] =
    Set(
       TLFeatures. S_FAVOR TED,
       TLFeatures. S_RETWEETED,
       TLFeatures. S_REPL ED,
       TLFeatures. S_CL CKED,
       TLFeatures. S_PROF LE_CL CKED,
       TLFeatures. S_OPEN_L NKED,
       TLFeatures. S_PHOTO_EXPANDED,
       TLFeatures. S_V DEO_PLAYBACK_50
    )

  val T etFeatures: Set[Feature[_]] = Set(
     TLFeatures.HAS_ MAGE,
     TLFeatures.HAS_CARD,
     TLFeatures.HAS_NEWS,
     TLFeatures.REPLY_COUNT,
     TLFeatures.FAV_COUNT,
     TLFeatures.REPLY_COUNT,
     TLFeatures.RETWEET_COUNT,
     TLFeatures.MATCHES_U _LANG,
     TLFeatures.MATCHES_SEARCHER_MA N_LANG,
     TLFeatures.MATCHES_SEARCHER_LANGS,
     TLFeatures.TEXT_SCORE,
     TLFeatures.L NK_LANGUAGE,
     TLFeatures.NUM_HASHTAGS,
     TLFeatures.NUM_MENT ONS,
     TLFeatures. S_SENS T VE,
     TLFeatures.HAS_V DEO,
     TLFeatures.HAS_L NK,
     TLFeatures.HAS_V S BLE_L NK,
    Engage ntDataRecordFeatures. nNetworkFavor esCount
    // n ce to have, but currently not hydrated  n t  Recom ndedT et payload
    //Engage ntDataRecordFeatures. nNetworkRet etsCount,
    //Engage ntDataRecordFeatures. nNetworkRepl esCount
  )

  val Rec procalLabels: Set[Feature.B nary] = Set(
     TLFeatures. S_REPL ED_REPLY_ MPRESSED_BY_AUTHOR,
     TLFeatures. S_REPL ED_REPLY_REPL ED_BY_AUTHOR,
     TLFeatures. S_REPL ED_REPLY_FAVOR TED_BY_AUTHOR,
     TLFeatures. S_REPL ED_REPLY_RETWEETED_BY_AUTHOR,
     TLFeatures. S_REPL ED_REPLY_QUOTED_BY_AUTHOR
  )
}
