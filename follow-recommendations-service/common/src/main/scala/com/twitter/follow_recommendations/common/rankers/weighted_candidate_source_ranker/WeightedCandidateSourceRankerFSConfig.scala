package com.twitter.follow_recommendations.common.rankers.weighted_candidate_source_ranker

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSParam

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class WeightedCandidateSourceRankerFSConfig @Inject() etwittertends FeatureSwitchConfig {
  override val booleanFSParams: Seq[FSParam[Boolean]] =
    Seq(WeightedCandidateSourceRankerParams.ScribeRankingInfoInWeightedRanker)
}
