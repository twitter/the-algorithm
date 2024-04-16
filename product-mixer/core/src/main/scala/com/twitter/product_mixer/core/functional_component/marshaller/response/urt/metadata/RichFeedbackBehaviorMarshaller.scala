package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.NotP nnableReplyP nState
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.P nnableReplyP nState
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.P nnedReplyP nState
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav or
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orBlockUser
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orMarkNot nterestedTop c
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orReplyP nState
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orReportL st
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orReportT et
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orToggleFollowTop c
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orToggleFollowTop cV2
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orToggleFollowUser
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orToggleMuteL st
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orToggleMuteUser
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class R chFeedbackBehav orMarshaller @ nject() () {

  def apply(r chFeedbackBehav or: R chFeedbackBehav or): urt.R chFeedbackBehav or =
    r chFeedbackBehav or match {
      case R chFeedbackBehav orReportL st(l st d, user d) =>
        urt.R chFeedbackBehav or.ReportL st(urt.R chFeedbackBehav orReportL st(l st d, user d))
      case R chFeedbackBehav orBlockUser(user d) =>
        urt.R chFeedbackBehav or.BlockUser(urt.R chFeedbackBehav orBlockUser(user d))
      case R chFeedbackBehav orToggleFollowTop c(top c d) =>
        urt.R chFeedbackBehav or.ToggleFollowTop c(
          urt.R chFeedbackBehav orToggleFollowTop c(top c d))
      case R chFeedbackBehav orToggleFollowTop cV2(top c d) =>
        urt.R chFeedbackBehav or.ToggleFollowTop cV2(
          urt.R chFeedbackBehav orToggleFollowTop cV2(top c d))
      case R chFeedbackBehav orToggleMuteL st(l st d) =>
        urt.R chFeedbackBehav or.ToggleMuteL st(urt.R chFeedbackBehav orToggleMuteL st(l st d))
      case R chFeedbackBehav orMarkNot nterestedTop c(top c d) =>
        urt.R chFeedbackBehav or.MarkNot nterestedTop c(
          urt.R chFeedbackBehav orMarkNot nterestedTop c(top c d))
      case R chFeedbackBehav orReplyP nState(replyP nState) =>
        val p nState: urt.ReplyP nState = replyP nState match {
          case P nnedReplyP nState => urt.ReplyP nState.P nned
          case P nnableReplyP nState => urt.ReplyP nState.P nnable
          case NotP nnableReplyP nState => urt.ReplyP nState.NotP nnable
        }
        urt.R chFeedbackBehav or.ReplyP nState(urt.R chFeedbackBehav orReplyP nState(p nState))
      case R chFeedbackBehav orToggleMuteUser(user d) =>
        urt.R chFeedbackBehav or.ToggleMuteUser(urt.R chFeedbackBehav orToggleMuteUser(user d))
      case R chFeedbackBehav orToggleFollowUser(user d) =>
        urt.R chFeedbackBehav or.ToggleFollowUser(urt.R chFeedbackBehav orToggleFollowUser(user d))
      case R chFeedbackBehav orReportT et(entry d) =>
        urt.R chFeedbackBehav or.ReportT et(urt.R chFeedbackBehav orReportT et(entry d))
    }
}
