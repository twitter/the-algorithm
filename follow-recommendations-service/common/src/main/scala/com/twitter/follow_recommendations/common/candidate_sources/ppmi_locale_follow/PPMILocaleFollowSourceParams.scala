package com.tw ter.follow_recom ndat ons.common.cand date_s ces.ppm _locale_follow

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

class PPM LocaleFollowS ceParams {}
object PPM LocaleFollowS ceParams {
  case object LocaleToExcludeFromRecom ndat on
      extends FSParam[Seq[Str ng]](
        "ppm locale_follow_s ce_locales_to_exclude_from_recom ndat on",
        default = Seq.empty)

  case object Cand dateS ceEnabled
      extends FSParam[Boolean]("ppm locale_follow_s ce_enabled", true)

  case object Cand dateS ce  ght
      extends FSBoundedParam[Double](
        "ppm locale_follow_s ce_cand date_s ce_  ght",
        default = 1,
        m n = 0.001,
        max = 2000)
}
