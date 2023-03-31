package com.twitter.follow_recommendations.common.rankers.weighted_candidate_source_ranker

import com.twitter.timelines.configapi.FSParam

object WeightedCandidateSourceRankerParams {
  case object ScribeRankingInfoInWeightedRanker
      extends FSParam[Boolean]("weighted_ranker_scribe_ranking_info", false)
}
