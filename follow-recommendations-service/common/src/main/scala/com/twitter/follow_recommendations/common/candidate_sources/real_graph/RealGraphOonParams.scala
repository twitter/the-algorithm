package com.twitter.follow_recommendations.common.candidate_sources.real_graph

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object RealGraphOonParams {
  case object IncludeRealGraphOonCandidates
      extends FSParam[Boolean](
        "real_graph_oon_include_candidates",
        false
      )
  case object TryToReadRealGraphOonCandidates
      extends FSParam[Boolean](
        "real_graph_oon_try_to_read_candidates",
        false
      )
  case object RealGraphOonResultCountThreshold
      extends FSBoundedParam[Int](
        "real_graph_oon_result_count_threshold",
        default = 1,
        min = 0,
        max = Integer.MAX_VALUE
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
        min = 0,
        max = 1.0
      )

  case object MaxResults
      extends FSBoundedParam[Int](
        "real_graph_oon_max_results",
        default = 200,
        min = 0,
        max = 1000
      )

}
