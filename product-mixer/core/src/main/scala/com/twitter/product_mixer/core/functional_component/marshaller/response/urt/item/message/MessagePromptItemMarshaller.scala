package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.CallbackMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.MessagePromptItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessagePromptItemMarshaller @Inject() (
  messageContentMarshaller: MessageContentMarshaller,
  callbackMarshaller: CallbackMarshaller) {

  def apply(messagePromptItem: MessagePromptItem): urt.TimelineItemContent =
    urt.TimelineItemContent.Message(
      urt.MessagePrompt(
        content = messageContentMarshaller(messagePromptItem.content),
        impressionCallbacks = messagePromptItem.impressionCallbacks.map { callbackList =>
          callbackList.map(callbackMarshaller(_))
        }
      )
    )
}
