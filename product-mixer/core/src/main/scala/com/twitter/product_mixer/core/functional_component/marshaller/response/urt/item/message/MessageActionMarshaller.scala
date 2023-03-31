package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.CallbackMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.ClientEventInfoMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.MessageAction
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageActionMarshaller @Inject() (
  callbackMarshaller: CallbackMarshaller,
  clientEventInfoMarshaller: ClientEventInfoMarshaller) {

  def apply(messageAction: MessageAction): urt.MessageAction = {

    urt.MessageAction(
      dismissOnClick = messageAction.dismissOnClick,
      url = messageAction.url,
      clientEventInfo = messageAction.clientEventInfo.map(clientEventInfoMarshaller(_)),
      onClickCallbacks =
        messageAction.onClickCallbacks.map(callbackList => callbackList.map(callbackMarshaller(_)))
    )
  }
}
