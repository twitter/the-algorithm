package com.twitter.follow_recommendations.common.predicates

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param

object InactivePredicateParams {
  case object DefaultInactivityThreshold
      etwittertends FSBoundedParam[Int](
        name = "inactive_predicate_default_inactivity_threshold",
        default = 60,
        min = 1,
        matwitter = 500
      )
  case object UseEggFilter etwittertends Param[Boolean](true)
  case object MightBeDisabled etwittertends FSParam[Boolean]("inactive_predicate_might_be_disabled", true)
  case object OnlyDisableForNewUserStateCandidates
      etwittertends FSParam[Boolean](
        "inactive_predicate_only_disable_for_new_user_state_candidates",
        false)
}
