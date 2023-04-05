try {
package com.twitter.follow_recommendations.common.models

trait HasScores {
  def scores: Option[Scores]
}

} catch {
  case e: Exception =>
}
