package com.X.product_mixer.core.service.pipeline_execution_logger

import com.X.product_mixer.core.pipeline.PipelineQuery

trait PipelineExecutionLogger {
  def apply(pipelineQuery: PipelineQuery, message: Any): Unit
}
