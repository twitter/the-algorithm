package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.icon.HorizonIconMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ChildFeedbackAction
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ChildFeedbackActionMarshaller @Inject() (
  feedbackTypeMarshaller: FeedbackTypeMarshaller,
  confirmationDisplayTypeMarshaller: ConfirmationDisplayTypeMarshaller,
  clientEventInfoMarshaller: ClientEventInfoMarshaller,
  horizonIconMarshaller: HorizonIconMarshaller,
  richFeedbackBehaviorMarshaller: RichFeedbackBehaviorMarshaller) {

  def apply(feedbackAction: ChildFeedbackAction): urt.FeedbackAction = {
    urt.FeedbackAction(
      feedbackType = feedbackTypeMarshaller(feedbackAction.feedbackType),
      prompt = feedbackAction.prompt,
      confirmation = feedbackAction.confirmation,
      childKeys = None,
      feedbackUrl = feedbackAction.feedbackUrl,
      hasUndoAction = feedbackAction.hasUndoAction,
      confirmationDisplayType =
        feedbackAction.confirmationDisplayType.map(confirmationDisplayTypeMarshaller(_)),
      clientEventInfo = feedbackAction.clientEventInfo.map(clientEventInfoMarshaller(_)),
      icon = feedbackAction.icon.map(horizonIconMarshaller(_)),
      richBehavior = feedbackAction.richBehavior.map(richFeedbackBehaviorMarshaller(_)),
      subprompt = feedbackAction.subprompt
    )
  }
}
