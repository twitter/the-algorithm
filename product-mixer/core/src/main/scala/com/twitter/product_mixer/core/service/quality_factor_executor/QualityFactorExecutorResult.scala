package com.twitter.product_mixer.core.service.quality_factor_executor

import com.twitter.product_mixer.core.model.common.identifier.ComponentIdentifier

case class QualityFactorExecutorResult(
  pipelineQualityFactors: Map[ComponentIdentifier, Double])

object QualityFactorExecutorResult {
  val empty: QualityFactorExecutorResult = QualityFactorExecutorResult(Map.empty)
}
