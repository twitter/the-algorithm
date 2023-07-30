package com.X.follow_recommendations.models

import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.logging.{thriftscala => offline}
import com.X.follow_recommendations.{thriftscala => t}

case class ScoringUserResponse(candidates: Seq[CandidateUser]) {
  lazy val toThrift: t.ScoringUserResponse =
    t.ScoringUserResponse(candidates.map(_.toUserThrift))

  lazy val toRecommendationResponse: RecommendationResponse = RecommendationResponse(candidates)

  lazy val toOfflineThrift: offline.OfflineScoringUserResponse =
    offline.OfflineScoringUserResponse(candidates.map(_.toOfflineUserThrift))
}
