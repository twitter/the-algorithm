package com.twitter.follow_recommendations.common.predicates.user_activity

import com.twitter.timelines.configapi.FSParam
import com.twitter.util.Duration

object UserActivityPredicateParams {
  case object HeavyTweeterEnabled
      extends FSParam[Boolean]("user_activity_predicate_heavy_tweeter_enabled", false)
  val CacheTTL: Duration = Duration.fromHours(6)
}
