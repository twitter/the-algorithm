package com.twitter.product_mitwitterer.core.pipeline.product

import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.PipelineResult
import com.twitter.product_mitwitterer.core.pipeline.mitwitterer.MitwittererPipelineResult
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.recommendation.RecommendationPipelineResult
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.pipeline_selector_etwitterecutor.PipelineSelectorEtwitterecutorResult
import com.twitter.product_mitwitterer.core.service.quality_factor_etwitterecutor.QualityFactorEtwitterecutorResult

case class ProductPipelineResult[Result](
  transformedQuery: Option[PipelineQuery],
  qualityFactorResult: Option[QualityFactorEtwitterecutorResult],
  gateResult: Option[GateEtwitterecutorResult],
  pipelineSelectorResult: Option[PipelineSelectorEtwitterecutorResult],
  mitwittererPipelineResult: Option[MitwittererPipelineResult[Result]],
  recommendationPipelineResult: Option[RecommendationPipelineResult[_, Result]],
  traceId: Option[String],
  failure: Option[PipelineFailure],
  result: Option[Result])
    etwittertends PipelineResult[Result] {

  override val resultSize: Int = {
    if (mitwittererPipelineResult.isDefined) {
      mitwittererPipelineResult.map(_.resultSize).getOrElse(0)
    } else {
      recommendationPipelineResult.map(_.resultSize).getOrElse(0)
    }
  }

  override def withFailure(failure: PipelineFailure): PipelineResult[Result] =
    copy(failure = Some(failure))

  override def withResult(result: Result): PipelineResult[Result] = copy(result = Some(result))
}

object ProductPipelineResult {
  def empty[A]: ProductPipelineResult[A] = ProductPipelineResult(
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None
  )

  def fromResult[A](result: A): ProductPipelineResult[A] = ProductPipelineResult(
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    Some(result)
  )
}
