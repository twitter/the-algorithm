package com.twitter.follow_recommendations.configapi

import com.twitter.follow_recommendations.common.candidate_sources.crowd_search_accounts.CrowdSearchAccountsParams.AccountsFilteringAndRankingLogics
import com.twitter.follow_recommendations.common.candidate_sources.top_organic_follows_accounts.TopOrganicFollowsAccountsParams.{
  AccountsFilteringAndRankingLogics => OrganicAccountsFilteringAndRankingLogics
}
import com.twitter.follow_recommendations.common.candidate_sources.sims_etwitterpansion.RecentEngagementSimilarUsersParams
import com.twitter.follow_recommendations.common.candidate_sources.sims_etwitterpansion.SimsEtwitterpansionSourceParams
import com.twitter.follow_recommendations.common.rankers.ml_ranker.ranking.MlRankerParams.CandidateScorerIdParam
import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.follow_recommendations.configapi.params.GlobalParams.CandidateSourcesToFilter
import com.twitter.follow_recommendations.configapi.params.GlobalParams.EnableCandidateParamHydrations
import com.twitter.follow_recommendations.configapi.params.GlobalParams.EnableGFSSocialProofTransform
import com.twitter.follow_recommendations.configapi.params.GlobalParams.EnableRecommendationFlowLogs
import com.twitter.follow_recommendations.configapi.params.GlobalParams.EnableWhoToFollowProducts
import com.twitter.follow_recommendations.configapi.params.GlobalParams.KeepSocialUserCandidate
import com.twitter.follow_recommendations.configapi.params.GlobalParams.KeepUserCandidate
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.Param
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class GlobalFeatureSwitchConfig @Inject() () etwittertends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] = {
    Seq(
      EnableCandidateParamHydrations,
      KeepUserCandidate,
      KeepSocialUserCandidate,
      EnableGFSSocialProofTransform,
      EnableWhoToFollowProducts,
      EnableRecommendationFlowLogs
    )
  }

  val enumFsParams =
    Seq(
      CandidateScorerIdParam,
      SimsEtwitterpansionSourceParams.Aggregator,
      RecentEngagementSimilarUsersParams.Aggregator,
      CandidateSourcesToFilter,
    )

  val enumSeqFsParams =
    Seq(
      AccountsFilteringAndRankingLogics,
      OrganicAccountsFilteringAndRankingLogics
    )
}
