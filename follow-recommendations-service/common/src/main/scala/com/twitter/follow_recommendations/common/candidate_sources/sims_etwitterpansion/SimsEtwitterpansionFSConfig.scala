package com.twitter.follow_recommendations.common.candidate_sources.sims_etwitterpansion

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class SimsEtwitterpansionFSConfig @Inject() () etwittertends FeatureSwitchConfig {
  override val intFSParams: Seq[FSBoundedParam[Int]] = Seq(
    RecentFollowingSimilarUsersParams.MatwitterFirstDegreeNodes,
    RecentFollowingSimilarUsersParams.MatwitterSecondaryDegreeEtwitterpansionPerNode,
    RecentFollowingSimilarUsersParams.MatwitterResults
  )

  override val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    DBV2SimsEtwitterpansionParams.RecentFollowingSimilarUsersDBV2CalibrateDivisor,
    DBV2SimsEtwitterpansionParams.RecentEngagementSimilarUsersDBV2CalibrateDivisor
  )

  override val booleanFSParams: Seq[FSParam[Boolean]] = Seq(
    DBV2SimsEtwitterpansionParams.DisableHeavyRanker,
    RecentFollowingSimilarUsersParams.TimestampIntegrated
  )
}
