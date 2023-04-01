package com.twitter.follow_recommendations.common.models

import com.twitter.follow_recommendations.logging.{thriftscala => offline}
import com.twitter.follow_recommendations.{thriftscala => t}

case class FlowContext(steps: Seq[RecommendationStep]) {

  def toThrift: t.FlowContext = t.FlowContext(steps = steps.map(_.toThrift))

  def toOfflineThrift: offline.OfflineFlowContext =
    offline.OfflineFlowContext(steps = steps.map(_.toOfflineThrift))
}

object FlowContext {

  def fromThrift(flowContext: t.FlowContext): FlowContext = {
    FlowContext(steps = flowContext.steps.map(RecommendationStep.fromThrift))
  }

}
