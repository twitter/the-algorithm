package com.twitter.product_mitwitterer.core.service.pipeline_etwitterecution_logger

import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait PipelineEtwitterecutionLogger {
  def apply(pipelineQuery: PipelineQuery, message: Any): Unit
}
