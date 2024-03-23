package com.ExTwitter.follow_recommendations.common.candidate_sources.stp

import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.FSParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineStpSourceFsConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean] with FSName] = Seq(
    OfflineStpSourceParams.UseDenserPmiMatrix
  )
}
