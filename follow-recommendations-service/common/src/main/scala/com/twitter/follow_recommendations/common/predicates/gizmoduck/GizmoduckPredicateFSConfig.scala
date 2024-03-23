package com.ExTwitter.follow_recommendations.common.predicates.gizmoduck

import com.ExTwitter.follow_recommendations.common.predicates.gizmoduck.GizmoduckPredicateParams._
import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.HasDurationConversion
import com.ExTwitter.util.Duration

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GizmoduckPredicateFSConfig @Inject() () extends FeatureSwitchConfig {
  override val durationFSParams: Seq[FSBoundedParam[Duration] with HasDurationConversion] = Seq(
    GizmoduckGetTimeout
  )
}
