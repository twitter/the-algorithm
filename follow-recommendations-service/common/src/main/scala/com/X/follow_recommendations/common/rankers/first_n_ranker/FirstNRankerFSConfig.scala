package com.X.follow_recommendations.common.rankers.first_n_ranker

import javax.inject.Inject
import javax.inject.Singleton
import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSParam

@Singleton
class FirstNRankerFSConfig @Inject() extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq(FirstNRankerParams.ScribeRankingInfoInFirstNRanker)

  override val intFSParams: Seq[FSBoundedParam[Int]] = Seq(
    FirstNRankerParams.CandidatesToRank
  )

  override val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    FirstNRankerParams.MinNumCandidatesScoredScaleDownFactor
  )
}
