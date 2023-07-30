package com.X.follow_recommendations.common.predicates

import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.Param

object InactivePredicateParams {
  case object DefaultInactivityThreshold
      extends FSBoundedParam[Int](
        name = "inactive_predicate_default_inactivity_threshold",
        default = 60,
        min = 1,
        max = 500
      )
  case object UseEggFilter extends Param[Boolean](true)
  case object MightBeDisabled extends FSParam[Boolean]("inactive_predicate_might_be_disabled", true)
  case object OnlyDisableForNewUserStateCandidates
      extends FSParam[Boolean](
        "inactive_predicate_only_disable_for_new_user_state_candidates",
        false)
}
