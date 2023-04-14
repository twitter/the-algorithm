try {
package com.twitter.product_mixer.core.model.marshalling.response.urt.item.forward_pivot

sealed trait SoftInterventionDisplayType

case object GetTheLatest extends SoftInterventionDisplayType
case object StayInformed extends SoftInterventionDisplayType
case object Misleading extends SoftInterventionDisplayType
case object GovernmentRequested extends SoftInterventionDisplayType

} catch {
  case e: Exception =>
}
