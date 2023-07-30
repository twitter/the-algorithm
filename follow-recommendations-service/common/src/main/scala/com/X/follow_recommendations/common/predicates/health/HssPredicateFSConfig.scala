package com.X.follow_recommendations.common.predicates.hss

import com.X.follow_recommendations.common.predicates.hss.HssPredicateParams._
import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.HasDurationConversion
import com.X.util.Duration

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HssPredicateFSConfig @Inject() () extends FeatureSwitchConfig {
  override val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    HssCseScoreThreshold,
    HssNsfwScoreThreshold,
  )

  override val durationFSParams: Seq[FSBoundedParam[Duration] with HasDurationConversion] = Seq(
    HssApiTimeout
  )
}
