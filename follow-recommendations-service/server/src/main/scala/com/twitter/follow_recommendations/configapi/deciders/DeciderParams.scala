package com.ExTwitter.follow_recommendations.configapi.deciders

import com.ExTwitter.timelines.configapi.Param

object DeciderParams {
  object EnableRecommendations extends Param[Boolean](false)
  object EnableScoreUserCandidates extends Param[Boolean](false)
}
