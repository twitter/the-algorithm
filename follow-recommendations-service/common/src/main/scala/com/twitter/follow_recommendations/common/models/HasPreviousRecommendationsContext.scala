package com.twitter.follow_recommendations.common.models

trait HasPreviousRecommendationsContext {

  def previouslyRecommendedUserIDs: Set[Long]

  def previouslyFollowedUserIds: Set[Long]

  def skippedFollows: Set[Long] = {
    previouslyRecommendedUserIDs.diff(previouslyFollowedUserIds)
  }
}
