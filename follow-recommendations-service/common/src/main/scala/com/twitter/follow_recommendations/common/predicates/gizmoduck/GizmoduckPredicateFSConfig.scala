package com.twitter.follow_recommendations.common.predicates.gizmoduck

import com.twitter.follow_recommendations.common.predicates.gizmoduck.GizmoduckPredicateParams._
import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.util.Duration

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GizmoduckPredicateFSConfig @Inject() () extends FeatureSwitchConfig {
  override val durationFSParams: Seq[FSBoundedParam[Duration] with HasDurationConversion] = Seq(
    GizmoduckGetTimeout
  )
}
