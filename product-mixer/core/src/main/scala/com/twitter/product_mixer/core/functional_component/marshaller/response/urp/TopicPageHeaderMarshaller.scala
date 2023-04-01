package com.twitter.product_mixer.core.functional_component.marshaller.response.urp

import com.twitter.pages.render.{thriftscala => urp}
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.ClientEventInfoMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urp.TopicPageHeader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicPageHeaderMarshaller @Inject() (
  topicPageHeaderFacepileMarshaller: TopicPageHeaderFacepileMarshaller,
  clientEventInfoMarshaller: ClientEventInfoMarshaller,
  topicPageHeaderDisplayTypeMarshaller: TopicPageHeaderDisplayTypeMarshaller) {

  def apply(topicPageHeader: TopicPageHeader): urp.TopicPageHeader =
    urp.TopicPageHeader(
      topicId = topicPageHeader.topicId,
      facepile = topicPageHeader.facepile.map(topicPageHeaderFacepileMarshaller(_)),
      clientEventInfo = topicPageHeader.clientEventInfo.map(clientEventInfoMarshaller(_)),
      landingContext = topicPageHeader.landingContext,
      displayType = topicPageHeader.displayType
        .map(topicPageHeaderDisplayTypeMarshaller(_)).getOrElse(
          urp.TopicPageHeaderDisplayType.Basic)
    )
}
