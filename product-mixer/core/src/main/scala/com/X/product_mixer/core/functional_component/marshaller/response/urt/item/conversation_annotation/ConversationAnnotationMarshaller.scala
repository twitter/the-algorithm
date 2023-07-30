package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.conversation_annotation

import com.X.product_mixer.core.model.marshalling.response.urt.item.conversation_annotation.ConversationAnnotation
import com.X.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextMarshaller
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationAnnotationMarshaller @Inject() (
  conversationAnnotationTypeMarshaller: ConversationAnnotationTypeMarshaller,
  richTextMarshaller: RichTextMarshaller) {

  def apply(conversationAnnotation: ConversationAnnotation): urt.ConversationAnnotation = {
    urt.ConversationAnnotation(
      conversationAnnotationType =
        conversationAnnotationTypeMarshaller(conversationAnnotation.conversationAnnotationType),
      header = conversationAnnotation.header.map(richTextMarshaller(_)),
      description = conversationAnnotation.description.map(richTextMarshaller(_))
    )
  }
}
