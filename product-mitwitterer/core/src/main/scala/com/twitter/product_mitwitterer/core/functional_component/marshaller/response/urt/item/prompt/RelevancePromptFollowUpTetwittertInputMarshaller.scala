package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.prompt

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.CallbackMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.RelevancePromptFollowUpTetwittertInput
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RelevancePromptFollowUpTetwittertInputMarshaller @Inject() (
  callbackMarshaller: CallbackMarshaller) {

  def apply(
    relevancePromptFollowUpTetwittertInput: RelevancePromptFollowUpTetwittertInput
  ): urt.RelevancePromptFollowUpTetwittertInput = urt.RelevancePromptFollowUpTetwittertInput(
    contetwittert = relevancePromptFollowUpTetwittertInput.contetwittert,
    tetwittertFieldPlaceholder = relevancePromptFollowUpTetwittertInput.tetwittertFieldPlaceholder,
    sendTetwittertCallback = callbackMarshaller(relevancePromptFollowUpTetwittertInput.sendTetwittertCallback)
  )
}
