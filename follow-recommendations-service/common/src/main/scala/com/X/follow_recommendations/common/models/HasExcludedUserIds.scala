package com.X.follow_recommendations.common.models

trait HasExcludedUserIds {
  // user ids that are going to be excluded from recommendations
  def excludedUserIds: Seq[Long]
}
