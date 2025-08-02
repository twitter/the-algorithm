package com.twitter.follow_recommendations.common.candidate_sources.stp

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class OfflineStpSourceFsConfig @Inject() () etwittertends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean] with FSName] = Seq(
    OfflineStpSourceParams.UseDenserPmiMatritwitter
  )
}
