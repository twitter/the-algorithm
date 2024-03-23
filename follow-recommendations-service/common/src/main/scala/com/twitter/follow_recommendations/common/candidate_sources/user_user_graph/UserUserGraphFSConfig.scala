package com.ExTwitter.follow_recommendations.common.candidate_sources.user_user_graph

import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.Param
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserUserGraphFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] = Seq(
    UserUserGraphParams.UserUserGraphCandidateSourceEnabledInWeightMap,
    UserUserGraphParams.UserUserGraphCandidateSourceEnabledInTransform
  )
}
