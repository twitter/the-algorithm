package com.ExTwitter.follow_recommendations.common.candidate_sources.sims_expansion

import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.timelines.configapi.FSParam

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentEngagementSimilarUsersFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean]] = Seq(
    RecentEngagementSimilarUsersParams.FirstDegreeSortEnabled
  )
}
