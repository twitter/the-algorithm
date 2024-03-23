package com.ExTwitter.follow_recommendations.common.predicates.hss

import com.ExTwitter.follow_recommendations.common.predicates.hss.HssPredicateParams._
import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.HasDurationConversion
import com.ExTwitter.util.Duration

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
