package com.X.follow_recommendations.common.predicates.gizmoduck

import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.DurationConversion
import com.X.timelines.configapi.HasDurationConversion
import com.X.util.Duration
import com.X.conversions.DurationOps._

object GizmoduckPredicateParams {
  case object GizmoduckGetTimeout
      extends FSBoundedParam[Duration](
        name = "gizmoduck_predicate_timeout_in_millis",
        default = 200.millisecond,
        min = 1.millisecond,
        max = 500.millisecond)
      with HasDurationConversion {
    override def durationConversion: DurationConversion = DurationConversion.FromMillis
  }
  val MaxCacheSize: Int = 250000
  val CacheTTL: Duration = Duration.fromHours(6)
}
