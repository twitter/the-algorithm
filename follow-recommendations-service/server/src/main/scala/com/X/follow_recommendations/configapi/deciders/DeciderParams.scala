package com.X.follow_recommendations.configapi.deciders

import com.X.timelines.configapi.Param

object DeciderParams {
  object EnableRecommendations extends Param[Boolean](false)
  object EnableScoreUserCandidates extends Param[Boolean](false)
}
