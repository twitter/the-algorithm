package com.X.product_mixer.core.gate

import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.product_mixer.core.model.common.identifier.GateIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch
import com.X.timelines.configapi.Param

case class ParamGate(name: String, param: Param[Boolean])(implicit file: sourcecode.File)
    extends Gate[PipelineQuery] {

  // From a customer-perspective, it's more useful to see the file that created the ParamGate
  override val identifier: GateIdentifier = GateIdentifier(name)(file)

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] =
    Stitch.value(query.params(param))
}

object ParamGate {
  val EnabledGateSuffix = "Enabled"
  val SupportedClientGateSuffix = "SupportedClient"
}
