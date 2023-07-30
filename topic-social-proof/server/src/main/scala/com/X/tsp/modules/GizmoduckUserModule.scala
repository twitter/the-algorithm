package com.X.tsp.modules

import com.google.inject.Module
import com.X.finagle.ThriftMux
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.mtls.client.MtlsStackClient._
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.thrift.ClientId
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.gizmoduck.thriftscala.UserService
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule

object GizmoduckUserModule
    extends ThriftMethodBuilderClientModule[
      UserService.ServicePerEndpoint,
      UserService.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label: String = "gizmoduck"
  override val dest: String = "/s/gizmoduck/gizmoduck"
  override val modules: Seq[Module] = Seq(TSPClientIdModule)

  override def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client = {
    super
      .configureThriftMuxClient(injector, client)
      .withMutualTls(injector.instance[ServiceIdentifier])
      .withClientId(injector.instance[ClientId])
      .withStatsReceiver(injector.instance[StatsReceiver].scope("giz"))
  }
}
