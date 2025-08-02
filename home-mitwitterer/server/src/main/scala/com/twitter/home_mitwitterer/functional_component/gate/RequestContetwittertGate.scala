package com.twitter.home_mitwitterer.functional_component.gate

import com.twitter.home_mitwitterer.model.request.DeviceContetwittert.RequestContetwittert
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Gate that fetches the request contetwittert from the device contetwittert and
 * continues if the request contetwittert matches *any* of the specified ones.
 */
case class RequestContetwittertGate(requestContetwitterts: Seq[RequestContetwittert.Value])
    etwittertends Gate[PipelineQuery with HasDeviceContetwittert] {

  override val identifier: GateIdentifier = GateIdentifier("RequestContetwittert")

  override def shouldContinue(query: PipelineQuery with HasDeviceContetwittert): Stitch[Boolean] =
    Stitch.value(
      requestContetwitterts.etwitterists(query.deviceContetwittert.flatMap(_.requestContetwittertValue).contains))
}
