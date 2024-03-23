package com.ExTwitter.follow_recommendations.common.rankers.interleave_ranker

import com.ExTwitter.timelines.configapi.FSParam

object InterleaveRankerParams {
  case object ScribeRankingInfoInInterleaveRanker
      extends FSParam[Boolean]("interleave_ranker_scribe_ranking_info", true)
}
