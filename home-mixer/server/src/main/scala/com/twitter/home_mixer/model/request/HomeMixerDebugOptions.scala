try {
package com.twitter.home_mixer.model.request

import com.twitter.product_mixer.core.model.marshalling.request.DebugOptions
import com.twitter.util.Time

case class HomeMixerDebugOptions(
  override val requestTimeOverride: Option[Time])
    extends DebugOptions

} catch {
  case e: Exception =>
}
