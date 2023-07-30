package com.X.product_mixer.core.module

import com.X.inject.XModule
import com.X.product_mixer.core.service.pipeline_execution_logger.AllowListedPipelineExecutionLogger
import com.X.product_mixer.core.service.pipeline_execution_logger.PipelineExecutionLogger

object PipelineExecutionLoggerModule extends XModule {

  override protected def configure(): Unit = {
    bind[PipelineExecutionLogger].to[AllowListedPipelineExecutionLogger]
  }
}
