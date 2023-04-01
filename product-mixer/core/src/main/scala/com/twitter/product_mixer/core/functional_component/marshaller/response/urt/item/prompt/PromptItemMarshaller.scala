package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.prompt

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.CallbackMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.ClientEventInfoMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt.PromptItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PromptItemMarshaller @Inject() (
  promptContentMarshaller: PromptContentMarshaller,
  clientEventInfoMarshaller: ClientEventInfoMarshaller,
  callbackMarshaller: CallbackMarshaller) {

  def apply(relevancePromptItem: PromptItem): urt.TimelineItemContent = {
    urt.TimelineItemContent.Prompt(
      urt.Prompt(
        content = promptContentMarshaller(relevancePromptItem.content),
        clientEventInfo = relevancePromptItem.clientEventInfo.map(clientEventInfoMarshaller(_)),
        impressionCallbacks = relevancePromptItem.impressionCallbacks.map { callbackList =>
          callbackList.map(callbackMarshaller(_))
        }
      ))
  }
}
