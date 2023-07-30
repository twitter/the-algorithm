package com.X.product_mixer.core.pipeline.product

import com.X.finagle.stats.StatsReceiver
import com.X.product_mixer.core.model.marshalling.request.Request
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.pipeline.mixer.MixerPipelineBuilderFactory
import com.X.product_mixer.core.pipeline.recommendation.RecommendationPipelineBuilderFactory
import com.X.product_mixer.core.service.gate_executor.GateExecutor
import com.X.product_mixer.core.service.pipeline_execution_logger.PipelineExecutionLogger
import com.X.product_mixer.core.service.pipeline_executor.PipelineExecutor
import com.X.product_mixer.core.service.pipeline_selector_executor.PipelineSelectorExecutor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductPipelineBuilderFactory @Inject() (
  gateExecutor: GateExecutor,
  pipelineSelectorExecutor: PipelineSelectorExecutor,
  pipelineExecutor: PipelineExecutor,
  mixerPipelineBuilderFactory: MixerPipelineBuilderFactory,
  recommendationPipelineBuilderFactory: RecommendationPipelineBuilderFactory,
  statsReceiver: StatsReceiver,
  pipelineExecutionLogger: PipelineExecutionLogger) {
  def get[
    TRequest <: Request,
    Query <: PipelineQuery,
    Response
  ]: ProductPipelineBuilder[TRequest, Query, Response] = {
    new ProductPipelineBuilder[TRequest, Query, Response](
      gateExecutor,
      pipelineSelectorExecutor,
      pipelineExecutor,
      mixerPipelineBuilderFactory,
      recommendationPipelineBuilderFactory,
      statsReceiver,
      pipelineExecutionLogger
    )
  }
}
