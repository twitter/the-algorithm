package com.tw ter.t  l nes.data_process ng.ad_hoc.earlyb rd_rank ng.common

 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.t  l nes.pred ct on.features.recap.RecapFeatures

class Earlyb rdTra n ngRecapConf gurat on extends Earlyb rdTra n ngConf gurat on {
  overr de val labels: Map[Str ng, Feature.B nary] = Map(
    "deta l_expanded" -> RecapFeatures. S_CL CKED,
    "favor ed" -> RecapFeatures. S_FAVOR TED,
    "open_l nked" -> RecapFeatures. S_OPEN_L NKED,
    "photo_expanded" -> RecapFeatures. S_PHOTO_EXPANDED,
    "prof le_cl cked" -> RecapFeatures. S_PROF LE_CL CKED,
    "repl ed" -> RecapFeatures. S_REPL ED,
    "ret eted" -> RecapFeatures. S_RETWEETED,
    "v deo_playback50" -> RecapFeatures. S_V DEO_PLAYBACK_50
  )
}
