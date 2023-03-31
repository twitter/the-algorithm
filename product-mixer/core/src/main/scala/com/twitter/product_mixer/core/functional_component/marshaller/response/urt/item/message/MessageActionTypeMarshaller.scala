package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.FollowAllMessageActionType
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.MessageActionType
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageActionTypeMarshaller @Inject() () {

  def apply(messageActionType: MessageActionType): urt.MessageActionType = messageActionType match {
    case FollowAllMessageActionType => urt.MessageActionType.FollowAll
  }
}
