package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.timelines.render.{thriftscala => urt}
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.GeneralContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.SocialContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.TopicContetwittert
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class SocialContetwittertMarshaller @Inject() (
  generalContetwittertMarshaller: GeneralContetwittertMarshaller,
  topicContetwittertMarshaller: TopicContetwittertMarshaller) {

  def apply(socialContetwittert: SocialContetwittert): urt.SocialContetwittert =
    socialContetwittert match {
      case generalContetwittertBanner: GeneralContetwittert =>
        generalContetwittertMarshaller(generalContetwittertBanner)
      case topicContetwittertBanner: TopicContetwittert =>
        topicContetwittertMarshaller(topicContetwittertBanner)
    }
}
