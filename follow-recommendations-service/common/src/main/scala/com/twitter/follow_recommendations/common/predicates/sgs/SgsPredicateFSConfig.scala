package com.ExTwitter.follow_recommendations.common.predicates.sgs

import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.HasDurationConversion
import com.ExTwitter.util.Duration

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SgsPredicateFSConfig @Inject() () extends FeatureSwitchConfig {
  override val durationFSParams: Seq[FSBoundedParam[Duration] with HasDurationConversion] = Seq(
    SgsPredicateParams.SgsRelationshipsPredicateTimeout
  )
}
