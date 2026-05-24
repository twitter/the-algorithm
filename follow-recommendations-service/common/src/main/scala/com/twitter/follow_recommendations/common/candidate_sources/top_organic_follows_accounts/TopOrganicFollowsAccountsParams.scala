package com.twitter.follow_recommendations.common.candidate_sources.top_organic_follows_accounts

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumSeqParam
import com.twitter.timelines.configapi.FSParam

object TopOrganicFollowsAccountsParams {
  // whether or not to fetch TopOrganicFollowsAccounts candidate sources
  case object CandidateSourceEnabled
      extends FSParam[Boolean]("top_organic_follows_accounts_candidate_source_enabled", false)

  /**
   *   Contains the logic key for account filtering and ranking. Currently we have 3 main logic keys
   *    - new_organic_follows: filtering top organically followed accounts followed by new users
   *    - non_new_organic_follows: filtering top organically followed accounts followed by non new users
   *    - organic_follows: filtering top organically followed accounts followed by all users
   *    Mapping of the Logic Id to Logic key is done via @enum AccountsFilteringAndRankingLogic
   */
  case object AccountsFilteringAndRankingLogics
      extends FSEnumSeqParam[AccountsFilteringAndRankingLogicId.type](
        name = "top_organic_follows_accounts_filtering_and_ranking_logic_ids",
        default = Seq(AccountsFilteringAndRankingLogicId.OrganicFollows),
        enum = AccountsFilteringAndRankingLogicId)

  case object CandidateSourceWeight
      extends FSBoundedParam[Double](
        "top_organic_follows_accounts_candidate_source_weight",
        default = 1200,
        min = 0.001,
        max = 2000)
}
