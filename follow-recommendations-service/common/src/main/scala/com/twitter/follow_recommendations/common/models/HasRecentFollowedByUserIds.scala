package com.twitter.follow_recommendations.common.models

trait HasRecentFollowedByUserIds {
  // user ids that have recently followed the target user; target user has been "followed by" them.
  def recentFollowedByUserIds: Option[Seq[Long]]

  lazy val numRecentFollowedByUserIds: Int = recentFollowedByUserIds.map(_.size).getOrElse(0)
}
