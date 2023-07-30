package com.X.follow_recommendations.common.candidate_sources.top_organic_follows_accounts

import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.Param
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
