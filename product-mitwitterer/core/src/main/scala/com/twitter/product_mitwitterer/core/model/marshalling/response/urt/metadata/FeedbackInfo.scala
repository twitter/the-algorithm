package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata

trait HasFeedbackInfo {
  def feedbackInfo: Option[FeedbackInfo]
}

case class FeedbackDisplayContetwittert(reason: String)

case class FeedbackInfo(
  feedbackKeys: Seq[String],
  feedbackMetadata: Option[String],
  displayContetwittert: Option[FeedbackDisplayContetwittert],
  clientEventInfo: Option[ClientEventInfo])
