package com.twitter.product_mitwitterer.core.gate

import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.gate.DenyLoggedOutUsersGate.Suffitwitter
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.Authentication
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.stitch.Stitch

case class DenyLoggedOutUsersGate(pipelineIdentifier: ComponentIdentifier)
    etwittertends Gate[PipelineQuery] {
  override val identifier: GateIdentifier = GateIdentifier(pipelineIdentifier + Suffitwitter)

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    if (query.getUserOrGuestId.nonEmpty) {
      Stitch.value(!query.isLoggedOut)
    } else {
      Stitch.etwitterception(
        PipelineFailure(
          Authentication,
          "Etwitterpected either a `userId` (for logged in users) or `guestId` (for logged out users) but found neither"
        ))
    }
  }
}

object DenyLoggedOutUsersGate {
  val Suffitwitter = "DenyLoggedOutUsers"
}
