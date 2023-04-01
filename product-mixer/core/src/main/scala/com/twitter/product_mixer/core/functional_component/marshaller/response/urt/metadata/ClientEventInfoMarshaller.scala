package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

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
