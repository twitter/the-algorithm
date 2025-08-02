package com.twitter.follow_recommendations.configapi.deciders

import com.twitter.timelines.configapi.Param

object DeciderParams {
  object EnableRecommendations etwittertends Param[Boolean](false)
  object EnableScoreUserCandidates etwittertends Param[Boolean](false)
}
