package com.twitter.follow_recommendations.common.candidate_sources.sims_expansion

import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam

object RecentEngagementSimilarUsersParams {

  case object FirstDegreeSortEnabled
      extends FSParam[Boolean](
        name = "sims_expansion_recent_engagement_first_degree_sort",
        default = true)
  case object Aggregator
      extends FSEnumParam[SimsExpansionSourceAggregatorId.type](
        name = "sims_expansion_recent_engagement_aggregator_id",
        default = SimsExpansionSourceAggregatorId.Sum,
        enum = SimsExpansionSourceAggregatorId)
}
