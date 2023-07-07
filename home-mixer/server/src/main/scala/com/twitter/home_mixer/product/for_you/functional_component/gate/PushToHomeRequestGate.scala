package com.twitter.home_mixer.product.for_you.functional_component.gate

import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.stitch.Stitch

/**
 * Continues when the request is a Push-To-Home notification request
 */
object PushToHomeRequestGate extends Gate[ForYouQuery] {
  override val identifier: GateIdentifier = GateIdentifier("PushToHomeRequest")

  override def shouldContinue(query: ForYouQuery): Stitch[Boolean] =
    Stitch.value(query.pushToHomeTweetId.isDefined)
}
