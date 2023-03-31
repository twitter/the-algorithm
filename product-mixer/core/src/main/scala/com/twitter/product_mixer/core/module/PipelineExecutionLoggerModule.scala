package com.twitter.product_mixer.core.module

import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.core.service.pipeline_execution_logger.AllowListedPipelineExecutionLogger
import com.twitter.product_mixer.core.service.pipeline_execution_logger.PipelineExecutionLogger

object PipelineExecutionLoggerModule extends TwitterModule {

  override protected def configure(): Unit = {
    bind[PipelineExecutionLogger].to[AllowListedPipelineExecutionLogger]
  }
}
