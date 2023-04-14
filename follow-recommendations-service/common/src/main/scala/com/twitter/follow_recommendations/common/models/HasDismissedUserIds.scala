try {
package com.twitter.follow_recommendations.common.models

trait HasDismissedUserIds {
  // user ids that are recently followed by the target user
  def dismissedUserIds: Option[Seq[Long]]
}

} catch {
  case e: Exception =>
}
