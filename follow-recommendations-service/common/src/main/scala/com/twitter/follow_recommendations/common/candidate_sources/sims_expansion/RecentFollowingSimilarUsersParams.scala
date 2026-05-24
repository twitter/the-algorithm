package com.twitter.follow_recommendations.common.candidate_sources.sims_expansion

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object RecentFollowingSimilarUsersParams {
  case object MaxFirstDegreeNodes
      extends FSBoundedParam[Int](
        name = "sims_expansion_recent_following_max_first_degree_nodes",
        default = 10,
        min = 0,
        max = 200)
  case object MaxSecondaryDegreeExpansionPerNode
      extends FSBoundedParam[Int](
        name = "sims_expansion_recent_following_max_secondary_degree_nodes",
        default = 40,
        min = 0,
        max = 200)
  case object MaxResults
      extends FSBoundedParam[Int](
        name = "sims_expansion_recent_following_max_results",
        default = 200,
        min = 0,
        max = 200)
  case object TimestampIntegrated
      extends FSParam[Boolean](
        name = "sims_expansion_recent_following_integ_timestamp",
        default = false)
}
