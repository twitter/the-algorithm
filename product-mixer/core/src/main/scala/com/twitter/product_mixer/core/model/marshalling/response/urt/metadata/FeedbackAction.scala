package com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. con.Hor zon con

case class FeedbackAct on(
  feedbackType: FeedbackType,
  prompt: Opt on[Str ng],
  conf rmat on: Opt on[Str ng],
  ch ldFeedbackAct ons: Opt on[Seq[Ch ldFeedbackAct on]],
  feedbackUrl: Opt on[Str ng],
  hasUndoAct on: Opt on[Boolean],
  conf rmat onD splayType: Opt on[Conf rmat onD splayType],
  cl entEvent nfo: Opt on[Cl entEvent nfo],
   con: Opt on[Hor zon con],
  r chBehav or: Opt on[R chFeedbackBehav or],
  subprompt: Opt on[Str ng],
  encodedFeedbackRequest: Opt on[Str ng])

case class Ch ldFeedbackAct on(
  feedbackType: FeedbackType,
  prompt: Opt on[Str ng],
  conf rmat on: Opt on[Str ng],
  feedbackUrl: Opt on[Str ng],
  hasUndoAct on: Opt on[Boolean],
  conf rmat onD splayType: Opt on[Conf rmat onD splayType],
  cl entEvent nfo: Opt on[Cl entEvent nfo],
   con: Opt on[Hor zon con],
  r chBehav or: Opt on[R chFeedbackBehav or],
  subprompt: Opt on[Str ng])
