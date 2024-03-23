package com.ExTwitter.follow_recommendations.common.models

trait HasScores {
  def scores: Option[Scores]
}
