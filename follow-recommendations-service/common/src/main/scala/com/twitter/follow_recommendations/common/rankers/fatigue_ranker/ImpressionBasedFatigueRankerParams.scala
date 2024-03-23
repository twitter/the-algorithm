package com.ExTwitter.follow_recommendations.common.rankers.fatigue_ranker

import com.ExTwitter.timelines.configapi.FSParam
import com.ExTwitter.timelines.configapi.Param

object ImpressionBasedFatigueRankerParams {
  // Whether to enable hard dropping of impressed candidates
  object DropImpressedCandidateEnabled extends Param[Boolean](false)
  // At what # of impressions to hard drop candidates.
  object DropCandidateImpressionThreshold extends Param[Int](default = 10)
  // Whether to scribe candidate ranking/scoring info per ranking stage
  object ScribeRankingInfoInFatigueRanker
      extends FSParam[Boolean]("fatigue_ranker_scribe_ranking_info", true)
}
