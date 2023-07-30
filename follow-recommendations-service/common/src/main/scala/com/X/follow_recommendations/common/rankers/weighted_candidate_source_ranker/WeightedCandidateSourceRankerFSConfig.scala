package com.X.follow_recommendations.common.rankers.weighted_candidate_source_ranker

import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.timelines.configapi.FSParam

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeightedCandidateSourceRankerFSConfig @Inject() extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq(WeightedCandidateSourceRankerParams.ScribeRankingInfoInWeightedRanker)
}
