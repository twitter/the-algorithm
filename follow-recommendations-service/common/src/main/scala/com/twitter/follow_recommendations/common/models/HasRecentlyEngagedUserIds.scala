package com.ExTwitter.follow_recommendations.common.models

trait HasRecentlyEngagedUserIds {
  val recentlyEngagedUserIds: Option[Seq[RecentlyEngagedUserId]]
}
