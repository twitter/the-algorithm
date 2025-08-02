package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata

trait HasFeedbackActionInfo {
  def feedbackActionInfo: Option[FeedbackActionInfo]
}

trait ContainsFeedbackActionInfos {
  def feedbackActionInfos: Seq[Option[FeedbackActionInfo]]
}

case class FeedbackActionInfo(
  feedbackActions: Seq[FeedbackAction],
  feedbackMetadata: Option[String],
  displayContetwittert: Option[FeedbackDisplayContetwittert],
  clientEventInfo: Option[ClientEventInfo])
