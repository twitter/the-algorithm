package com.twitter.graph_feature_service.worker.handlers

import com.twitter.finatra.thrift.routing.ThriftWarmup
import com.twitter.inject.Logging
import com.twitter.inject.utils.Handler
import javax.inject.{Inject, Singleton}

@Singleton
class WorkerWarmupHandler @Inject() (warmup: ThriftWarmup) extends Handler with Logging {

  override def handle(): Unit = {
    info("Warmup Done!")
  }
}
