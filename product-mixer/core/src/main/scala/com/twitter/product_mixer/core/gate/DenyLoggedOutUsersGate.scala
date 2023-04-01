package com.twitter.product_mixer.core.gate

import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.gate.DenyLoggedOutUsersGate.Suffix
import com.twitter.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.pipeline_failure.Authentication
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.stitch.Stitch

case class DenyLoggedOutUsersGate(pipelineIdentifier: ComponentIdentifier)
    extends Gate[PipelineQuery] {
  override val identifier: GateIdentifier = GateIdentifier(pipelineIdentifier + Suffix)

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    if (query.getUserOrGuestId.nonEmpty) {
      Stitch.value(!query.isLoggedOut)
    } else {
      Stitch.exception(
        PipelineFailure(
          Authentication,
          "Expected either a `userId` (for logged in users) or `guestId` (for logged out users) but found neither"
        ))
    }
  }
}

object DenyLoggedOutUsersGate {
  val Suffix = "DenyLoggedOutUsers"
}
