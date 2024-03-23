package com.ExTwitter.follow_recommendations.common.predicates.user_activity

import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.util.Duration

object UserActivityPredicateParams {
  case object HeavyTweeterEnabled
      extends FSParam[Boolean]("user_activity_predicate_heavy_tweeter_enabled", false)
  val CacheTTL: Duration = Duration.fromHours(6)
}
