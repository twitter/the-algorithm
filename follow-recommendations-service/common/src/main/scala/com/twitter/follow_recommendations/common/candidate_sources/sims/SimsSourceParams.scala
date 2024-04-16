package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms

 mport com.tw ter.t  l nes.conf gap .FSParam

object S msS ceParams {
  case object EnableDBV2S msStore extends FSParam[Boolean]("s ms_s ce_enable_dbv2_s ce", false)

  case object EnableDBV2S msRefreshStore
      extends FSParam[Boolean]("s ms_s ce_enable_dbv2_refresh_s ce", false)

  case object EnableExper  ntalS msStore
      extends FSParam[Boolean]("s ms_s ce_enable_exper  ntal_s ce", false)

  case object D sable avyRanker
      extends FSParam[Boolean]("s ms_s ce_d sable_ avy_ranker", default = false)
}
