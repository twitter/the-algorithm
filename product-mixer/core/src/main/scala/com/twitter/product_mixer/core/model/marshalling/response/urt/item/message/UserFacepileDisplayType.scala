try {
package com.twitter.product_mixer.core.model.marshalling.response.urt.item.message

sealed trait UserFacepileDisplayType

case object LargeUserFacepileDisplayType extends UserFacepileDisplayType
case object CompactUserFacepileDisplayType extends UserFacepileDisplayType

} catch {
  case e: Exception =>
}
