package com.twitter.follow_recommendations.common.rankers.interleave_ranker

import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSParam

@Singleton
class InterleaveRankerFSConfig @Inject() etwittertends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq(InterleaveRankerParams.ScribeRankingInfoInInterleaveRanker)
}
