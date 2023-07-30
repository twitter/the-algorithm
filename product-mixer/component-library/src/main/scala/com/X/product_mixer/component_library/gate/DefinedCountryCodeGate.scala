package com.X.product_mixer.component_library.gate

import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.product_mixer.core.model.common.identifier.GateIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

object DefinedCountryCodeGate extends Gate[PipelineQuery] {
  override val identifier: GateIdentifier = GateIdentifier("DefinedCountryCode")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] =
    Stitch.value(query.getCountryCode.isDefined)
}
