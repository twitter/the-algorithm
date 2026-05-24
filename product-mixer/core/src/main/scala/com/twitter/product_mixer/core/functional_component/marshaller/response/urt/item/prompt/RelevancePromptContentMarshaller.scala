package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.prompt

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.CallbackMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt.RelevancePromptContent
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RelevancePromptContentMarshaller @Inject() (
  callbackMarshaller: CallbackMarshaller,
  relevancePromptDisplayTypeMarshaller: RelevancePromptDisplayTypeMarshaller,
  relevancePromptFollowUpFeedbackTypeMarshaller: RelevancePromptFollowUpFeedbackTypeMarshaller) {

  def apply(relevancePromptContent: RelevancePromptContent): urt.RelevancePrompt =
    urt.RelevancePrompt(
      title = relevancePromptContent.title,
      confirmation = relevancePromptContent.confirmation,
      isRelevantText = relevancePromptContent.isRelevantText,
      notRelevantText = relevancePromptContent.notRelevantText,
      isRelevantCallback = callbackMarshaller(relevancePromptContent.isRelevantCallback),
      notRelevantCallback = callbackMarshaller(relevancePromptContent.notRelevantCallback),
      displayType = relevancePromptDisplayTypeMarshaller(relevancePromptContent.displayType),
      isRelevantFollowUp = relevancePromptContent.isRelevantFollowUp.map(
        relevancePromptFollowUpFeedbackTypeMarshaller(_)),
      notRelevantFollowUp = relevancePromptContent.notRelevantFollowUp.map(
        relevancePromptFollowUpFeedbackTypeMarshaller(_))
    )
}
