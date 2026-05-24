package com.twitter.follow_recommendations.common.rankers.first_n_ranker

object FirstNRankerFeatureSwitchKeys {
  val CandidatePoolSize = "first_n_ranker_candidate_pool_size"
  val ScribeRankingInfo = "first_n_ranker_scribe_ranking_info"
  val MinNumCandidatesScoredScaleDownFactor =
    "first_n_ranker_min_scale_down_factor"
}
