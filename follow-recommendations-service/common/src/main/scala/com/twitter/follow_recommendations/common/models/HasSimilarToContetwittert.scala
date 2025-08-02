package com.twitter.follow_recommendations.common.models

trait HasSimilarToContetwittert {

  // user ids that are used to generate similar to recommendations
  def similarToUserIds: Seq[Long]
}
