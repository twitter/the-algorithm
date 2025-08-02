package com.twitter.follow_recommendations.common.candidate_sources.triangular_loops

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TriangularLoopsFSConfig @Inject() () etwittertends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean] with FSName] = Nil
}
