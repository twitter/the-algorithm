package com.twitter.follow_recommendations.common.models

import com.twitter.follow_recommendations.logging.{thriftscala => offline}
import com.twitter.follow_recommendations.{thriftscala => t}

case class RecentlyEngagedUserId(id: Long, engagementType: EngagementType) {
  def toThrift: t.RecentlyEngagedUserId =
    t.RecentlyEngagedUserId(id = id, engagementType = engagementType.toThrift)

  def toOfflineThrift: offline.RecentlyEngagedUserId =
    offline.RecentlyEngagedUserId(id = id, engagementType = engagementType.toOfflineThrift)
}

object RecentlyEngagedUserId {
  def fromThrift(recentlyEngagedUserId: t.RecentlyEngagedUserId): RecentlyEngagedUserId = {
    RecentlyEngagedUserId(
      id = recentlyEngagedUserId.id,
      engagementType = EngagementType.fromThrift(recentlyEngagedUserId.engagementType)
    )
  }

  def fromOfflineThrift(
    recentlyEngagedUserId: offline.RecentlyEngagedUserId
  ): RecentlyEngagedUserId = {
    RecentlyEngagedUserId(
      id = recentlyEngagedUserId.id,
      engagementType = EngagementType.fromOfflineThrift(recentlyEngagedUserId.engagementType)
    )
  }

}
