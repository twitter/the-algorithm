package com.twitter.product_mitwitterer.component_library.gate

import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Gate used in first page. Use request cursor to determine if the gate should be open or closed.
 */
object FirstPageGate etwittertends Gate[PipelineQuery with HasPipelineCursor[_]] {

  override val identifier: GateIdentifier = GateIdentifier("FirstPage")

  // If cursor is first page, then gate should return continue, otherwise return stop
  override def shouldContinue(query: PipelineQuery with HasPipelineCursor[_]): Stitch[Boolean] =
    Stitch.value(query.isFirstPage)
}
