package com.twitter.follow_recommendations.common.models

import com.twitter.follow_recommendations.{thriftscala => t}
import com.twitter.follow_recommendations.logging.{thriftscala => offline}

case class RecommendationStep(
  recommendations: Seq[FlowRecommendation],
  followedUserIds: Set[Long]) {

  def toThrift: t.RecommendationStep = t.RecommendationStep(
    recommendations = recommendations.map(_.toThrift),
    followedUserIds = followedUserIds
  )

  def toOfflineThrift: offline.OfflineRecommendationStep =
    offline.OfflineRecommendationStep(
      recommendations = recommendations.map(_.toOfflineThrift),
      followedUserIds = followedUserIds)

}

object RecommendationStep {

  def fromThrift(recommendationStep: t.RecommendationStep): RecommendationStep = {
    RecommendationStep(
      recommendations = recommendationStep.recommendations.map(FlowRecommendation.fromThrift),
      followedUserIds = recommendationStep.followedUserIds.toSet)
  }

}
