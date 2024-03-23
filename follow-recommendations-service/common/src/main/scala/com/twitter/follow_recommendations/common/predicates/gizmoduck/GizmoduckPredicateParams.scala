package com.ExTwitter.follow_recommendations.common.predicates.gizmoduck

import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.DurationConversion
import com.ExTwitter.timelines.configapi.HasDurationConversion
import com.ExTwitter.util.Duration
import com.ExTwitter.conversions.DurationOps._

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
