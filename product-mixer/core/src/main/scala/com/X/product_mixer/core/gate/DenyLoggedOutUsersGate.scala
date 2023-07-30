package com.X.product_mixer.core.gate

import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.product_mixer.core.gate.DenyLoggedOutUsersGate.Suffix
import com.X.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.X.product_mixer.core.model.common.identifier.GateIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.pipeline.pipeline_failure.Authentication
import com.X.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.X.stitch.Stitch

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
