package com.X.follow_recommendations.common.predicates.sgs

import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.HasDurationConversion
import com.X.util.Duration

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SgsPredicateFSConfig @Inject() () extends FeatureSwitchConfig {
  override val durationFSParams: Seq[FSBoundedParam[Duration] with HasDurationConversion] = Seq(
    SgsPredicateParams.SgsRelationshipsPredicateTimeout
  )
}
