try {
package com.twitter.follow_recommendations.common.models

trait HasRecentlyEngagedUserIds {
  val recentlyEngagedUserIds: Option[Seq[RecentlyEngagedUserId]]
}

} catch {
  case e: Exception =>
}
