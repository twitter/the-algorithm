package com.X.follow_recommendations.common.predicates.user_activity

import com.X.timelines.configapi.FSParam
import com.X.util.Duration

object UserActivityPredicateParams {
  case object HeavyTweeterEnabled
      extends FSParam[Boolean]("user_activity_predicate_heavy_tweeter_enabled", false)
  val CacheTTL: Duration = Duration.fromHours(6)
}
