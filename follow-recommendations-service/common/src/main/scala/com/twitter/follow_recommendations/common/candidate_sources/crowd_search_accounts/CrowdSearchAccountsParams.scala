package com.twitter.follow_recommendations.common.candidate_sources.crowd_search_accounts

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumSeqParam
import com.twitter.timelines.configapi.FSParam

object CrowdSearchAccountsParams {
  // whether or not to fetch CrowdSearchAccounts candidate sources
  case object CandidateSourceEnabled
      extends FSParam[Boolean]("crowd_search_accounts_candidate_source_enabled", false)

  /**
   *   Contains the logic key for account filtering and ranking. Currently we have 3 main logic keys
   *    - new_daily: filtering top searched accounts with max daily searches based on new users
   *    - new_weekly: filtering top searched accounts with max weekly searches based on new users
   *    - daily: filtering top searched accounts with max daily searches
   *    - weekly: filtering top searched accounts with max weekly searches
   *    Mapping of the Logic Id to Logic key is done via @enum AccountsFilteringAndRankingLogic
   */
  case object AccountsFilteringAndRankingLogics
      extends FSEnumSeqParam[AccountsFilteringAndRankingLogicId.type](
        name = "crowd_search_accounts_filtering_and_ranking_logic_ids",
        default = Seq(AccountsFilteringAndRankingLogicId.SearchesWeekly),
        enum = AccountsFilteringAndRankingLogicId)

  case object CandidateSourceWeight
      extends FSBoundedParam[Double](
        "crowd_search_accounts_candidate_source_weight",
        default = 1200,
        min = 0.001,
        max = 2000)
}
