package com.X.follow_recommendations.common.candidate_sources.sims

import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimsSourceFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean] with FSName] = Seq(
    SimsSourceParams.DisableHeavyRanker
  )
}
