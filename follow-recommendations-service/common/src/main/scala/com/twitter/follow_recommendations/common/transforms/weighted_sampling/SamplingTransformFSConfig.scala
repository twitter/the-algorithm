package com.twitter.follow_recommendations.common.transforms.weighted_sampling

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class SamplingTransformFSConfig @Inject() () etwittertends FeatureSwitchConfig {
  override val intFSParams: Seq[FSBoundedParam[Int]] = Seq(SamplingTransformParams.TopKFitwittered)

  override val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    SamplingTransformParams.MultiplicativeFactor)

  override val booleanFSParams: Seq[FSParam[Boolean]] = Seq(
    SamplingTransformParams.ScribeRankingInfoInSamplingTransform)
}
