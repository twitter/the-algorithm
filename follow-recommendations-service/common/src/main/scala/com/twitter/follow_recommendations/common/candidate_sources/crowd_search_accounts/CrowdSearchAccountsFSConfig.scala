package com.twitter.follow_recommendations.common.candidate_sources.crowd_search_accounts

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.Param
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CrowdSearchAccountsFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] = Seq(
    CrowdSearchAccountsParams.CandidateSourceEnabled,
  )
  override val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    CrowdSearchAccountsParams.CandidateSourceWeight,
  )
}
