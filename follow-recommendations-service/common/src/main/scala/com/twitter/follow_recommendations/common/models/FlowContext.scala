package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}
 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}

case class FlowContext(steps: Seq[Recom ndat onStep]) {

  def toThr ft: t.FlowContext = t.FlowContext(steps = steps.map(_.toThr ft))

  def toOffl neThr ft: offl ne.Offl neFlowContext =
    offl ne.Offl neFlowContext(steps = steps.map(_.toOffl neThr ft))
}

object FlowContext {

  def fromThr ft(flowContext: t.FlowContext): FlowContext = {
    FlowContext(steps = flowContext.steps.map(Recom ndat onStep.fromThr ft))
  }

}
