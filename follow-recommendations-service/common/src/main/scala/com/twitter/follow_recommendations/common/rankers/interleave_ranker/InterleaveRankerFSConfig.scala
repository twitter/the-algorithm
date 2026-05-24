package com.twitter.follow_recommendations.common.rankers.interleave_ranker

import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSParam

@Singleton
class InterleaveRankerFSConfig @Inject() extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq(InterleaveRankerParams.ScribeRankingInfoInInterleaveRanker)
}
