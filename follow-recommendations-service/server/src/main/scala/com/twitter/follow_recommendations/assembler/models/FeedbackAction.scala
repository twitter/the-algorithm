package com.twitter.follow_recommendations.assembler.models

import com.twitter.follow_recommendations.{thriftscala => t}

trait FeedbackAction {
  def toThrift: t.FeedbackAction
}

case class DismissUserId() extends FeedbackAction {
  override lazy val toThrift: t.FeedbackAction = {
    t.FeedbackAction.DismissUserId(t.DismissUserId())
  }
}
