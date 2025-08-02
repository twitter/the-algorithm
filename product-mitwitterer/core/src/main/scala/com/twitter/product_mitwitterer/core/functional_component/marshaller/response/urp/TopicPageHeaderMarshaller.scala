package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urp

import com.twitter.pages.render.{thriftscala => urp}
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.ClientEventInfoMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.TopicPageHeader
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

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
      landingContetwittert = topicPageHeader.landingContetwittert,
      displayType = topicPageHeader.displayType
        .map(topicPageHeaderDisplayTypeMarshaller(_)).getOrElse(
          urp.TopicPageHeaderDisplayType.Basic)
    )
}
