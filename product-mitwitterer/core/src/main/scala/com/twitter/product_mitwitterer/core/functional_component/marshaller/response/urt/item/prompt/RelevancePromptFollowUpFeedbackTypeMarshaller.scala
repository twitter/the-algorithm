package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.prompt

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt._
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RelevancePromptFollowUpFeedbackTypeMarshaller @Inject() (
  relevancePromptFollowUpTetwittertInputMarshaller: RelevancePromptFollowUpTetwittertInputMarshaller) {

  def apply(
    relevancePromptFollowUpFeedbackType: RelevancePromptFollowUpFeedbackType
  ): urt.RelevancePromptFollowUpFeedbackType = relevancePromptFollowUpFeedbackType match {
    case relevancePromptFollowUpTetwittertInput: RelevancePromptFollowUpTetwittertInput =>
      urt.RelevancePromptFollowUpFeedbackType.FollowUpTetwittertInput(
        relevancePromptFollowUpTetwittertInputMarshaller(relevancePromptFollowUpTetwittertInput))
  }
}
