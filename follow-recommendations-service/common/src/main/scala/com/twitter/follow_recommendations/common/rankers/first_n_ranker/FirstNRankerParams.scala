package com.twitter.follow_recommendations.common.rankers.first_n_ranker

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param

object FirstNRankerParams {
  case object CandidatesToRank
      etwittertends FSBoundedParam[Int](
        FirstNRankerFeatureSwitchKeys.CandidatePoolSize,
        default = 100,
        min = 50,
        matwitter = 600)

  case object GroupDuplicateCandidates etwittertends Param[Boolean](true)
  case object ScribeRankingInfoInFirstNRanker
      etwittertends FSParam[Boolean](FirstNRankerFeatureSwitchKeys.ScribeRankingInfo, true)

  // the minimum of candidates to score in each request.
  object MinNumCandidatesScoredScaleDownFactor
      etwittertends FSBoundedParam[Double](
        name = FirstNRankerFeatureSwitchKeys.MinNumCandidatesScoredScaleDownFactor,
        default = 0.3,
        min = 0.1,
        matwitter = 1.0)
}
