package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.HeaderImagePromptMessageContent
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeaderImagePromptMessageContentMarshaller @Inject() (
  messageImageMarshaller: MessageImageMarshaller,
  messageTextActionMarshaller: MessageTextActionMarshaller,
  messageActionMarshaller: MessageActionMarshaller,
  richTextMarshaller: RichTextMarshaller) {

  def apply(
    headerImagePromptMessageContent: HeaderImagePromptMessageContent
  ): urt.MessageContent =
    urt.MessageContent.HeaderImagePrompt(
      urt.HeaderImagePrompt(
        headerImage = messageImageMarshaller(headerImagePromptMessageContent.headerImage),
        headerText = headerImagePromptMessageContent.headerText,
        bodyText = headerImagePromptMessageContent.bodyText,
        primaryButtonAction =
          headerImagePromptMessageContent.primaryButtonAction.map(messageTextActionMarshaller(_)),
        secondaryButtonAction =
          headerImagePromptMessageContent.secondaryButtonAction.map(messageTextActionMarshaller(_)),
        action = headerImagePromptMessageContent.action.map(messageActionMarshaller(_)),
        headerRichText = headerImagePromptMessageContent.headerRichText.map(richTextMarshaller(_)),
        bodyRichText = headerImagePromptMessageContent.bodyRichText.map(richTextMarshaller(_))
      )
    )
}
