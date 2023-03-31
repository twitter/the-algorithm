package com.twitter.follow_recommendations.configapi.deciders

import com.twitter.timelines.configapi.Param

object DeciderParams {
  object EnableRecommendations extends Param[Boolean](false)
  object EnableScoreUserCandidates extends Param[Boolean](false)
}
