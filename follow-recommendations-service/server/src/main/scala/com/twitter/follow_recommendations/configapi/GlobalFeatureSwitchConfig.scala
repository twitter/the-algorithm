package com.ExTwitter.follow_recommendations.configapi

import com.ExTwitter.follow_recommendations.common.candidate_sources.crowd_search_accounts.CrowdSearchAccountsParams.AccountsFilteringAndRankingLogics
import com.ExTwitter.follow_recommendations.common.candidate_sources.top_organic_follows_accounts.TopOrganicFollowsAccountsParams.{
  AccountsFilteringAndRankingLogics => OrganicAccountsFilteringAndRankingLogics
}
import com.ExTwitter.follow_recommendations.common.candidate_sources.sims_expansion.RecentEngagementSimilarUsersParams
import com.ExTwitter.follow_recommendations.common.candidate_sources.sims_expansion.SimsExpansionSourceParams
import com.ExTwitter.follow_recommendations.common.rankers.ml_ranker.ranking.MlRankerParams.CandidateScorerIdParam
import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.follow_recommendations.configapi.params.GlobalParams.CandidateSourcesToFilter
import com.ExTwitter.follow_recommendations.configapi.params.GlobalParams.EnableCandidateParamHydrations
import com.ExTwitter.follow_recommendations.configapi.params.GlobalParams.EnableGFSSocialProofTransform
import com.ExTwitter.follow_recommendations.configapi.params.GlobalParams.EnableRecommendationFlowLogs
import com.ExTwitter.follow_recommendations.configapi.params.GlobalParams.EnableWhoToFollowProducts
import com.ExTwitter.follow_recommendations.configapi.params.GlobalParams.KeepSocialUserCandidate
import com.ExTwitter.follow_recommendations.configapi.params.GlobalParams.KeepUserCandidate
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.Param
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalFeatureSwitchConfig @Inject() () extends FeatureSwitchConfig {
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
      SimsExpansionSourceParams.Aggregator,
      RecentEngagementSimilarUsersParams.Aggregator,
      CandidateSourcesToFilter,
    )

  val enumSeqFsParams =
    Seq(
      AccountsFilteringAndRankingLogics,
      OrganicAccountsFilteringAndRankingLogics
    )
}
