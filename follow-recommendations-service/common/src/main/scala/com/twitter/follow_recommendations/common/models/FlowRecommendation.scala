package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}
 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}

case class FlowRecom ndat on(user d: Long) {

  def toThr ft: t.FlowRecom ndat on =
    t.FlowRecom ndat on(user d = user d)

  def toOffl neThr ft: offl ne.Offl neFlowRecom ndat on =
    offl ne.Offl neFlowRecom ndat on(user d = user d)

}

object FlowRecom ndat on {
  def fromThr ft(flowRecom ndat on: t.FlowRecom ndat on): FlowRecom ndat on = {
    FlowRecom ndat on(
      user d = flowRecom ndat on.user d
    )
  }

}
