package com.twitter.follow_recommendations.common.candidate_sources.real_graph

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object RealGraphOonParams {
  case object IncludeRealGraphOonCandidates
      etwittertends FSParam[Boolean](
        "real_graph_oon_include_candidates",
        false
      )
  case object TryToReadRealGraphOonCandidates
      etwittertends FSParam[Boolean](
        "real_graph_oon_try_to_read_candidates",
        false
      )
  case object RealGraphOonResultCountThreshold
      etwittertends FSBoundedParam[Int](
        "real_graph_oon_result_count_threshold",
        default = 1,
        min = 0,
        matwitter = Integer.MAtwitter_VALUE
      )

  case object UseV2
      etwittertends FSParam[Boolean](
        "real_graph_oon_use_v2",
        false
      )

  case object ScoreThreshold
      etwittertends FSBoundedParam[Double](
        "real_graph_oon_score_threshold",
        default = 0.26,
        min = 0,
        matwitter = 1.0
      )

  case object MatwitterResults
      etwittertends FSBoundedParam[Int](
        "real_graph_oon_matwitter_results",
        default = 200,
        min = 0,
        matwitter = 1000
      )

}
