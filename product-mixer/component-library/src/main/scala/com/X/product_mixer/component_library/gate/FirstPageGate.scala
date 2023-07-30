package com.X.product_mixer.component_library.gate

import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.product_mixer.core.model.common.identifier.GateIdentifier
import com.X.product_mixer.core.pipeline.HasPipelineCursor
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

/**
 * Gate used in first page. Use request cursor to determine if the gate should be open or closed.
 */
object FirstPageGate extends Gate[PipelineQuery with HasPipelineCursor[_]] {

  override val identifier: GateIdentifier = GateIdentifier("FirstPage")

  // If cursor is first page, then gate should return continue, otherwise return stop
  override def shouldContinue(query: PipelineQuery with HasPipelineCursor[_]): Stitch[Boolean] =
    Stitch.value(query.isFirstPage)
}
