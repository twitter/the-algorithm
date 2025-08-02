package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.prompt

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.CallbackMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.RelevancePromptContent
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RelevancePromptContentMarshaller @Inject() (
  callbackMarshaller: CallbackMarshaller,
  relevancePromptDisplayTypeMarshaller: RelevancePromptDisplayTypeMarshaller,
  relevancePromptFollowUpFeedbackTypeMarshaller: RelevancePromptFollowUpFeedbackTypeMarshaller) {

  def apply(relevancePromptContent: RelevancePromptContent): urt.RelevancePrompt =
    urt.RelevancePrompt(
      title = relevancePromptContent.title,
      confirmation = relevancePromptContent.confirmation,
      isRelevantTetwittert = relevancePromptContent.isRelevantTetwittert,
      notRelevantTetwittert = relevancePromptContent.notRelevantTetwittert,
      isRelevantCallback = callbackMarshaller(relevancePromptContent.isRelevantCallback),
      notRelevantCallback = callbackMarshaller(relevancePromptContent.notRelevantCallback),
      displayType = relevancePromptDisplayTypeMarshaller(relevancePromptContent.displayType),
      isRelevantFollowUp = relevancePromptContent.isRelevantFollowUp.map(
        relevancePromptFollowUpFeedbackTypeMarshaller(_)),
      notRelevantFollowUp = relevancePromptContent.notRelevantFollowUp.map(
        relevancePromptFollowUpFeedbackTypeMarshaller(_))
    )
}
