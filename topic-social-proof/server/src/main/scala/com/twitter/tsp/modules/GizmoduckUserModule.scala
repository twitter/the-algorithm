package com.twitter.tsp.modules

import com.google.inject.Module
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient._
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.thrift.ClientId
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.gizmoduck.thriftscala.UserService
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule

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
