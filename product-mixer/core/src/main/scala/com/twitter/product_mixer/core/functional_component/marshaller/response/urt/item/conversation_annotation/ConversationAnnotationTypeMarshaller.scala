package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.conversation_annotation

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.conversation_annotation.ConversationAnnotationType
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.conversation_annotation.Large
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.conversation_annotation.Political

import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationAnnotationTypeMarshaller @Inject() () {

  def apply(
    conversationAnnotationType: ConversationAnnotationType
  ): urt.ConversationAnnotationType = conversationAnnotationType match {
    case Large => urt.ConversationAnnotationType.Large
    case Political => urt.ConversationAnnotationType.Political
  }
}
