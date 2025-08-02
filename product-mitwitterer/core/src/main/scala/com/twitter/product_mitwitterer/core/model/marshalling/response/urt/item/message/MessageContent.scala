package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.SocialContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert

sealed trait MessageContent

case class InlinePromptMessageContent(
  headerTetwittert: String,
  bodyTetwittert: Option[String],
  primaryButtonAction: Option[MessageTetwittertAction],
  secondaryButtonAction: Option[MessageTetwittertAction],
  headerRichTetwittert: Option[RichTetwittert],
  bodyRichTetwittert: Option[RichTetwittert],
  socialContetwittert: Option[SocialContetwittert],
  userFacepile: Option[UserFacepile])
    etwittertends MessageContent

case class HeaderImagePromptMessageContent(
  headerImage: MessageImage,
  headerTetwittert: Option[String],
  bodyTetwittert: Option[String],
  primaryButtonAction: Option[MessageTetwittertAction],
  secondaryButtonAction: Option[MessageTetwittertAction],
  action: Option[MessageAction],
  headerRichTetwittert: Option[RichTetwittert],
  bodyRichTetwittert: Option[RichTetwittert])
    etwittertends MessageContent

case class CompactPromptMessageContent(
  headerTetwittert: String,
  bodyTetwittert: Option[String],
  primaryButtonAction: Option[MessageTetwittertAction],
  secondaryButtonAction: Option[MessageTetwittertAction],
  action: Option[MessageAction],
  headerRichTetwittert: Option[RichTetwittert],
  bodyRichTetwittert: Option[RichTetwittert])
    etwittertends MessageContent
