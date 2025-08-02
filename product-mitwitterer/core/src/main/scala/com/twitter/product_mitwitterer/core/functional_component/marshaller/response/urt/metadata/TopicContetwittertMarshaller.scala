package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.BasicTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.TopicContetwittert
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TopicContetwittertMarshaller @Inject() () {

  def apply(topicContetwittert: TopicContetwittert): urt.SocialContetwittert = {
    urt.SocialContetwittert.TopicContetwittert(
      urt.TopicContetwittert(
        topicId = topicContetwittert.topicId,
        functionalityType = TopicContetwittertFunctionalityTypeMarshaller(
          topicContetwittert.functionalityType.getOrElse(BasicTopicContetwittertFunctionalityType))
      )
    )
  }
}
