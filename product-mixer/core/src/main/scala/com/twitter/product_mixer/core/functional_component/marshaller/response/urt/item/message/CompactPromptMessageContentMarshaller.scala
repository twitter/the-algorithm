package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.CompactPromptMessageContent
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompactPromptMessageContentMarshaller @Inject() (
  messageTextActionMarshaller: MessageTextActionMarshaller,
  messageActionMarshaller: MessageActionMarshaller,
  richTextMarshaller: RichTextMarshaller) {

  def apply(compactPromptMessageContent: CompactPromptMessageContent): urt.MessageContent =
    urt.MessageContent.CompactPrompt(
      urt.CompactPrompt(
        headerText = compactPromptMessageContent.headerText,
        bodyText = compactPromptMessageContent.bodyText,
        primaryButtonAction =
          compactPromptMessageContent.primaryButtonAction.map(messageTextActionMarshaller(_)),
        secondaryButtonAction =
          compactPromptMessageContent.secondaryButtonAction.map(messageTextActionMarshaller(_)),
        action = compactPromptMessageContent.action.map(messageActionMarshaller(_)),
        headerRichText = compactPromptMessageContent.headerRichText.map(richTextMarshaller(_)),
        bodyRichText = compactPromptMessageContent.bodyRichText.map(richTextMarshaller(_))
      )
    )
}
