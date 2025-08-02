package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.SocialContetwittertMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.InlinePromptMessageContent
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class InlinePromptMessageContentMarshaller @Inject() (
  messageTetwittertActionMarshaller: MessageTetwittertActionMarshaller,
  richTetwittertMarshaller: RichTetwittertMarshaller,
  socialContetwittertMarshaller: SocialContetwittertMarshaller,
  userFacepileMarshaller: UserFacepileMarshaller) {

  def apply(inlinePromptMessageContent: InlinePromptMessageContent): urt.MessageContent =
    urt.MessageContent.InlinePrompt(
      urt.InlinePrompt(
        headerTetwittert = inlinePromptMessageContent.headerTetwittert,
        bodyTetwittert = inlinePromptMessageContent.bodyTetwittert,
        primaryButtonAction =
          inlinePromptMessageContent.primaryButtonAction.map(messageTetwittertActionMarshaller(_)),
        secondaryButtonAction =
          inlinePromptMessageContent.secondaryButtonAction.map(messageTetwittertActionMarshaller(_)),
        headerRichTetwittert = inlinePromptMessageContent.headerRichTetwittert.map(richTetwittertMarshaller(_)),
        bodyRichTetwittert = inlinePromptMessageContent.bodyRichTetwittert.map(richTetwittertMarshaller(_)),
        socialContetwittert = inlinePromptMessageContent.socialContetwittert.map(socialContetwittertMarshaller(_)),
        userFacepile = inlinePromptMessageContent.userFacepile.map(userFacepileMarshaller(_))
      )
    )
}
