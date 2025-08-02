package com.twitter.follow_recommendations.common.clients.real_time_real_graph

sealed trait EngagementType

// We do not include SoftFollow since it's deprecated
object EngagementType {
  object Click etwittertends EngagementType
  object Like etwittertends EngagementType
  object Mention etwittertends EngagementType
  object Retweet etwittertends EngagementType
  object ProfileView etwittertends EngagementType
}

case class Engagement(engagementType: EngagementType, timestamp: Long)
