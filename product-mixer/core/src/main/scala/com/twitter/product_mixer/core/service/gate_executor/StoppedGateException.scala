package com.twitter.product_mixer.core.service.gate_executor

import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailureCategory
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailureClassifier

import scala.util.control.NoStackTrace

case class StoppedGateException(identifier: GateIdentifier)
    extends Exception("Closed gate stopped execution of the pipeline")
    with NoStackTrace {
  override def toString: String = s"StoppedGateException($identifier)"
}

object StoppedGateException {

  /**
   * Creates a [[PipelineFailureClassifier]] that is used as the default for classifying failures
   * in a pipeline by mapping [[StoppedGateException]] to a [[PipelineFailure]] with the provided
   * [[PipelineFailureCategory]]
   */
  def classifier(
    category: PipelineFailureCategory
  ): PipelineFailureClassifier = PipelineFailureClassifier {
    case stoppedGateException: StoppedGateException =>
      PipelineFailure(category, stoppedGateException.getMessage, Some(stoppedGateException))
  }
}
