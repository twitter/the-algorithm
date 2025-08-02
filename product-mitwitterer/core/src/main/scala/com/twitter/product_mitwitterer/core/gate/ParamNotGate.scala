package com.twitter.product_mitwitterer.core.gate

import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Param

case class ParamNotGate(name: String, param: Param[Boolean]) etwittertends Gate[PipelineQuery] {
  override val identifier: GateIdentifier = GateIdentifier(name)

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] =
    Stitch.value(!query.params(param))
}
