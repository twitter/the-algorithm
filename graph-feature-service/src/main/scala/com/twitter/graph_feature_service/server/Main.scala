package com.ExTwitter.graph_feature_service.server

import com.google.inject.Module
import com.ExTwitter.finatra.decider.modules.DeciderModule
import com.ExTwitter.finatra.mtls.thriftmux.Mtls
import com.ExTwitter.finatra.thrift.ThriftServer
import com.ExTwitter.finatra.thrift.filters.{
  AccessLoggingFilter,
  LoggingMDCFilter,
  StatsFilter,
  ThriftMDCFilter,
  TraceIdMDCFilter
}
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsThriftWebFormsModule
import com.ExTwitter.finatra.thrift.routing.ThriftRouter
import com.ExTwitter.graph_feature_service.server.controllers.ServerController
import com.ExTwitter.graph_feature_service.server.handlers.ServerWarmupHandler
import com.ExTwitter.graph_feature_service.server.modules.{
  GetIntersectionStoreModule,
  GraphFeatureServiceWorkerClientsModule,
  ServerFlagsModule
}
import com.ExTwitter.graph_feature_service.thriftscala
import com.ExTwitter.inject.thrift.modules.ThriftClientIdModule

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
