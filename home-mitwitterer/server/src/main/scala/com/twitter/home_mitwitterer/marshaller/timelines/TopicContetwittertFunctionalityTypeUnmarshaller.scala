package com.twitter.home_mitwitterer.marshaller.timelines

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.BasicTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RecWithEducationTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RecommendationTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.TopicContetwittertFunctionalityType
import com.twitter.timelines.render.{thriftscala => urt}

object TopicContetwittertFunctionalityTypeUnmarshaller {

  def apply(
    topicContetwittertFunctionalityType: urt.TopicContetwittertFunctionalityType
  ): TopicContetwittertFunctionalityType = topicContetwittertFunctionalityType match {
    case urt.TopicContetwittertFunctionalityType.Basic => BasicTopicContetwittertFunctionalityType
    case urt.TopicContetwittertFunctionalityType.Recommendation =>
      RecommendationTopicContetwittertFunctionalityType
    case urt.TopicContetwittertFunctionalityType.RecWithEducation =>
      RecWithEducationTopicContetwittertFunctionalityType
    case urt.TopicContetwittertFunctionalityType.EnumUnknownTopicContetwittertFunctionalityType(field) =>
      throw new UnsupportedOperationEtwitterception(s"Unknown topic contetwittert functionality type: $field")
  }
}
