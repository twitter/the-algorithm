try {
package com.twitter.product_mixer.core.model.marshalling.response.urt.cover

sealed trait HalfCoverDisplayType

case object CoverHalfCoverDisplayType extends HalfCoverDisplayType
case object CenterCoverHalfCoverDisplayType extends HalfCoverDisplayType

} catch {
  case e: Exception =>
}
