package com.twitter.product_mixer.core.pipeline.state

import com.twitter.product_mixer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mixer.core.service.ExecutorResult
import scala.collection.immutable.ListMap

trait HasExecutorResults[State] {
  // We use a list map to maintain the insertion order
  val executorResultsByPipelineStep: ListMap[PipelineStepIdentifier, ExecutorResult]
  private[pipeline] def setExecutorResults(
    newMap: ListMap[PipelineStepIdentifier, ExecutorResult]
  ): State
}
