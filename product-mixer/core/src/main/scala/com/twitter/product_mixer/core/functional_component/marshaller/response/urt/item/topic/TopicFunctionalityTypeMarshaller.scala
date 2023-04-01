package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.topic

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.topic.BasicTopicFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.topic.PivotTopicFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.topic.RecommendationTopicFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.topic.TopicFunctionalityType
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicFunctionalityTypeMarshaller @Inject() () {

  def apply(topicFunctionalityType: TopicFunctionalityType): urt.TopicFunctionalityType =
    topicFunctionalityType match {
      case BasicTopicFunctionalityType => urt.TopicFunctionalityType.Basic
      case RecommendationTopicFunctionalityType => urt.TopicFunctionalityType.Recommendation
      case PivotTopicFunctionalityType => urt.TopicFunctionalityType.Pivot
    }
}
