package com.twitter.follow_recommendations.common.candidate_sources.user_user_graph

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.Param
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class UserUserGraphFSConfig @Inject() () etwittertends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] = Seq(
    UserUserGraphParams.UserUserGraphCandidateSourceEnabledInWeightMap,
    UserUserGraphParams.UserUserGraphCandidateSourceEnabledInTransform
  )
}
