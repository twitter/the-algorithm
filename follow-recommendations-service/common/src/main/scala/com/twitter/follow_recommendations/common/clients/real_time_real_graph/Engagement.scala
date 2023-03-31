package com.twitter.follow_recommendations.common.clients.real_time_real_graph

sealed trait EngagementType

// We do not include SoftFollow since it's deprecated
object EngagementType {
  object Click extends EngagementType
  object Like extends EngagementType
  object Mention extends EngagementType
  object Retweet extends EngagementType
  object ProfileView extends EngagementType
}

case class Engagement(engagementType: EngagementType, timestamp: Long)
