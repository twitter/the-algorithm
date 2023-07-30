package com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.BasicTopicContextFunctionalityType
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.RecWithEducationTopicContextFunctionalityType
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.RecommendationTopicContextFunctionalityType
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.TopicContextFunctionalityType
import com.X.timelines.render.{thriftscala => urt}

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
