package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.topic

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.topic.TopicItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicItemMarshaller @Inject() (
  displayTypeMarshaller: TopicDisplayTypeMarshaller,
  functionalityTypeMarshaller: TopicFunctionalityTypeMarshaller) {

  def apply(topicItem: TopicItem): urt.TimelineItemContent = {
    urt.TimelineItemContent.Topic(
      urt.Topic(
        topicId = topicItem.id.toString,
        topicDisplayType = topicItem.topicDisplayType
          .map(displayTypeMarshaller(_)).getOrElse(urt.TopicDisplayType.Basic),
        topicFunctionalityType = topicItem.topicFunctionalityType
          .map(functionalityTypeMarshaller(_)).getOrElse(urt.TopicFunctionalityType.Basic),
        // This is currently not required by users of this library
        reactiveTriggers = None
      )
    )
  }
}
