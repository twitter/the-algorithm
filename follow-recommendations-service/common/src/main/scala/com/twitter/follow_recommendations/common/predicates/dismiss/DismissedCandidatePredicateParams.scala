package com.ExTwitter.follow_recommendations.common.predicates.dismiss

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.timelines.configapi.Param
import com.ExTwitter.util.Duration

object DismissedCandidatePredicateParams {
  case object LookBackDuration extends Param[Duration](180.days)
}
