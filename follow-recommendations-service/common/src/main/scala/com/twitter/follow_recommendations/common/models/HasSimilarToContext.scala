package com.twitter.follow_recommendations.common.models

trait HasSimilarToContext {

  // user ids that are used to generate similar to recommendations
  def similarToUserIds: Seq[Long]
}
