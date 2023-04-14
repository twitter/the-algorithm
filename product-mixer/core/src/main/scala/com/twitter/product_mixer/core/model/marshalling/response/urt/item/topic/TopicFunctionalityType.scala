try {
package com.twitter.product_mixer.core.model.marshalling.response.urt.item.topic

sealed trait TopicFunctionalityType

case object BasicTopicFunctionalityType extends TopicFunctionalityType
case object PivotTopicFunctionalityType extends TopicFunctionalityType
case object RecommendationTopicFunctionalityType extends TopicFunctionalityType

} catch {
  case e: Exception =>
}
