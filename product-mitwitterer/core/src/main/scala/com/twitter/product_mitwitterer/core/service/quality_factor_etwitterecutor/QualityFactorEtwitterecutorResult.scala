package com.twitter.product_mitwitterer.core.service.quality_factor_etwitterecutor

import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier

case class QualityFactorEtwitterecutorResult(
  pipelineQualityFactors: Map[ComponentIdentifier, Double])

object QualityFactorEtwitterecutorResult {
  val empty: QualityFactorEtwitterecutorResult = QualityFactorEtwitterecutorResult(Map.empty)
}
