package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.prompt

import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.CallbackMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.item.prompt.RelevancePromptFollowUpTextInput
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RelevancePromptFollowUpTextInputMarshaller @Inject() (
  callbackMarshaller: CallbackMarshaller) {

  def apply(
    relevancePromptFollowUpTextInput: RelevancePromptFollowUpTextInput
  ): urt.RelevancePromptFollowUpTextInput = urt.RelevancePromptFollowUpTextInput(
    context = relevancePromptFollowUpTextInput.context,
    textFieldPlaceholder = relevancePromptFollowUpTextInput.textFieldPlaceholder,
    sendTextCallback = callbackMarshaller(relevancePromptFollowUpTextInput.sendTextCallback)
  )
}
