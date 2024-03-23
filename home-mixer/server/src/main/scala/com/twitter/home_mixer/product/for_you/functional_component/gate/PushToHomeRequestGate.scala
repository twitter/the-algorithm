package com.ExTwitter.home_mixer.product.for_you.functional_component.gate

import com.ExTwitter.home_mixer.product.for_you.model.ForYouQuery
import com.ExTwitter.product_mixer.core.functional_component.gate.Gate
import com.ExTwitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.ExTwitter.stitch.Stitch

/**
 * Continues when the request is a Push-To-Home notification request
 */
object PushToHomeRequestGate extends Gate[ForYouQuery] {
  override val identifier: GateIdentifier = GateIdentifier("PushToHomeRequest")

  override def shouldContinue(query: ForYouQuery): Stitch[Boolean] =
    Stitch.value(query.pushToHomeTweetId.isDefined)
}
