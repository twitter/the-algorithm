package com.twitter.follow_recommendations.common.candidate_sources.sims_etwitterpansion

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSParam

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RecentEngagementSimilarUsersFSConfig @Inject() () etwittertends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean]] = Seq(
    RecentEngagementSimilarUsersParams.FirstDegreeSortEnabled
  )
}
