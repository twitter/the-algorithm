package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.conversation_annotation

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.conversation_annotation.ConversationAnnotation
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ConversationAnnotationMarshaller @Inject() (
  conversationAnnotationTypeMarshaller: ConversationAnnotationTypeMarshaller,
  richTetwittertMarshaller: RichTetwittertMarshaller) {

  def apply(conversationAnnotation: ConversationAnnotation): urt.ConversationAnnotation = {
    urt.ConversationAnnotation(
      conversationAnnotationType =
        conversationAnnotationTypeMarshaller(conversationAnnotation.conversationAnnotationType),
      header = conversationAnnotation.header.map(richTetwittertMarshaller(_)),
      description = conversationAnnotation.description.map(richTetwittertMarshaller(_))
    )
  }
}
