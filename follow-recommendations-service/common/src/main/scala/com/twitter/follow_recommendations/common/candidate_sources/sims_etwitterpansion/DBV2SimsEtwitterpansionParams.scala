package com.twitter.follow_recommendations.common.candidate_sources.sims_etwitterpansion

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object DBV2SimsEtwitterpansionParams {
  // Theses divisors are used to calibrate DBv2Sims etwittertension candidates scores
  case object RecentFollowingSimilarUsersDBV2CalibrateDivisor
      etwittertends FSBoundedParam[Double](
        "sims_etwitterpansion_recent_following_similar_users_dbv2_divisor",
        default = 1.0d,
        min = 0.1d,
        matwitter = 100d)
  case object RecentEngagementSimilarUsersDBV2CalibrateDivisor
      etwittertends FSBoundedParam[Double](
        "sims_etwitterpansion_recent_engagement_similar_users_dbv2_divisor",
        default = 1.0d,
        min = 0.1d,
        matwitter = 100d)
  case object DisableHeavyRanker
      etwittertends FSParam[Boolean]("sims_etwitterpansion_disable_heavy_ranker", default = false)
}
