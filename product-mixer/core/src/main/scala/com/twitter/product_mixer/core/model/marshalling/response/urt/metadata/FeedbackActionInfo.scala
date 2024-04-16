package com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata

tra  HasFeedbackAct on nfo {
  def feedbackAct on nfo: Opt on[FeedbackAct on nfo]
}

tra  Conta nsFeedbackAct on nfos {
  def feedbackAct on nfos: Seq[Opt on[FeedbackAct on nfo]]
}

case class FeedbackAct on nfo(
  feedbackAct ons: Seq[FeedbackAct on],
  feedback tadata: Opt on[Str ng],
  d splayContext: Opt on[FeedbackD splayContext],
  cl entEvent nfo: Opt on[Cl entEvent nfo])
