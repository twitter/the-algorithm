package com.tw ter.follow_recom ndat ons.common.cand date_s ces.real_graph

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

object RealGraphOonParams {
  case object  ncludeRealGraphOonCand dates
      extends FSParam[Boolean](
        "real_graph_oon_ nclude_cand dates",
        false
      )
  case object TryToReadRealGraphOonCand dates
      extends FSParam[Boolean](
        "real_graph_oon_try_to_read_cand dates",
        false
      )
  case object RealGraphOonResultCountThreshold
      extends FSBoundedParam[ nt](
        "real_graph_oon_result_count_threshold",
        default = 1,
        m n = 0,
        max =  nteger.MAX_VALUE
      )

  case object UseV2
      extends FSParam[Boolean](
        "real_graph_oon_use_v2",
        false
      )

  case object ScoreThreshold
      extends FSBoundedParam[Double](
        "real_graph_oon_score_threshold",
        default = 0.26,
        m n = 0,
        max = 1.0
      )

  case object MaxResults
      extends FSBoundedParam[ nt](
        "real_graph_oon_max_results",
        default = 200,
        m n = 0,
        max = 1000
      )

}
