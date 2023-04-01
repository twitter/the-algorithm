package com.twitter.follow_recommendations.common.models

// intersection of recent followers and followed by
trait HasMutualFollowedUserIds extends HasRecentFollowedUserIds with HasRecentFollowedByUserIds {

  lazy val recentMutualFollows: Seq[Long] =
    recentFollowedUserIds.getOrElse(Nil).intersect(recentFollowedByUserIds.getOrElse(Nil))

  lazy val numRecentMutualFollows: Int = recentMutualFollows.size
}
