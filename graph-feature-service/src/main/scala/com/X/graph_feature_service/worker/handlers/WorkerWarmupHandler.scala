package com.X.graph_feature_service.worker.handlers

import com.X.finatra.thrift.routing.ThriftWarmup
import com.X.inject.Logging
import com.X.inject.utils.Handler
import javax.inject.{Inject, Singleton}

@Singleton
class WorkerWarmupHandler @Inject() (warmup: ThriftWarmup) extends Handler with Logging {

  override def handle(): Unit = {
    info("Warmup Done!")
  }
}
