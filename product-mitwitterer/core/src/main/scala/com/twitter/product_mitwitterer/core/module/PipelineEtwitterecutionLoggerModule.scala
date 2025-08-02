package com.twitter.product_mitwitterer.core.module

import com.twitter.inject.TwitterModule
import com.twitter.product_mitwitterer.core.service.pipeline_etwitterecution_logger.AllowListedPipelineEtwitterecutionLogger
import com.twitter.product_mitwitterer.core.service.pipeline_etwitterecution_logger.PipelineEtwitterecutionLogger

object PipelineEtwitterecutionLoggerModule etwittertends TwitterModule {

  override protected def configure(): Unit = {
    bind[PipelineEtwitterecutionLogger].to[AllowListedPipelineEtwitterecutionLogger]
  }
}
