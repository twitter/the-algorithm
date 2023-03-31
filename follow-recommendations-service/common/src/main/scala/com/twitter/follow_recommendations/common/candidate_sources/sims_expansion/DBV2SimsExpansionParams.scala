package com.twitter.follow_recommendations.common.candidate_sources.sims_expansion

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object DBV2SimsExpansionParams {
  // Theses divisors are used to calibrate DBv2Sims extension candidates scores
  case object RecentFollowingSimilarUsersDBV2CalibrateDivisor
      extends FSBoundedParam[Double](
        "sims_expansion_recent_following_similar_users_dbv2_divisor",
        default = 1.0d,
        min = 0.1d,
        max = 100d)
  case object RecentEngagementSimilarUsersDBV2CalibrateDivisor
      extends FSBoundedParam[Double](
        "sims_expansion_recent_engagement_similar_users_dbv2_divisor",
        default = 1.0d,
        min = 0.1d,
        max = 100d)
  case object DisableHeavyRanker
      extends FSParam[Boolean]("sims_expansion_disable_heavy_ranker", default = false)
}
