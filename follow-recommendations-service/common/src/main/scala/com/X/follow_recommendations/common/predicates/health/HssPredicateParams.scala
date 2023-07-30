package com.X.follow_recommendations.common.predicates.hss

import com.X.conversions.DurationOps._
import com.X.timelines.configapi.DurationConversion
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.HasDurationConversion
import com.X.util.Duration

object HssPredicateParams {
  object HssCseScoreThreshold
      extends FSBoundedParam[Double](
        "hss_predicate_cse_score_threshold",
        default = 0.992d,
        min = 0.0d,
        max = 1.0d)

  object HssNsfwScoreThreshold
      extends FSBoundedParam[Double](
        "hss_predicate_nsfw_score_threshold",
        default = 1.5d,
        min = -100.0d,
        max = 100.0d)

  object HssApiTimeout
      extends FSBoundedParam[Duration](
        name = "hss_predicate_timeout_in_millis",
        default = 200.millisecond,
        min = 1.millisecond,
        max = 500.millisecond)
      with HasDurationConversion {
    override def durationConversion: DurationConversion = DurationConversion.FromMillis
  }

}
