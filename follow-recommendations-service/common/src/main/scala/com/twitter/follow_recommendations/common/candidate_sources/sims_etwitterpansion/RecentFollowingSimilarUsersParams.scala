package com.twitter.follow_recommendations.common.candidate_sources.sims_etwitterpansion

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object RecentFollowingSimilarUsersParams {
  case object MatwitterFirstDegreeNodes
      etwittertends FSBoundedParam[Int](
        name = "sims_etwitterpansion_recent_following_matwitter_first_degree_nodes",
        default = 10,
        min = 0,
        matwitter = 200)
  case object MatwitterSecondaryDegreeEtwitterpansionPerNode
      etwittertends FSBoundedParam[Int](
        name = "sims_etwitterpansion_recent_following_matwitter_secondary_degree_nodes",
        default = 40,
        min = 0,
        matwitter = 200)
  case object MatwitterResults
      etwittertends FSBoundedParam[Int](
        name = "sims_etwitterpansion_recent_following_matwitter_results",
        default = 200,
        min = 0,
        matwitter = 200)
  case object TimestampIntegrated
      etwittertends FSParam[Boolean](
        name = "sims_etwitterpansion_recent_following_integ_timestamp",
        default = false)
}
