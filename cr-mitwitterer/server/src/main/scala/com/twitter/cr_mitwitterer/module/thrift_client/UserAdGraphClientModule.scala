package com.twitter.cr_mitwitterer.module.thrift_client

import com.twitter.app.Flag
import com.twitter.cr_mitwitterer.module.core.TimeoutConfigModule.UserAdGraphClientTimeoutFlagName
import com.twitter.finagle.ThriftMutwitter
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient.MtlsThriftMutwitterClientSyntatwitter
import com.twitter.finagle.mutwitter.ClientDiscardedRequestEtwitterception
import com.twitter.finagle.service.ReqRep
import com.twitter.finagle.service.ResponseClass
import com.twitter.finagle.service.RetryBudget
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.recos.user_ad_graph.thriftscala.UserAdGraph
import com.twitter.util.Duration
import com.twitter.util.Throw

object UserAdGraphClientModule
    etwittertends ThriftMethodBuilderClientModule[
      UserAdGraph.ServicePerEndpoint,
      UserAdGraph.MethodPerEndpoint
    ]
    with MtlsClient {

  override val label = "user-ad-graph"
  override val dest = "/s/user-tweet-graph/user-ad-graph"
  private val userAdGraphClientTimeout: Flag[Duration] =
    flag[Duration](UserAdGraphClientTimeoutFlagName, "userAdGraph client timeout")
  override def requestTimeout: Duration = userAdGraphClientTimeout()

  override def retryBudget: RetryBudget = RetryBudget.Empty

  override def configureThriftMutwitterClient(
    injector: Injector,
    client: ThriftMutwitter.Client
  ): ThriftMutwitter.Client =
    super
      .configureThriftMutwitterClient(injector, client)
      .withMutualTls(injector.instance[ServiceIdentifier])
      .withStatsReceiver(injector.instance[StatsReceiver].scope("clnt"))
      .withResponseClassifier {
        case ReqRep(_, Throw(_: ClientDiscardedRequestEtwitterception)) => ResponseClass.Ignorable
      }

}
