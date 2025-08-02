package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.CompactPromptMessageContent
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CompactPromptMessageContentMarshaller @Inject() (
  messageTetwittertActionMarshaller: MessageTetwittertActionMarshaller,
  messageActionMarshaller: MessageActionMarshaller,
  richTetwittertMarshaller: RichTetwittertMarshaller) {

  def apply(compactPromptMessageContent: CompactPromptMessageContent): urt.MessageContent =
    urt.MessageContent.CompactPrompt(
      urt.CompactPrompt(
        headerTetwittert = compactPromptMessageContent.headerTetwittert,
        bodyTetwittert = compactPromptMessageContent.bodyTetwittert,
        primaryButtonAction =
          compactPromptMessageContent.primaryButtonAction.map(messageTetwittertActionMarshaller(_)),
        secondaryButtonAction =
          compactPromptMessageContent.secondaryButtonAction.map(messageTetwittertActionMarshaller(_)),
        action = compactPromptMessageContent.action.map(messageActionMarshaller(_)),
        headerRichTetwittert = compactPromptMessageContent.headerRichTetwittert.map(richTetwittertMarshaller(_)),
        bodyRichTetwittert = compactPromptMessageContent.bodyRichTetwittert.map(richTetwittertMarshaller(_))
      )
    )
}
