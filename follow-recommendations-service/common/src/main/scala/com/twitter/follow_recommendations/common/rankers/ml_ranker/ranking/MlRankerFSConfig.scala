package com.ExTwitter.follow_recommendations.common.rankers.ml_ranker.ranking

import javax.inject.Inject
import javax.inject.Singleton
import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.timelines.configapi.FSParam

@Singleton
class MlRankerFSConfig @Inject() extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq(MlRankerParams.ScribeRankingInfoInMlRanker)
}
