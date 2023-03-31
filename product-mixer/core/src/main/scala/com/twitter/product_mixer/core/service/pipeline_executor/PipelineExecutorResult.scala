package com.twitter.product_mixer.core.service.pipeline_executor

import com.twitter.product_mixer.core.pipeline.PipelineResult
import com.twitter.product_mixer.core.service.ExecutorResult

case class PipelineExecutorResult[ResultType](
  pipelineResult: PipelineResult[ResultType])
    extends ExecutorResult
