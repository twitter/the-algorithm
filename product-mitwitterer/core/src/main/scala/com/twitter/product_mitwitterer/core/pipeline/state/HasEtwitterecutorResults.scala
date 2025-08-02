package com.twitter.product_mitwitterer.core.pipeline.state

import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult
import scala.collection.immutable.ListMap

trait HasEtwitterecutorResults[State] {
  // We use a list map to maintain the insertion order
  val etwitterecutorResultsByPipelineStep: ListMap[PipelineStepIdentifier, EtwitterecutorResult]
  private[pipeline] def setEtwitterecutorResults(
    newMap: ListMap[PipelineStepIdentifier, EtwitterecutorResult]
  ): State
}
