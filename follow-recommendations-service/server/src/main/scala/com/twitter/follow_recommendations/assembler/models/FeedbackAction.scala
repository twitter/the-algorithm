package com.tw ter.follow_recom ndat ons.assembler.models

 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}

tra  FeedbackAct on {
  def toThr ft: t.FeedbackAct on
}

case class D sm ssUser d() extends FeedbackAct on {
  overr de lazy val toThr ft: t.FeedbackAct on = {
    t.FeedbackAct on.D sm ssUser d(t.D sm ssUser d())
  }
}
