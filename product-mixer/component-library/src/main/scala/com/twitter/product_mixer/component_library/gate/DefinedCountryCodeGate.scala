package com.ExTwitter.product_mixer.component_library.gate

import com.ExTwitter.product_mixer.core.functional_component.gate.Gate
import com.ExTwitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch

object DefinedCountryCodeGate extends Gate[PipelineQuery] {
  override val identifier: GateIdentifier = GateIdentifier("DefinedCountryCode")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] =
    Stitch.value(query.getCountryCode.isDefined)
}
