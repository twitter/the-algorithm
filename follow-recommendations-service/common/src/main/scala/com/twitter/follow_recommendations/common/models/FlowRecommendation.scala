package com.ExTwitter.follow_recommendations.common.models

import com.ExTwitter.follow_recommendations.logging.{thriftscala => offline}
import com.ExTwitter.follow_recommendations.{thriftscala => t}

case class FlowRecommendation(userId: Long) {

  def toThrift: t.FlowRecommendation =
    t.FlowRecommendation(userId = userId)

  def toOfflineThrift: offline.OfflineFlowRecommendation =
    offline.OfflineFlowRecommendation(userId = userId)

}

object FlowRecommendation {
  def fromThrift(flowRecommendation: t.FlowRecommendation): FlowRecommendation = {
    FlowRecommendation(
      userId = flowRecommendation.userId
    )
  }

}
