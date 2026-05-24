package com.twitter.follow_recommendations.common.predicates.hss

import com.twitter.follow_recommendations.common.predicates.hss.HssPredicateParams._
import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.util.Duration

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
