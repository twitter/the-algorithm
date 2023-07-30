package com.X.follow_recommendations.common.candidate_sources.user_user_graph

import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.Param
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserUserGraphFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] = Seq(
    UserUserGraphParams.UserUserGraphCandidateSourceEnabledInWeightMap,
    UserUserGraphParams.UserUserGraphCandidateSourceEnabledInTransform
  )
}
