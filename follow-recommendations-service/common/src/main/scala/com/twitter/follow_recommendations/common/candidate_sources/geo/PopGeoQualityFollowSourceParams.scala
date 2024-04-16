package com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

object PopGeoQual yFollowS ceParams {
  case object Cand dateS ceEnabled
      extends FSParam[Boolean]("pop_geo_qual y_follow_s ce_enabled", false)

  case object PopGeoS ceGeoHashM nPrec s on
      extends FSBoundedParam[ nt](
        "pop_geo_qual y_follow_s ce_geo_hash_m n_prec s on",
        default = 2,
        m n = 0,
        max = 10)

  case object PopGeoS ceGeoHashMaxPrec s on
      extends FSBoundedParam[ nt](
        "pop_geo_qual y_follow_s ce_geo_hash_max_prec s on",
        default = 3,
        m n = 0,
        max = 10)

  case object PopGeoS ceReturnFromAllPrec s ons
      extends FSParam[Boolean](
        "pop_geo_qual y_follow_s ce_return_from_all_prec s ons",
        default = false)

  case object PopGeoS ceMaxResultsPerPrec s on
      extends FSBoundedParam[ nt](
        "pop_geo_qual y_follow_s ce_max_results_per_prec s on",
        default = 200,
        m n = 0,
        max = 1000)

  case object Cand dateS ce  ght
      extends FSBoundedParam[Double](
        "pop_geo_qual y_follow_s ce_  ght",
        default = 200,
        m n = 0.001,
        max = 2000)
}
