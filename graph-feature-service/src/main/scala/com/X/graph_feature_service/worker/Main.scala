package com.X.graph_feature_service.worker

import com.google.inject.Module
import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.gizmoduck.modules.TimerModule
import com.X.finatra.mtls.thriftmux.Mtls
import com.X.finatra.thrift.ThriftServer
import com.X.finatra.thrift.filters.{
  LoggingMDCFilter,
  StatsFilter,
  ThriftMDCFilter,
  TraceIdMDCFilter
}
import com.X.finatra.mtls.thriftmux.modules.MtlsThriftWebFormsModule
import com.X.finatra.thrift.routing.ThriftRouter
import com.X.graph_feature_service.thriftscala
import com.X.graph_feature_service.worker.controllers.WorkerController
import com.X.graph_feature_service.worker.handlers.WorkerWarmupHandler
import com.X.graph_feature_service.worker.modules.{
  GraphContainerProviderModule,
  WorkerFlagModule
}
import com.X.graph_feature_service.worker.util.GraphContainer
import com.X.inject.thrift.modules.ThriftClientIdModule
import com.X.util.Await

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
