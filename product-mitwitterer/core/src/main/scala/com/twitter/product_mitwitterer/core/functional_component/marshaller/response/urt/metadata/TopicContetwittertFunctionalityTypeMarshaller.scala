package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.BasicTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RecWithEducationTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RecommendationTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.TopicContetwittertFunctionalityType
import com.twitter.timelines.render.{thriftscala => urt}

object TopicContetwittertFunctionalityTypeMarshaller {

  def apply(
    topicContetwittertFunctionalityType: TopicContetwittertFunctionalityType
  ): urt.TopicContetwittertFunctionalityType = topicContetwittertFunctionalityType match {
    case BasicTopicContetwittertFunctionalityType => urt.TopicContetwittertFunctionalityType.Basic
    case RecommendationTopicContetwittertFunctionalityType =>
      urt.TopicContetwittertFunctionalityType.Recommendation
    case RecWithEducationTopicContetwittertFunctionalityType =>
      urt.TopicContetwittertFunctionalityType.RecWithEducation
  }
}
