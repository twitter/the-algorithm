package com.ExTwitter.follow_recommendations.common.candidate_sources.top_organic_follows_accounts

import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.Param
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopOrganicFollowsAccountsFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] = Seq(
    TopOrganicFollowsAccountsParams.CandidateSourceEnabled,
  )
  override val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    TopOrganicFollowsAccountsParams.CandidateSourceWeight,
  )
}
