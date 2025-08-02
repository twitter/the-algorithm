package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.MessageTetwittertAction
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class MessageTetwittertActionMarshaller @Inject() (
  messageActionMarshaller: MessageActionMarshaller) {

  def apply(messageTetwittertAction: MessageTetwittertAction): urt.MessageTetwittertAction =
    urt.MessageTetwittertAction(
      tetwittert = messageTetwittertAction.tetwittert,
      action = messageActionMarshaller(messageTetwittertAction.action)
    )
}
