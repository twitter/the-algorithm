package com.twitter.follow_recommendations.common.rankers.weighted_candidate_source_ranker

object WeightMethod extends Enumeration {
  type WeightMethod = Value
  val WeightedRandomSampling, WeightedRoundRobin = Value
}
