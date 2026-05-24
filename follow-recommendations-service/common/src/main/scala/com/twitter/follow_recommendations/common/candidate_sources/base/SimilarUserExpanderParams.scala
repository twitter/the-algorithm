package com.twitter.follow_recommendations.common.candidate_sources.base

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object SimilarUserExpanderParams {

  case object EnableNonDirectFollowExpansion
      extends FSParam[Boolean]("similar_user_enable_non_direct_follow_expansion", true)

  case object EnableSimsExpandSeedAccountsSort
      extends FSParam[Boolean]("similar_user_enable_sims_expander_seed_account_sort", false)

  case object DefaultExpansionInputCount
      extends FSBoundedParam[Int](
        name = "similar_user_default_expansion_input_count",
        default = Integer.MAX_VALUE,
        min = 0,
        max = Integer.MAX_VALUE)

  case object DefaultFinalCandidatesReturnedCount
      extends FSBoundedParam[Int](
        name = "similar_user_default_final_candidates_returned_count",
        default = Integer.MAX_VALUE,
        min = 0,
        max = Integer.MAX_VALUE)

  case object DefaultEnableImplicitEngagedExpansion
      extends FSParam[Boolean]("similar_user_enable_implicit_engaged_expansion", true)

}
