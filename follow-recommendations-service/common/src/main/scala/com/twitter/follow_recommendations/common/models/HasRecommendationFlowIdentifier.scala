package com.twitter.follow_recommendations.common.models

trait HasRecommendationFlowIdentifier {
  def recommendationFlowIdentifier: Option[String]
}
