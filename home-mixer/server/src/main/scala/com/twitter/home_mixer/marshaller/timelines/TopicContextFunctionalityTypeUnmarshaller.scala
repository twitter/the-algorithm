package com.twitter.home_mixer.marshaller.timelines

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.BasicTopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RecWithEducationTopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RecommendationTopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.TopicContextFunctionalityType
import com.twitter.timelines.render.{thriftscala => urt}

object TopicContextFunctionalityTypeUnmarshaller {

  def apply(
    topicContextFunctionalityType: urt.TopicContextFunctionalityType
  ): TopicContextFunctionalityType = topicContextFunctionalityType match {
    case urt.TopicContextFunctionalityType.Basic => BasicTopicContextFunctionalityType
    case urt.TopicContextFunctionalityType.Recommendation =>
      RecommendationTopicContextFunctionalityType
    case urt.TopicContextFunctionalityType.RecWithEducation =>
      RecWithEducationTopicContextFunctionalityType
    case urt.TopicContextFunctionalityType.EnumUnknownTopicContextFunctionalityType(field) =>
      throw new UnsupportedOperationException(s"Unknown topic context functionality type: $field")
  }
}
