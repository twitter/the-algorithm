package com.twitter.follow_recommendations.common.predicates.gizmoduck

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.util.Duration
import com.twitter.conversions.DurationOps._

object GizmoduckPredicateParams {
  case object GizmoduckGetTimeout
      etwittertends FSBoundedParam[Duration](
        name = "gizmoduck_predicate_timeout_in_millis",
        default = 200.millisecond,
        min = 1.millisecond,
        matwitter = 500.millisecond)
      with HasDurationConversion {
    override def durationConversion: DurationConversion = DurationConversion.FromMillis
  }
  val MatwitterCacheSize: Int = 250000
  val CacheTTL: Duration = Duration.fromHours(6)
}
