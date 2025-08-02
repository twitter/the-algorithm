package com.twitter.product_mitwitterer.core.service.gate_etwitterecutor

import com.twitter.product_mitwitterer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailureCategory
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailureClassifier

import scala.util.control.NoStackTrace

case class StoppedGateEtwitterception(identifier: GateIdentifier)
    etwittertends Etwitterception("Closed gate stopped etwitterecution of the pipeline")
    with NoStackTrace {
  override def toString: String = s"StoppedGateEtwitterception($identifier)"
}

object StoppedGateEtwitterception {

  /**
   * Creates a [[PipelineFailureClassifier]] that is used as the default for classifying failures
   * in a pipeline by mapping [[StoppedGateEtwitterception]] to a [[PipelineFailure]] with the provided
   * [[PipelineFailureCategory]]
   */
  def classifier(
    category: PipelineFailureCategory
  ): PipelineFailureClassifier = PipelineFailureClassifier {
    case stoppedGateEtwitterception: StoppedGateEtwitterception =>
      PipelineFailure(category, stoppedGateEtwitterception.getMessage, Some(stoppedGateEtwitterception))
  }
}
