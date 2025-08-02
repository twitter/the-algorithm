package com.twitter.product_mitwitterer.core.pipeline

import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.state.HasEtwitterecutorResults

case class NewStepData[State <: HasEtwitterecutorResults[State]](
  pipelineState: State,
  pipelineFailure: Option[PipelineFailure] = None) {

  val stopEtwitterecuting = pipelineFailure.isDefined
  def withFailure(failure: PipelineFailure): NewStepData[State] =
    this.copy(pipelineFailure = Some(failure))
}
