package com.twitter.follow_recommendations.common.candidate_sources.triangular_loops

import com.twitter.timelines.configapi.FSParam

object TriangularLoopsParams {

  object KeepOnlyCandidatesWhoFollowTargetUser
      extends FSParam[Boolean](
        "triangular_loops_keep_only_candidates_who_follow_target_user",
        false)
}
