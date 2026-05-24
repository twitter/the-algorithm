package com.twitter.product_mixer.core.pipeline

import com.twitter.product_mixer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mixer.core.service.ExecutorResult
import scala.collection.immutable.ListMap

sealed trait NewPipelineResult[-Result] {
  def executorResultsByPipelineStep: ListMap[PipelineStepIdentifier, ExecutorResult]
}

object NewPipelineResult {
  case class Failure(
    failure: PipelineFailure,
    override val executorResultsByPipelineStep: ListMap[PipelineStepIdentifier, ExecutorResult])
      extends NewPipelineResult[Any]

  case class Success[Result](
    result: Result,
    override val executorResultsByPipelineStep: ListMap[PipelineStepIdentifier, ExecutorResult])
      extends NewPipelineResult[Result]
}
