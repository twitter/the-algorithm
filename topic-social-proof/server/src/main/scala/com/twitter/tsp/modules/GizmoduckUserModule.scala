package com.twitter.tsp.modules

import com.google.inject.Module
import com.twitter.finagle.ThriftMutwitter
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient._
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.thrift.ClientId
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.gizmoduck.thriftscala.UserService
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule

object GizmoduckUserModule
    etwittertends ThriftMethodBuilderClientModule[
      UserService.ServicePerEndpoint,
      UserService.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label: String = "gizmoduck"
  override val dest: String = "/s/gizmoduck/gizmoduck"
  override val modules: Seq[Module] = Seq(TSPClientIdModule)

  override def configureThriftMutwitterClient(
    injector: Injector,
    client: ThriftMutwitter.Client
  ): ThriftMutwitter.Client = {
    super
      .configureThriftMutwitterClient(injector, client)
      .withMutualTls(injector.instance[ServiceIdentifier])
      .withClientId(injector.instance[ClientId])
      .withStatsReceiver(injector.instance[StatsReceiver].scope("giz"))
  }
}
