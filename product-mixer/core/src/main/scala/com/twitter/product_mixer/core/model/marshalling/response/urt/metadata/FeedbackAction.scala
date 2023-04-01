package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.icon.HorizonIcon

case class FeedbackAction(
  feedbackType: FeedbackType,
  prompt: Option[String],
  confirmation: Option[String],
  childFeedbackActions: Option[Seq[ChildFeedbackAction]],
  feedbackUrl: Option[String],
  hasUndoAction: Option[Boolean],
  confirmationDisplayType: Option[ConfirmationDisplayType],
  clientEventInfo: Option[ClientEventInfo],
  icon: Option[HorizonIcon],
  richBehavior: Option[RichFeedbackBehavior],
  subprompt: Option[String],
  encodedFeedbackRequest: Option[String])

case class ChildFeedbackAction(
  feedbackType: FeedbackType,
  prompt: Option[String],
  confirmation: Option[String],
  feedbackUrl: Option[String],
  hasUndoAction: Option[Boolean],
  confirmationDisplayType: Option[ConfirmationDisplayType],
  clientEventInfo: Option[ClientEventInfo],
  icon: Option[HorizonIcon],
  richBehavior: Option[RichFeedbackBehavior],
  subprompt: Option[String])
