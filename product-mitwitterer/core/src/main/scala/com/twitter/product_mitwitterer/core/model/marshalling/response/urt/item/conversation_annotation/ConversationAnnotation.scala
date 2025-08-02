package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.conversation_annotation

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert

case class ConversationAnnotation(
  conversationAnnotationType: ConversationAnnotationType,
  header: Option[RichTetwittert],
  description: Option[RichTetwittert])
