package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.HeaderImagePromptMessageContent
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class HeaderImagePromptMessageContentMarshaller @Inject() (
  messageImageMarshaller: MessageImageMarshaller,
  messageTetwittertActionMarshaller: MessageTetwittertActionMarshaller,
  messageActionMarshaller: MessageActionMarshaller,
  richTetwittertMarshaller: RichTetwittertMarshaller) {

  def apply(
    headerImagePromptMessageContent: HeaderImagePromptMessageContent
  ): urt.MessageContent =
    urt.MessageContent.HeaderImagePrompt(
      urt.HeaderImagePrompt(
        headerImage = messageImageMarshaller(headerImagePromptMessageContent.headerImage),
        headerTetwittert = headerImagePromptMessageContent.headerTetwittert,
        bodyTetwittert = headerImagePromptMessageContent.bodyTetwittert,
        primaryButtonAction =
          headerImagePromptMessageContent.primaryButtonAction.map(messageTetwittertActionMarshaller(_)),
        secondaryButtonAction =
          headerImagePromptMessageContent.secondaryButtonAction.map(messageTetwittertActionMarshaller(_)),
        action = headerImagePromptMessageContent.action.map(messageActionMarshaller(_)),
        headerRichTetwittert = headerImagePromptMessageContent.headerRichTetwittert.map(richTetwittertMarshaller(_)),
        bodyRichTetwittert = headerImagePromptMessageContent.bodyRichTetwittert.map(richTetwittertMarshaller(_))
      )
    )
}
