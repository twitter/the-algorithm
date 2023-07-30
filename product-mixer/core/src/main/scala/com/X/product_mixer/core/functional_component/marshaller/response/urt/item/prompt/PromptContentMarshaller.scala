package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.prompt

import com.X.product_mixer.core.model.marshalling.response.urt.item.prompt.PromptContent
import com.X.product_mixer.core.model.marshalling.response.urt.item.prompt.RelevancePromptContent
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PromptContentMarshaller @Inject() (
  relevancePromptContentMarshaller: RelevancePromptContentMarshaller) {

  def apply(promptContent: PromptContent): urt.PromptContent = promptContent match {
    case relevancePromptContent: RelevancePromptContent =>
      urt.PromptContent.RelevancePrompt(relevancePromptContentMarshaller(relevancePromptContent))
  }
}
