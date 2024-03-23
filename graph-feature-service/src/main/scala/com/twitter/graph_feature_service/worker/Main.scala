package com.ExTwitter.graph_feature_service.worker

import com.google.inject.Module
import com.ExTwitter.finatra.decider.modules.DeciderModule
import com.ExTwitter.finatra.gizmoduck.modules.TimerModule
import com.ExTwitter.finatra.mtls.thriftmux.Mtls
import com.ExTwitter.finatra.thrift.ThriftServer
import com.ExTwitter.finatra.thrift.filters.{
  LoggingMDCFilter,
  StatsFilter,
  ThriftMDCFilter,
  TraceIdMDCFilter
}
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsThriftWebFormsModule
import com.ExTwitter.finatra.thrift.routing.ThriftRouter
import com.ExTwitter.graph_feature_service.thriftscala
import com.ExTwitter.graph_feature_service.worker.controllers.WorkerController
import com.ExTwitter.graph_feature_service.worker.handlers.WorkerWarmupHandler
import com.ExTwitter.graph_feature_service.worker.modules.{
  GraphContainerProviderModule,
  WorkerFlagModule
}
import com.ExTwitter.graph_feature_service.worker.util.GraphContainer
import com.ExTwitter.inject.thrift.modules.ThriftClientIdModule
import com.ExTwitter.util.Await

object Main extends WorkerMain

class WorkerMain extends ThriftServer with Mtls {

  override val name = "graph_feature_service-worker"

  override val modules: Seq[Module] = {
    Seq(
      WorkerFlagModule,
      DeciderModule,
      TimerModule,
      ThriftClientIdModule,
      GraphContainerProviderModule,
      new MtlsThriftWebFormsModule[thriftscala.Worker.MethodPerEndpoint](this)
    )
  }

  override def configureThrift(router: ThriftRouter): Unit = {
    router
      .filter[LoggingMDCFilter]
      .filter[TraceIdMDCFilter]
      .filter[ThriftMDCFilter]
      .filter[StatsFilter]
      .add[WorkerController]
  }

  override protected def warmup(): Unit = {
    val graphContainer = injector.instance[GraphContainer]
    Await.result(graphContainer.warmup)
    handle[WorkerWarmupHandler]()
  }
}
