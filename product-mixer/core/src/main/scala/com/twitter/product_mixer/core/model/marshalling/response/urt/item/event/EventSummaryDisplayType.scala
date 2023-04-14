try {
package com.twitter.product_mixer.core.model.marshalling.response.urt.item.event

sealed trait EventSummaryDisplayType

case object CellEventSummaryDisplayType extends EventSummaryDisplayType
case object HeroEventSummaryDisplayType extends EventSummaryDisplayType
case object CellWithProminentSocialContextEventSummaryDisplayType extends EventSummaryDisplayType

} catch {
  case e: Exception =>
}
