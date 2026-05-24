package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

trait HasFeedbackInfo {
  def feedbackInfo: Option[FeedbackInfo]
}

case class FeedbackDisplayContext(reason: String)

case class FeedbackInfo(
  feedbackKeys: Seq[String],
  feedbackMetadata: Option[String],
  displayContext: Option[FeedbackDisplayContext],
  clientEventInfo: Option[ClientEventInfo])
