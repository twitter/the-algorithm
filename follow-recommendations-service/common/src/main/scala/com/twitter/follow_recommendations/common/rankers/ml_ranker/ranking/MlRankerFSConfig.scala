package com.twitter.follow_recommendations.common.rankers.ml_ranker.ranking

import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSParam

@Singleton
class MlRankerFSConfig @Inject() etwittertends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq(MlRankerParams.ScribeRankingInfoInMlRanker)
}
