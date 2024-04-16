package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

object RecentFollow ngS m larUsersParams {
  case object MaxF rstDegreeNodes
      extends FSBoundedParam[ nt](
        na  = "s ms_expans on_recent_follow ng_max_f rst_degree_nodes",
        default = 10,
        m n = 0,
        max = 200)
  case object MaxSecondaryDegreeExpans onPerNode
      extends FSBoundedParam[ nt](
        na  = "s ms_expans on_recent_follow ng_max_secondary_degree_nodes",
        default = 40,
        m n = 0,
        max = 200)
  case object MaxResults
      extends FSBoundedParam[ nt](
        na  = "s ms_expans on_recent_follow ng_max_results",
        default = 200,
        m n = 0,
        max = 200)
  case object T  stamp ntegrated
      extends FSParam[Boolean](
        na  = "s ms_expans on_recent_follow ng_ nteg_t  stamp",
        default = false)
}
