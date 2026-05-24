package com.twitter.follow_recommendations.common.predicates.sgs

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.util.Duration
import com.twitter.conversions.DurationOps._

object SgsPredicateParams {
  case object SgsRelationshipsPredicateTimeout
      extends FSBoundedParam[Duration](
        name = "sgs_predicate_relationships_timeout_in_millis",
        default = 300.millisecond,
        min = 1.millisecond,
        max = 1000.millisecond)
      with HasDurationConversion {
    override def durationConversion: DurationConversion = DurationConversion.FromMillis
  }
}
