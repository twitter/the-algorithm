package com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata

sealed tra  R chFeedbackBehav or

case class R chFeedbackBehav orReportL st(l st d: Long, user d: Long) extends R chFeedbackBehav or
case class R chFeedbackBehav orBlockUser(user d: Long) extends R chFeedbackBehav or
case class R chFeedbackBehav orToggleFollowTop c(top c d: Long) extends R chFeedbackBehav or
case class R chFeedbackBehav orToggleFollowTop cV2(top c d: Str ng) extends R chFeedbackBehav or
case class R chFeedbackBehav orToggleMuteL st(l st d: Long) extends R chFeedbackBehav or
case class R chFeedbackBehav orMarkNot nterestedTop c(top c d: Str ng) extends R chFeedbackBehav or
case class R chFeedbackBehav orReplyP nState(replyP nState: ReplyP nState)
    extends R chFeedbackBehav or
case class R chFeedbackBehav orToggleMuteUser(user d: Long) extends R chFeedbackBehav or
case class R chFeedbackBehav orToggleFollowUser(user d: Long) extends R chFeedbackBehav or
case class R chFeedbackBehav orReportT et(entry d: Long) extends R chFeedbackBehav or
