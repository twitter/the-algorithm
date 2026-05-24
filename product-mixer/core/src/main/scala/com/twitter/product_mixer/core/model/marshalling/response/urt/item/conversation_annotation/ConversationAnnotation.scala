package com.twitter.product_mixer.core.model.marshalling.response.urt.item.conversation_annotation

import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.RichText

case class ConversationAnnotation(
  conversationAnnotationType: ConversationAnnotationType,
  header: Option[RichText],
  description: Option[RichText])
