package com.twitter.product_mixer.core.functional_component.marshaller.response.urp

import com.twitter.pages.render.{thriftscala => urp}
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.ClientEventInfoMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urp.TopicPageNavBar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicPageNavBarMarshaller @Inject() (
  clientEventInfoMarshaller: ClientEventInfoMarshaller) {

  def apply(topicPageNavBar: TopicPageNavBar): urp.TopicPageNavBar =
    urp.TopicPageNavBar(
      topicId = topicPageNavBar.topicId,
      clientEventInfo = topicPageNavBar.clientEventInfo.map(clientEventInfoMarshaller(_))
    )
}
