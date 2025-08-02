package com.twitter.follow_recommendations.common.models

import com.twitter.follow_recommendations.logging.{thriftscala => offline}
import com.twitter.follow_recommendations.{thriftscala => t}

case class FlowContetwittert(steps: Seq[RecommendationStep]) {

  def toThrift: t.FlowContetwittert = t.FlowContetwittert(steps = steps.map(_.toThrift))

  def toOfflineThrift: offline.OfflineFlowContetwittert =
    offline.OfflineFlowContetwittert(steps = steps.map(_.toOfflineThrift))
}

object FlowContetwittert {

  def fromThrift(flowContetwittert: t.FlowContetwittert): FlowContetwittert = {
    FlowContetwittert(steps = flowContetwittert.steps.map(RecommendationStep.fromThrift))
  }

}
