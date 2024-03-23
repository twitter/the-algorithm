package com.ExTwitter.home_mixer.functional_component.gate

import com.ExTwitter.home_mixer.model.request.DeviceContext.RequestContext
import com.ExTwitter.home_mixer.model.request.HasDeviceContext
import com.ExTwitter.product_mixer.core.functional_component.gate.Gate
import com.ExTwitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch

/**
 * Gate that fetches the request context from the device context and
 * continues if the request context does not match any of the specified ones.
 *
 * If no input request context is specified, the gate continues
 */
case class RequestContextNotGate(requestContexts: Seq[RequestContext.Value])
    extends Gate[PipelineQuery with HasDeviceContext] {

  override val identifier: GateIdentifier = GateIdentifier("RequestContextNot")

  override def shouldContinue(query: PipelineQuery with HasDeviceContext): Stitch[Boolean] =
    Stitch.value(
      !requestContexts.exists(query.deviceContext.flatMap(_.requestContextValue).contains))
}
