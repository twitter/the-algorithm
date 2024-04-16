package com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata

tra  HasFeedback nfo {
  def feedback nfo: Opt on[Feedback nfo]
}

case class FeedbackD splayContext(reason: Str ng)

case class Feedback nfo(
  feedbackKeys: Seq[Str ng],
  feedback tadata: Opt on[Str ng],
  d splayContext: Opt on[FeedbackD splayContext],
  cl entEvent nfo: Opt on[Cl entEvent nfo])
