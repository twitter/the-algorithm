package com.X.product_mixer.core.service.pipeline_executor

import com.X.product_mixer.core.pipeline.PipelineResult
import com.X.product_mixer.core.service.ExecutorResult

case class PipelineExecutorResult[ResultType](
  pipelineResult: PipelineResult[ResultType])
    extends ExecutorResult
