package com.X.product_mixer.core.functional_component.marshaller.response.urp

import com.X.pages.render.{thriftscala => urp}
import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.X.product_mixer.core.model.marshalling.response.urp.TopicPageHeaderFacepile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicPageHeaderFacepileMarshaller @Inject() (
  urlMarshaller: UrlMarshaller) {

  def apply(topicPageHeaderFacepile: TopicPageHeaderFacepile): urp.TopicPageHeaderFacepile =
    urp.TopicPageHeaderFacepile(
      userIds = topicPageHeaderFacepile.userIds,
      facepileUrl = topicPageHeaderFacepile.facepileUrl.map(urlMarshaller(_))
    )
}
