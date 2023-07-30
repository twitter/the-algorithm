package com.X.follow_recommendations.common.candidate_sources.sims_expansion

import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimsExpansionFSConfig @Inject() () extends FeatureSwitchConfig {
  override val intFSParams: Seq[FSBoundedParam[Int]] = Seq(
    RecentFollowingSimilarUsersParams.MaxFirstDegreeNodes,
    RecentFollowingSimilarUsersParams.MaxSecondaryDegreeExpansionPerNode,
    RecentFollowingSimilarUsersParams.MaxResults
  )

  override val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    DBV2SimsExpansionParams.RecentFollowingSimilarUsersDBV2CalibrateDivisor,
    DBV2SimsExpansionParams.RecentEngagementSimilarUsersDBV2CalibrateDivisor
  )

  override val booleanFSParams: Seq[FSParam[Boolean]] = Seq(
    DBV2SimsExpansionParams.DisableHeavyRanker,
    RecentFollowingSimilarUsersParams.TimestampIntegrated
  )
}
