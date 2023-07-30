package com.X.product_mixer.core.gate

import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.product_mixer.core.model.common.identifier.GateIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch
import com.X.timelines.configapi.Param

case class ParamNotGate(name: String, param: Param[Boolean]) extends Gate[PipelineQuery] {
  override val identifier: GateIdentifier = GateIdentifier(name)

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] =
    Stitch.value(!query.params(param))
}
