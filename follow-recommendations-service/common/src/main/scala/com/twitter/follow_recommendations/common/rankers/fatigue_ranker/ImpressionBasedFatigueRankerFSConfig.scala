package com.twitter.follow_recommendations.common.rankers.fatigue_ranker

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImpressionBasedFatigueRankerFSConfig @Inject() extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq(ImpressionBasedFatigueRankerParams.ScribeRankingInfoInFatigueRanker)
}
