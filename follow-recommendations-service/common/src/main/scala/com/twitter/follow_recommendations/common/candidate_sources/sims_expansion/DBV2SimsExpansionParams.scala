package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

object DBV2S msExpans onParams {
  // T ses d v sors are used to cal brate DBv2S ms extens on cand dates scores
  case object RecentFollow ngS m larUsersDBV2Cal brateD v sor
      extends FSBoundedParam[Double](
        "s ms_expans on_recent_follow ng_s m lar_users_dbv2_d v sor",
        default = 1.0d,
        m n = 0.1d,
        max = 100d)
  case object RecentEngage ntS m larUsersDBV2Cal brateD v sor
      extends FSBoundedParam[Double](
        "s ms_expans on_recent_engage nt_s m lar_users_dbv2_d v sor",
        default = 1.0d,
        m n = 0.1d,
        max = 100d)
  case object D sable avyRanker
      extends FSParam[Boolean]("s ms_expans on_d sable_ avy_ranker", default = false)
}
