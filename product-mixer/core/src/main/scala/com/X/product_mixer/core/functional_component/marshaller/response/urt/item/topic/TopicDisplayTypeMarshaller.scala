package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.topic

import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.BasicTopicDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.NoIconTopicDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.PillTopicDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.PillWithoutActionIconDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.TopicDisplayType
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicDisplayTypeMarshaller @Inject() () {

  def apply(topicDisplayType: TopicDisplayType): urt.TopicDisplayType = topicDisplayType match {
    case BasicTopicDisplayType => urt.TopicDisplayType.Basic
    case PillTopicDisplayType => urt.TopicDisplayType.Pill
    case NoIconTopicDisplayType => urt.TopicDisplayType.NoIcon
    case PillWithoutActionIconDisplayType => urt.TopicDisplayType.PillWithoutActionIcon
  }
}
