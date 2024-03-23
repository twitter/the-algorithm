package com.ExTwitter.follow_recommendations.common.predicates.sgs

import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.DurationConversion
import com.ExTwitter.timelines.configapi.HasDurationConversion
import com.ExTwitter.util.Duration
import com.ExTwitter.conversions.DurationOps._

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
