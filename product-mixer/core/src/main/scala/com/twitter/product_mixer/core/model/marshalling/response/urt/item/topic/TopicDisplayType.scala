try {
package com.twitter.product_mixer.core.model.marshalling.response.urt.item.topic

sealed trait TopicDisplayType

case object BasicTopicDisplayType extends TopicDisplayType
case object PillTopicDisplayType extends TopicDisplayType
case object NoIconTopicDisplayType extends TopicDisplayType
case object PillWithoutActionIconDisplayType extends TopicDisplayType

} catch {
  case e: Exception =>
}
