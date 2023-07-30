package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.message

import com.X.product_mixer.core.model.marshalling.response.urt.item.message.CompactPromptMessageContent
import com.X.product_mixer.core.model.marshalling.response.urt.item.message.InlinePromptMessageContent
import com.X.product_mixer.core.model.marshalling.response.urt.item.message.HeaderImagePromptMessageContent
import com.X.product_mixer.core.model.marshalling.response.urt.item.message.MessageContent
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageContentMarshaller @Inject() (
  inlinePromptMessageContentMarshaller: InlinePromptMessageContentMarshaller,
  headerImagePromptMessageContentMarshaller: HeaderImagePromptMessageContentMarshaller,
  compactPromptMessageContentMarshaller: CompactPromptMessageContentMarshaller) {

  def apply(messageContent: MessageContent): urt.MessageContent = messageContent match {
    case inlinePromptMessageContent: InlinePromptMessageContent =>
      inlinePromptMessageContentMarshaller(inlinePromptMessageContent)
    case headerImagePromptMessageContent: HeaderImagePromptMessageContent =>
      headerImagePromptMessageContentMarshaller(headerImagePromptMessageContent)
    case compactPromptMessageContent: CompactPromptMessageContent =>
      compactPromptMessageContentMarshaller(compactPromptMessageContent)
  }
}
