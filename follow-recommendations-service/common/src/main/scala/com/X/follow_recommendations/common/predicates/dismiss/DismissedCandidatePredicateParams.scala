package com.X.follow_recommendations.common.predicates.dismiss

import com.X.conversions.DurationOps._
import com.X.timelines.configapi.Param
import com.X.util.Duration

object DismissedCandidatePredicateParams {
  case object LookBackDuration extends Param[Duration](180.days)
}
