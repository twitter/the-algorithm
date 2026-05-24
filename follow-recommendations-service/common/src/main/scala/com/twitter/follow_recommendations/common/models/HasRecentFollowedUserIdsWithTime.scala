package com.twitter.follow_recommendations.common.models

trait HasRecentFollowedUserIdsWithTime {
  // user ids that are recently followed by the target user
  def recentFollowedUserIdsWithTime: Option[Seq[UserIdWithTimestamp]]

  lazy val numRecentFollowedUserIdsWithTime: Int =
    recentFollowedUserIdsWithTime.map(_.size).getOrElse(0)
}
