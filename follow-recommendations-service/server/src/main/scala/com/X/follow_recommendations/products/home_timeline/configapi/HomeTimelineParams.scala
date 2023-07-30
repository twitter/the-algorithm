package com.X.follow_recommendations.products.home_timeline.configapi

import com.X.conversions.DurationOps._
import com.X.timelines.configapi.DurationConversion
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.HasDurationConversion
import com.X.timelines.configapi.Param
import com.X.util.Duration

object HomeTimelineParams {
  object EnableProduct extends Param[Boolean](false)

  object DefaultMaxResults extends Param[Int](20)

  object EnableWritingServingHistory
      extends FSParam[Boolean]("home_timeline_enable_writing_serving_history", false)

  object DurationGuardrailToForceSuggest
      extends FSBoundedParam[Duration](
        name = "home_timeline_duration_guardrail_to_force_suggest_in_hours",
        default = 0.hours,
        min = 0.hours,
        max = 1000.hours)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromHours
  }

  object SuggestBasedFatigueDuration
      extends FSBoundedParam[Duration](
        name = "home_timeline_suggest_based_fatigue_duration_in_hours",
        default = 0.hours,
        min = 0.hours,
        max = 1000.hours)
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromHours
  }
}
