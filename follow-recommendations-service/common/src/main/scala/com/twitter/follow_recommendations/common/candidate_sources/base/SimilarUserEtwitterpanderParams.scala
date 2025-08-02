package com.twitter.follow_recommendations.common.candidate_sources.base

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object SimilarUserEtwitterpanderParams {

  case object EnableNonDirectFollowEtwitterpansion
      etwittertends FSParam[Boolean]("similar_user_enable_non_direct_follow_etwitterpansion", true)

  case object EnableSimsEtwitterpandSeedAccountsSort
      etwittertends FSParam[Boolean]("similar_user_enable_sims_etwitterpander_seed_account_sort", false)

  case object DefaultEtwitterpansionInputCount
      etwittertends FSBoundedParam[Int](
        name = "similar_user_default_etwitterpansion_input_count",
        default = Integer.MAtwitter_VALUE,
        min = 0,
        matwitter = Integer.MAtwitter_VALUE)

  case object DefaultFinalCandidatesReturnedCount
      etwittertends FSBoundedParam[Int](
        name = "similar_user_default_final_candidates_returned_count",
        default = Integer.MAtwitter_VALUE,
        min = 0,
        matwitter = Integer.MAtwitter_VALUE)

  case object DefaultEnableImplicitEngagedEtwitterpansion
      etwittertends FSParam[Boolean]("similar_user_enable_implicit_engaged_etwitterpansion", true)

}
