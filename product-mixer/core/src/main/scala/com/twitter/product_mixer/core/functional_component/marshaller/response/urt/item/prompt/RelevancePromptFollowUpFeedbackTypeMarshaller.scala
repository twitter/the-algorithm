package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.prompt

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt._
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RelevancePromptFollowUpFeedbackTypeMarshaller @Inject() (
  relevancePromptFollowUpTextInputMarshaller: RelevancePromptFollowUpTextInputMarshaller) {

  def apply(
    relevancePromptFollowUpFeedbackType: RelevancePromptFollowUpFeedbackType
  ): urt.RelevancePromptFollowUpFeedbackType = relevancePromptFollowUpFeedbackType match {
    case relevancePromptFollowUpTextInput: RelevancePromptFollowUpTextInput =>
      urt.RelevancePromptFollowUpFeedbackType.FollowUpTextInput(
        relevancePromptFollowUpTextInputMarshaller(relevancePromptFollowUpTextInput))
  }
}
