package com.twitter.follow_recommendations.common.rankers.fatigue_ranker

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSParam
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ImpressionBasedFatigueRankerFSConfig @Inject() etwittertends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq(ImpressionBasedFatigueRankerParams.ScribeRankingInfoInFatigueRanker)
}
