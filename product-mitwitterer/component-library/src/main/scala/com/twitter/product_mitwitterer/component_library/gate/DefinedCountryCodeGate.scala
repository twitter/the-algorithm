package com.twitter.product_mitwitterer.component_library.gate

import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object DefinedCountryCodeGate etwittertends Gate[PipelineQuery] {
  override val identifier: GateIdentifier = GateIdentifier("DefinedCountryCode")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] =
    Stitch.value(query.getCountryCode.isDefined)
}
