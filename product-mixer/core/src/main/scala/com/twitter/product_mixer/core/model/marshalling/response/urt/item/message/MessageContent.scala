package com.twitter.product_mixer.core.model.marshalling.response.urt.item.message

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.SocialContext
import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.RichText

sealed trait MessageContent

case class InlinePromptMessageContent(
  headerText: String,
  bodyText: Option[String],
  primaryButtonAction: Option[MessageTextAction],
  secondaryButtonAction: Option[MessageTextAction],
  headerRichText: Option[RichText],
  bodyRichText: Option[RichText],
  socialContext: Option[SocialContext],
  userFacepile: Option[UserFacepile])
    extends MessageContent

case class HeaderImagePromptMessageContent(
  headerImage: MessageImage,
  headerText: Option[String],
  bodyText: Option[String],
  primaryButtonAction: Option[MessageTextAction],
  secondaryButtonAction: Option[MessageTextAction],
  action: Option[MessageAction],
  headerRichText: Option[RichText],
  bodyRichText: Option[RichText])
    extends MessageContent

case class CompactPromptMessageContent(
  headerText: String,
  bodyText: Option[String],
  primaryButtonAction: Option[MessageTextAction],
  secondaryButtonAction: Option[MessageTextAction],
  action: Option[MessageAction],
  headerRichText: Option[RichText],
  bodyRichText: Option[RichText])
    extends MessageContent
