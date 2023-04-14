try {
package com.twitter.follow_recommendations.common.candidate_sources.stp

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineStpSourceFsConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean] with FSName] = Seq(
    OfflineStpSourceParams.UseDenserPmiMatrix
  )
}

} catch {
  case e: Exception =>
}
