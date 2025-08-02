package com.twitter.product_mitwitterer.core.pipeline

import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult
import scala.collection.immutable.ListMap

sealed trait NewPipelineResult[-Result] {
  def etwitterecutorResultsByPipelineStep: ListMap[PipelineStepIdentifier, EtwitterecutorResult]
}

object NewPipelineResult {
  case class Failure(
    failure: PipelineFailure,
    override val etwitterecutorResultsByPipelineStep: ListMap[PipelineStepIdentifier, EtwitterecutorResult])
      etwittertends NewPipelineResult[Any]

  case class Success[Result](
    result: Result,
    override val etwitterecutorResultsByPipelineStep: ListMap[PipelineStepIdentifier, EtwitterecutorResult])
      etwittertends NewPipelineResult[Result]
}
