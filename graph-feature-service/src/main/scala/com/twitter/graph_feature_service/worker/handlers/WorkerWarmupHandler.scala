package com.ExTwitter.graph_feature_service.worker.handlers

import com.ExTwitter.finatra.thrift.routing.ThriftWarmup
import com.ExTwitter.inject.Logging
import com.ExTwitter.inject.utils.Handler
import javax.inject.{Inject, Singleton}

@Singleton
class WorkerWarmupHandler @Inject() (warmup: ThriftWarmup) extends Handler with Logging {

  override def handle(): Unit = {
    info("Warmup Done!")
  }
}
