try {
package com.twitter.product_mixer.core.model.marshalling.response.urt.item.topic

sealed trait TopicFollowPromptDisplayType

case object IncentiveFocusTopicFollowPromptDisplayType extends TopicFollowPromptDisplayType
case object TopicFocusTopicFollowPromptDisplayType extends TopicFollowPromptDisplayType

} catch {
  case e: Exception =>
}
