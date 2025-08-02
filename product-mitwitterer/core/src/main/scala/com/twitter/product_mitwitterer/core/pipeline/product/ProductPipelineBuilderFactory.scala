package com.twitter.product_mitwitterer.core.pipeline.product

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.model.marshalling.request.Request
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.mitwitterer.MitwittererPipelineBuilderFactory
import com.twitter.product_mitwitterer.core.pipeline.recommendation.RecommendationPipelineBuilderFactory
import com.twitter.product_mitwitterer.core.service.gate_etwitterecutor.GateEtwitterecutor
import com.twitter.product_mitwitterer.core.service.pipeline_etwitterecution_logger.PipelineEtwitterecutionLogger
import com.twitter.product_mitwitterer.core.service.pipeline_etwitterecutor.PipelineEtwitterecutor
import com.twitter.product_mitwitterer.core.service.pipeline_selector_etwitterecutor.PipelineSelectorEtwitterecutor
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ProductPipelineBuilderFactory @Inject() (
  gateEtwitterecutor: GateEtwitterecutor,
  pipelineSelectorEtwitterecutor: PipelineSelectorEtwitterecutor,
  pipelineEtwitterecutor: PipelineEtwitterecutor,
  mitwittererPipelineBuilderFactory: MitwittererPipelineBuilderFactory,
  recommendationPipelineBuilderFactory: RecommendationPipelineBuilderFactory,
  statsReceiver: StatsReceiver,
  pipelineEtwitterecutionLogger: PipelineEtwitterecutionLogger) {
  def get[
    TRequest <: Request,
    Query <: PipelineQuery,
    Response
  ]: ProductPipelineBuilder[TRequest, Query, Response] = {
    new ProductPipelineBuilder[TRequest, Query, Response](
      gateEtwitterecutor,
      pipelineSelectorEtwitterecutor,
      pipelineEtwitterecutor,
      mitwittererPipelineBuilderFactory,
      recommendationPipelineBuilderFactory,
      statsReceiver,
      pipelineEtwitterecutionLogger
    )
  }
}
