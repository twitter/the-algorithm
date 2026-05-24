package com.twitter.follow_recommendations.common.predicates.dismiss

import com.twitter.conversions.DurationOps._
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration

object DismissedCandidatePredicateParams {
  case object LookBackDuration extends Param[Duration](180.days)
}
