package com.ExTwitter.follow_recommendations.common.transforms.weighted_sampling

import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.timelines.configapi.FSBoundedParam
import com.ExTwitter.timelines.configapi.FSParam

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SamplingTransformFSConfig @Inject() () extends FeatureSwitchConfig {
  override val intFSParams: Seq[FSBoundedParam[Int]] = Seq(SamplingTransformParams.TopKFixed)

  override val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    SamplingTransformParams.MultiplicativeFactor)

  override val booleanFSParams: Seq[FSParam[Boolean]] = Seq(
    SamplingTransformParams.ScribeRankingInfoInSamplingTransform)
}
