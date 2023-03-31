package com.twitter.follow_recommendations.common.candidate_sources.user_user_graph

import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param

object UserUserGraphParams {

  // max number of candidates to return in total, 50 is the default param used in MagicRecs
  object MaxCandidatesToReturn extends Param[Int](default = 50)

  // whether or not to include UserUserGraph candidate source in the weighted blending step
  case object UserUserGraphCandidateSourceEnabledInWeightMap
      extends FSParam[Boolean]("user_user_graph_candidate_source_enabled_in_weight_map", true)

  // whether or not to include UserUserGraph candidate source in the final transform step
  case object UserUserGraphCandidateSourceEnabledInTransform
      extends FSParam[Boolean]("user_user_graph_candidate_source_enabled_in_transform", true)

}
