package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.prompt

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.PromptContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.RelevancePromptContent
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class PromptContentMarshaller @Inject() (
  relevancePromptContentMarshaller: RelevancePromptContentMarshaller) {

  def apply(promptContent: PromptContent): urt.PromptContent = promptContent match {
    case relevancePromptContent: RelevancePromptContent =>
      urt.PromptContent.RelevancePrompt(relevancePromptContentMarshaller(relevancePromptContent))
  }
}
