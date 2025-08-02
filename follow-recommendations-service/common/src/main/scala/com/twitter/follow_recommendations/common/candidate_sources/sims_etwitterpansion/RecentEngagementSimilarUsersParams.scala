package com.twitter.follow_recommendations.common.candidate_sources.sims_etwitterpansion

import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam

object RecentEngagementSimilarUsersParams {

  case object FirstDegreeSortEnabled
      etwittertends FSParam[Boolean](
        name = "sims_etwitterpansion_recent_engagement_first_degree_sort",
        default = true)
  case object Aggregator
      etwittertends FSEnumParam[SimsEtwitterpansionSourceAggregatorId.type](
        name = "sims_etwitterpansion_recent_engagement_aggregator_id",
        default = SimsEtwitterpansionSourceAggregatorId.Sum,
        enum = SimsEtwitterpansionSourceAggregatorId)
}
