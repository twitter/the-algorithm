package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on

 mport com.tw ter.t  l nes.conf gap .FSEnumParam
 mport com.tw ter.t  l nes.conf gap .FSParam

object RecentEngage ntS m larUsersParams {

  case object F rstDegreeSortEnabled
      extends FSParam[Boolean](
        na  = "s ms_expans on_recent_engage nt_f rst_degree_sort",
        default = true)
  case object Aggregator
      extends FSEnumParam[S msExpans onS ceAggregator d.type](
        na  = "s ms_expans on_recent_engage nt_aggregator_ d",
        default = S msExpans onS ceAggregator d.Sum,
        enum = S msExpans onS ceAggregator d)
}
