package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.BasicTopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RecWithEducationTopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RecommendationTopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.TopicContextFunctionalityType
import com.twitter.timelines.render.{thriftscala => urt}

object TopicContextFunctionalityTypeMarshaller {

  def apply(
    topicContextFunctionalityType: TopicContextFunctionalityType
  ): urt.TopicContextFunctionalityType = topicContextFunctionalityType match {
    case BasicTopicContextFunctionalityType => urt.TopicContextFunctionalityType.Basic
    case RecommendationTopicContextFunctionalityType =>
      urt.TopicContextFunctionalityType.Recommendation
    case RecWithEducationTopicContextFunctionalityType =>
      urt.TopicContextFunctionalityType.RecWithEducation
  }
}
