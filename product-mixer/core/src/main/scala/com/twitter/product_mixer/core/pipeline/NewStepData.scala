package com.twitter.product_mixer.core.pipeline

import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mixer.core.pipeline.state.HasExecutorResults

case class NewStepData[State <: HasExecutorResults[State]](
  pipelineState: State,
  pipelineFailure: Option[PipelineFailure] = None) {

  val stopExecuting = pipelineFailure.isDefined
  def withFailure(failure: PipelineFailure): NewStepData[State] =
    this.copy(pipelineFailure = Some(failure))
}
