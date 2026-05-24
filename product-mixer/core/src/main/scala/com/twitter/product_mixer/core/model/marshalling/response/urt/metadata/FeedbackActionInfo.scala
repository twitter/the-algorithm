package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

trait HasFeedbackActionInfo {
  def feedbackActionInfo: Option[FeedbackActionInfo]
}

trait ContainsFeedbackActionInfos {
  def feedbackActionInfos: Seq[Option[FeedbackActionInfo]]
}

case class FeedbackActionInfo(
  feedbackActions: Seq[FeedbackAction],
  feedbackMetadata: Option[String],
  displayContext: Option[FeedbackDisplayContext],
  clientEventInfo: Option[ClientEventInfo])
