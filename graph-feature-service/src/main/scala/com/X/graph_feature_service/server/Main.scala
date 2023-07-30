package com.X.graph_feature_service.server

import com.google.inject.Module
import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.mtls.thriftmux.Mtls
import com.X.finatra.thrift.ThriftServer
import com.X.finatra.thrift.filters.{
  AccessLoggingFilter,
  LoggingMDCFilter,
  StatsFilter,
  ThriftMDCFilter,
  TraceIdMDCFilter
}
import com.X.finatra.mtls.thriftmux.modules.MtlsThriftWebFormsModule
import com.X.finatra.thrift.routing.ThriftRouter
import com.X.graph_feature_service.server.controllers.ServerController
import com.X.graph_feature_service.server.handlers.ServerWarmupHandler
import com.X.graph_feature_service.server.modules.{
  GetIntersectionStoreModule,
  GraphFeatureServiceWorkerClientsModule,
  ServerFlagsModule
}
import com.X.graph_feature_service.thriftscala
import com.X.inject.thrift.modules.ThriftClientIdModule

object Main extends ServerMain

class ServerMain extends ThriftServer with Mtls {

  override val name = "graph_feature_service-server"

  override val modules: Seq[Module] = {
    Seq(
      ServerFlagsModule,
      DeciderModule,
      ThriftClientIdModule,
      GraphFeatureServiceWorkerClientsModule,
      GetIntersectionStoreModule,
      new MtlsThriftWebFormsModule[thriftscala.Server.MethodPerEndpoint](this)
    )
  }

  override def configureThrift(router: ThriftRouter): Unit = {
    router
      .filter[LoggingMDCFilter]
      .filter[TraceIdMDCFilter]
      .filter[ThriftMDCFilter]
      .filter[AccessLoggingFilter]
      .filter[StatsFilter]
      .add[ServerController]
  }

  override protected def warmup(): Unit = {
    handle[ServerWarmupHandler]()
  }
}
