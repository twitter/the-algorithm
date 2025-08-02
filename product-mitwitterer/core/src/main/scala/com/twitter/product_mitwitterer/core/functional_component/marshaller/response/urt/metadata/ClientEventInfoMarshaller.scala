package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ClientEventInfoMarshaller @Inject() (
  clientEventDetailsMarshaller: ClientEventDetailsMarshaller) {

  def apply(clientEventInfo: ClientEventInfo): urt.ClientEventInfo = {
    urt.ClientEventInfo(
      component = clientEventInfo.component,
      element = clientEventInfo.element,
      details = clientEventInfo.details.map(clientEventDetailsMarshaller(_)),
      action = clientEventInfo.action,
      entityToken = clientEventInfo.entityToken
    )
  }
}
