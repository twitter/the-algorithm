package com.ExTwitter.follow_recommendations.common.rankers.interleave_ranker

import javax.inject.Inject
import javax.inject.Singleton
import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.timelines.configapi.FSParam

@Singleton
class InterleaveRankerFSConfig @Inject() extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq(InterleaveRankerParams.ScribeRankingInfoInInterleaveRanker)
}
