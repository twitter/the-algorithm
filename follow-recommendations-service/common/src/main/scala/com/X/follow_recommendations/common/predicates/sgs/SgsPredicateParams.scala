package com.X.follow_recommendations.common.predicates.sgs

import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.DurationConversion
import com.X.timelines.configapi.HasDurationConversion
import com.X.util.Duration
import com.X.conversions.DurationOps._

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
