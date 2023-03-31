package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.SocialContextMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.InlinePromptMessageContent
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InlinePromptMessageContentMarshaller @Inject() (
  messageTextActionMarshaller: MessageTextActionMarshaller,
  richTextMarshaller: RichTextMarshaller,
  socialContextMarshaller: SocialContextMarshaller,
  userFacepileMarshaller: UserFacepileMarshaller) {

  def apply(inlinePromptMessageContent: InlinePromptMessageContent): urt.MessageContent =
    urt.MessageContent.InlinePrompt(
      urt.InlinePrompt(
        headerText = inlinePromptMessageContent.headerText,
        bodyText = inlinePromptMessageContent.bodyText,
        primaryButtonAction =
          inlinePromptMessageContent.primaryButtonAction.map(messageTextActionMarshaller(_)),
        secondaryButtonAction =
          inlinePromptMessageContent.secondaryButtonAction.map(messageTextActionMarshaller(_)),
        headerRichText = inlinePromptMessageContent.headerRichText.map(richTextMarshaller(_)),
        bodyRichText = inlinePromptMessageContent.bodyRichText.map(richTextMarshaller(_)),
        socialContext = inlinePromptMessageContent.socialContext.map(socialContextMarshaller(_)),
        userFacepile = inlinePromptMessageContent.userFacepile.map(userFacepileMarshaller(_))
      )
    )
}
