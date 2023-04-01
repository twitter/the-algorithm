package com.twitter.follow_recommendations.common.rankers.first_n_ranker

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param

object FirstNRankerParams {
  case object CandidatesToRank
      extends FSBoundedParam[Int](
        FirstNRankerFeatureSwitchKeys.CandidatePoolSize,
        default = 100,
        min = 50,
        max = 600)

  case object GroupDuplicateCandidates extends Param[Boolean](true)
  case object ScribeRankingInfoInFirstNRanker
      extends FSParam[Boolean](FirstNRankerFeatureSwitchKeys.ScribeRankingInfo, true)

  // the minimum of candidates to score in each request.
  object MinNumCandidatesScoredScaleDownFactor
      extends FSBoundedParam[Double](
        name = FirstNRankerFeatureSwitchKeys.MinNumCandidatesScoredScaleDownFactor,
        default = 0.3,
        min = 0.1,
        max = 1.0)
}
