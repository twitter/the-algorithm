package com.twitter.follow_recommendations.common.models

trait HasRecentFollowedUserIds {
  // user ids that are recently followed by the target user
  def recentFollowedUserIds: Option[Seq[Long]]

  // user ids that are recently followed by the target user in set data-structure
  lazy val recentFollowedUserIdsSet: Option[Set[Long]] = recentFollowedUserIds match {
    case Some(users) => Some(users.toSet)
    case None => Some(Set.empty)
  }

  lazy val numRecentFollowedUserIds: Int = recentFollowedUserIds.map(_.size).getOrElse(0)
}
