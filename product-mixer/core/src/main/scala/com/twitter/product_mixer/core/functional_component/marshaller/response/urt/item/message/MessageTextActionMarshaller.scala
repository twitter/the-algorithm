package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.MessageTextAction
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageTextActionMarshaller @Inject() (
  messageActionMarshaller: MessageActionMarshaller) {

  def apply(messageTextAction: MessageTextAction): urt.MessageTextAction =
    urt.MessageTextAction(
      text = messageTextAction.text,
      action = messageActionMarshaller(messageTextAction.action)
    )
}
