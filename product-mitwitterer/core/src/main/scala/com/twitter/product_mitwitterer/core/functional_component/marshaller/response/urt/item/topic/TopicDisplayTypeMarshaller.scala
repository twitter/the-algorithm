package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.topic

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.topic.BasicTopicDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.topic.NoIconTopicDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.topic.PillTopicDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.topic.PillWithoutActionIconDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.topic.TopicDisplayType
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TopicDisplayTypeMarshaller @Inject() () {

  def apply(topicDisplayType: TopicDisplayType): urt.TopicDisplayType = topicDisplayType match {
    case BasicTopicDisplayType => urt.TopicDisplayType.Basic
    case PillTopicDisplayType => urt.TopicDisplayType.Pill
    case NoIconTopicDisplayType => urt.TopicDisplayType.NoIcon
    case PillWithoutActionIconDisplayType => urt.TopicDisplayType.PillWithoutActionIcon
  }
}
