try {
package com.twitter.product_mixer.core.model.marshalling.response.urp

sealed trait TopicPageHeaderDisplayType

case object BasicTopicPageHeaderDisplayType extends TopicPageHeaderDisplayType
case object PersonalizedTopicPageHeaderDisplayType extends TopicPageHeaderDisplayType

} catch {
  case e: Exception =>
}
